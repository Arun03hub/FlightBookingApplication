package flight.org.Exception;

public class PassengerNotFoundException extends ResourceNotFoundException {
    public PassengerNotFoundException(String message) {
        super(message);
    }
}
