package flight.org.Exception;

import flight.org.DTO.Response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {
    // all exception like flight,passenger Exception have to write same code...so those exception will extend
    // ResourceNotFoundException  and this exception handle in global exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(ResourceNotFoundException e) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
