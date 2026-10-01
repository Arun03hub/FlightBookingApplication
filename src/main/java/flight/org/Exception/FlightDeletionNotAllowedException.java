package flight.org.Exception;

public class FlightDeletionNotAllowedException extends ResourceNotFoundException{
    public FlightDeletionNotAllowedException(String message) {
        super(message);
    }
}
