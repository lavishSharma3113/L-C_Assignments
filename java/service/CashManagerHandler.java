package service;

import interfaces.CashManager;

public class CashManagerHandler implements CashManager {

    private double availableCash;

    public CashManagerHandler(double initialCash) {
        this.availableCash = initialCash;
    }

    @Override
    public boolean hasSufficientCash(double amount) {
        return availableCash >= amount;
    }

    @Override
    public void dispenseCash(double amount) {
        if (hasSufficientCash(amount)) {
            availableCash -= amount;
            System.out.println("Please take your cash: $" + amount);
        }
    }

    @Override
    public double getAvailableCash() {
        return availableCash;
    }

    @Override
    public void addCash(double amount) {
        availableCash += amount;
    }

   
}