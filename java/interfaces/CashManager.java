package interfaces;

public interface CashManager {
    boolean hasSufficientCash(double amount);
    void dispenseCash(double amount);
    double getAvailableCash();
    void addCash(double amount);
}
