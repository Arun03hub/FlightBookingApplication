package flight.org.Exception;

public class InsufficientSeatException extends ResourceNotFoundException {
    public InsufficientSeatException(String message) {
        super(message);
    }
}
