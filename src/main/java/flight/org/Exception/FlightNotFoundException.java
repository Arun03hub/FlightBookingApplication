package flight.org.Exception;

public class FlightNotFoundException extends ResourceNotFoundException {
    public FlightNotFoundException(String message)
    {
        super(message);
    }
}
