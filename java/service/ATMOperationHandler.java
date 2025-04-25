package service;

import atm.exceptions.*;
import interfaces.ATMOperation;
import interfaces.BankServer;
import interfaces.CashManager;

public class ATMOperationHandler implements ATMOperation{
    private String currentCardNumber;
    private int pinAttempts;
    private final BankServer bankServer;
    private final CashManager cashDispenser;
    private boolean isSessionActive;

    public ATMOperationHandler(BankServer bankServer, CashManager cashDispenser) {
        this.bankServer = bankServer;
        this.cashDispenser = cashDispenser;
        this.pinAttempts = 0;
        this.isSessionActive = false;
    }

    @Override
    public void insertCard(String cardNumber) {
        this.currentCardNumber = cardNumber;
        this.pinAttempts = 0;
        this.isSessionActive = true;
        System.out.println("Card inserted. Please enter your PIN.");
    }

    @Override
    public boolean validatePin(String pin) throws InvalidPinException, CardBlockedException, ServerConnectionException {
        if (!isSessionActive) {
            throw new IllegalStateException("No card inserted");
        }

        if (bankServer.isCardBlocked(currentCardNumber)) {
            throw new CardBlockedException("This card is blocked. Please contact your bank.");
        }

        boolean isValid = bankServer.validateCard(currentCardNumber, pin);
        
        if (!isValid) {
            pinAttempts++;
            if (pinAttempts >= 3) {
                bankServer.blockCard(currentCardNumber);
                throw new CardBlockedException("Too many invalid PIN attempts. Your card has been blocked.");
            }
            throw new InvalidPinException("Invalid PIN. Attempts left: " + (3 - pinAttempts));
        }
        
        pinAttempts = 0;
        return true;
    }

    @Override
    public double checkBalance() throws ServerConnectionException, CardBlockedException {
        if (!isSessionActive) {
            throw new IllegalStateException("No card inserted");
        }

        if (bankServer.isCardBlocked(currentCardNumber)) {
            throw new CardBlockedException("This card is blocked. Please contact your bank.");
        }

        return bankServer.getAccountBalance(currentCardNumber);
    }

    @Override
    public void withdrawCash(double amount) throws InsufficientFundsException, InsufficientCashInATMException, 
                                                ServerConnectionException, DailyLimitExceededException, CardBlockedException {
        validateSession();
        validateCardStatus();
        validateATMFunds(amount);
        validateWithdrawalLimits(amount);
        performWithdrawal(amount);
    }

    private void validateSession() {
        if (!isSessionActive) {
            throw new IllegalStateException("No card inserted");
        }
    }

    private void validateCardStatus() throws CardBlockedException {
        if (bankServer.isCardBlocked(currentCardNumber)) {
            throw new CardBlockedException("This card is blocked. Please contact your bank.");
        }
    }

    private void validateATMFunds(double amount) throws InsufficientCashInATMException {
        if (!cashDispenser.hasSufficientCash(amount)) {
            throw new InsufficientCashInATMException("Insufficient cash in ATM. Maximum withdrawal: $" + cashDispenser.getAvailableCash());
        }
    }

    private void validateWithdrawalLimits(double amount) throws DailyLimitExceededException, ServerConnectionException {
        double dailyWithdrawalAmount = bankServer.getDailyWithdrawalAmount(currentCardNumber);
        double dailyLimit = bankServer.getDailyWithdrawalLimit(currentCardNumber);

        if (dailyWithdrawalAmount + amount > dailyLimit) {
            throw new DailyLimitExceededException("Daily withdrawal limit exceeded. Remaining limit: $" + 
                                                (dailyLimit - dailyWithdrawalAmount));
        }
    }

    private void performWithdrawal(double amount) throws InsufficientFundsException, ServerConnectionException {
        double balance = bankServer.getAccountBalance(currentCardNumber);
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds in account. Current balance: $" + balance);
        }

        bankServer.updateBalance(currentCardNumber, balance - amount);
        bankServer.updateDailyWithdrawalAmount(currentCardNumber, bankServer.getDailyWithdrawalAmount(currentCardNumber) + amount);
        cashDispenser.dispenseCash(amount);

        System.out.println("Transaction successful. New balance: $" + (balance - amount));
    }

    @Override
    public void ejectCard() {
        if (isSessionActive) {
            System.out.println("Card ejected. Thank you for using our ATM.");
            this.currentCardNumber = null;
            this.pinAttempts = 0;
            this.isSessionActive = false;
        }
    }
}
