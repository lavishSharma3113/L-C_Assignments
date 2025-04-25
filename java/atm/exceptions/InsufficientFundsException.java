package atm.exceptions;

public class InsufficientFundsException extends ATMException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
