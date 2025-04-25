package service;

import atm.exceptions.ServerConnectionException;
import interfaces.BankServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BankServerHandler implements BankServer{
    private Map<String, String> cardPins;
    private Map<String, Double> accountBalances;
    private Map<String, Boolean> blockedCards;
    private Map<String, Double> dailyWithdrawalAmounts;
    private Map<String, Double> dailyWithdrawalLimits;
    private Random random;

    public BankServerHandler() {
        cardPins = new HashMap<>();
        accountBalances = new HashMap<>();
        blockedCards = new HashMap<>();
        dailyWithdrawalAmounts = new HashMap<>();
        dailyWithdrawalLimits = new HashMap<>();
        random = new Random();

        // Add sample data
        cardPins.put("1234567890", "1234");
        accountBalances.put("1234567890", 10000.0);
        blockedCards.put("1234567890", false);
        dailyWithdrawalAmounts.put("1234567890", 0.0);
        dailyWithdrawalLimits.put("1234567890", 2000.0);
    }

    @Override
    public boolean validateCard(String cardNumber, String pin)  {
        simulateServerConnection();
        return cardPins.containsKey(cardNumber) && cardPins.get(cardNumber).equals(pin);
    }

    @Override
    public double getAccountBalance(String cardNumber) throws ServerConnectionException {
        simulateServerConnection();
        return accountBalances.getOrDefault(cardNumber, 0.0);
    }

    @Override
    public boolean updateBalance(String cardNumber, double newBalance) throws ServerConnectionException {
        simulateServerConnection();
        if (accountBalances.containsKey(cardNumber)) {
            accountBalances.put(cardNumber, newBalance);
            return true;
        }
        return false;
    }

    @Override
    public boolean isCardBlocked(String cardNumber) throws ServerConnectionException {
        simulateServerConnection();
        return blockedCards.getOrDefault(cardNumber, false);
    }

    @Override
    public void blockCard(String cardNumber) throws ServerConnectionException {
        simulateServerConnection();
        blockedCards.put(cardNumber, true);
    }

    @Override
    public double getDailyWithdrawalAmount(String cardNumber) throws ServerConnectionException {
        simulateServerConnection();
        return dailyWithdrawalAmounts.getOrDefault(cardNumber, 0.0);
    }

    @Override
    public void updateDailyWithdrawalAmount(String cardNumber, double amount) throws ServerConnectionException {
        simulateServerConnection();
        dailyWithdrawalAmounts.put(cardNumber, amount);
    }

    @Override
    public double getDailyWithdrawalLimit(String cardNumber) throws ServerConnectionException {
        simulateServerConnection();
        return dailyWithdrawalLimits.getOrDefault(cardNumber, 0.0);
    }

    private void simulateServerConnection() throws ServerConnectionException {
        if (random.nextInt(100) < 5) {
            throw new ServerConnectionException("Unable to connect to server. Please try again later.");
        }
    }
}
