package atm.exceptions;

public class InvalidPinException extends ATMException {
    public InvalidPinException(String message) {
        super(message);
    }
}
