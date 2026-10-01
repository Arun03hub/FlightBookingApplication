package flight.org.Exception;

public class InvalidBookingStatusTransactionException extends ResourceNotFoundException{
    public InvalidBookingStatusTransactionException(String message) {
        super(message);
    }
}
