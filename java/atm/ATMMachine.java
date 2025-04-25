package atm;

import java.util.Scanner;

import atm.exceptions.ATMException;
import atm.exceptions.CardBlockedException;
import atm.exceptions.InvalidPinException;
import atm.exceptions.ServerConnectionException;
import interfaces.ATMOperation;
import interfaces.BankServer;
import interfaces.CashManager;
import service.ATMOperationHandler;
import service.BankServerHandler;
import service.CashManagerHandler;

public class ATMMachine {
    private ATMOperation atmOperation;

    public ATMMachine() {
        BankServer bankServer = new BankServerHandler();
        CashManager cashDispenser = new CashManagerHandler(5000.0); 
        this.atmOperation = new ATMOperationHandler(bankServer, cashDispenser);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM Simulation!");

        while (true) {
            showMainMenu();
            int choice = getUserChoice(scanner);

            if (choice == 1) {
                handleCardSession(scanner);
            } else if (choice == 2) {
                System.out.println("Thank you for using the ATM Simulation. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void showMainMenu() {
        System.out.println("\n1. Insert Card");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");
    }

    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private void handleCardSession(Scanner scanner) {
        String cardNumber = getCardNumber(scanner);
        atmOperation.insertCard(cardNumber);

        if (authenticateUser(scanner)) {
            handleTransactions(scanner);
        }

        atmOperation.ejectCard();
    }

    private String getCardNumber(Scanner scanner) {
        System.out.print("Enter your card number: ");
        scanner.nextLine(); // Consume newline
        return scanner.nextLine();
    }

    private boolean authenticateUser(Scanner scanner) {
        int attempts = 0;

        while (attempts < 3) {
            String pin = getPin(scanner);

            try {
                if (atmOperation.validatePin(pin)) {
                    return true;
                }
            } catch (InvalidPinException e) {
                System.out.println(e.getMessage());
                attempts++;
            } catch (CardBlockedException | ServerConnectionException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        System.out.println("Too many failed attempts. Your card has been blocked.");
        return false;
    }

    private String getPin(Scanner scanner) {
        System.out.print("Enter your PIN: ");
        return scanner.nextLine();
    }

    private void handleTransactions(Scanner scanner) {
        while (true) {
            showTransactionMenu();
            int choice = getUserChoice(scanner);

            if (choice == 1) {
                checkBalance();
            } else if (choice == 2) {
                withdrawCash(scanner);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showTransactionMenu() {
        System.out.println("\nTransaction Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Cash");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private void checkBalance() {
        try {
            double balance = atmOperation.checkBalance();
            System.out.println("Your current balance: $" + balance);
        } catch (ATMException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void withdrawCash(Scanner scanner) {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();

        try {
            atmOperation.withdrawCash(amount);
        } catch (ATMException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ATMMachine atm = new ATMMachine();
        atm.start();
    }
}