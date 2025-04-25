package interfaces;


public interface BankServer {
    boolean validateCard(String cardNumber, String pin) ;
    double getAccountBalance(String cardNumber) ;
    boolean updateBalance(String cardNumber, double newBalance) ;
    boolean isCardBlocked(String cardNumber) ;
    void blockCard(String cardNumber) ;
    double getDailyWithdrawalAmount(String cardNumber) ;
    void updateDailyWithdrawalAmount(String cardNumber, double amount) ;
    double getDailyWithdrawalLimit(String cardNumber) ;
}
