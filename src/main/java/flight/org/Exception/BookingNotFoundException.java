package flight.org.Exception;

public class BookingNotFoundException extends ResourceNotFoundException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
