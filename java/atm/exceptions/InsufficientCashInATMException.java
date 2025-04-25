package atm.exceptions;

public class InsufficientCashInATMException extends ATMException {
    public InsufficientCashInATMException(String message) {
        super(message);
    }
}
