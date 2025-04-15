package Assignment2;

public class Customer {

    private String firstName;
    private String lastName;
    private Wallet myWallet;

    public Customer(String firstName, String lastName, float initialBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.myWallet = new Wallet(initialBalance);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean pay(float amount) {
        if (myWallet.hasSufficientFunds(amount)) {
            myWallet.subtractMoney(amount);
            return true;
        }
        return false;
    }

    public float getWalletBalance() {
        return myWallet.getTotalMoney(); 
    }
}






