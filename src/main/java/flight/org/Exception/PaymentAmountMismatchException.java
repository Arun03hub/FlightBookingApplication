package flight.org.Exception;

public class PaymentAmountMismatchException extends ResourceNotFoundException {
    public PaymentAmountMismatchException(String message) {
        super(message);
    }
}
