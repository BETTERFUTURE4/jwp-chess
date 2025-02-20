package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handle() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
