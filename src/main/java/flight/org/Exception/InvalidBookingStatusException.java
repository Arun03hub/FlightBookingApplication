package flight.org.Exception;

public class InvalidBookingStatusException extends ResourceNotFoundException {
    public InvalidBookingStatusException(String message) {
        super(message);
    }
}
