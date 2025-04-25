package atm.exceptions;

public class DailyLimitExceededException extends ATMException {
    public DailyLimitExceededException(String message) {
        super(message);
    }
    
}
