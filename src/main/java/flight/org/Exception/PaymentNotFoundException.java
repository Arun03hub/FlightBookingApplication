package flight.org.Exception;

public class PaymentNotFoundException extends ResourceNotFoundException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
