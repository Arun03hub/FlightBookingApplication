package flight.org.Exception;

public class InvalidPaymentStatusException extends ResourceNotFoundException {
    public InvalidPaymentStatusException(String message) {
        super(message);
    }
}
