package Assignment2;

public class Wallet {

    private float value;

    public Wallet(float initialAmount) {
        this.value = initialAmount;
    }

    public float getTotalMoney() {
        return value;
    }

    public void addMoney(float deposit) {
        value += deposit;
    }

    public void subtractMoney(float debit) {
        value -= debit;
    }

    public boolean hasSufficientFunds(float amount) {
        return value >= amount;
    }
}

