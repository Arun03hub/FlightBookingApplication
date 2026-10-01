package flight.org.Exception;

public class PaymentFailedException extends ResourceNotFoundException {
    public PaymentFailedException(String message) {
        super(message);
    }
}
