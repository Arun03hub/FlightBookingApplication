package flight.org.Exception;

public class InvalidSeatException extends ResourceNotFoundException {
    public InvalidSeatException(String message) {
        super(message);
    }
}
