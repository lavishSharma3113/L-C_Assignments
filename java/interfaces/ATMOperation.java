package interfaces;

public interface ATMOperation {
    void insertCard(String cardNumber);
    boolean validatePin(String pin) ;
    double checkBalance() ;
    void withdrawCash(double amount) ;
    void ejectCard();
}
