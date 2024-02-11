package uz.cosinus.thinkstore.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.cosinus.thinkstore.exception.*;


@ControllerAdvice
public class  GlobalExceptionHandler {
    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<String> authException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(value = DataAlreadyExistsException.class)
    public ResponseEntity<String> dataAlreadyExists (DataAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> authException(AuthenticationCredentialsNotFoundException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(value = DataNotEnoughException.class)
    public ResponseEntity<String> dataNotFound (DataNotEnoughException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<String> dataNotFound (DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = ItemNotFoundException.class)
    public ResponseEntity<String> itemNotFount(ItemNotFoundException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
