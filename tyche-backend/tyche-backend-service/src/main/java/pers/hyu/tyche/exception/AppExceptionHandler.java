package pers.hyu.tyche.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * This class is used for handler the exceptions.
 *
 * @author Heng Yu
 * @version 1.0
 */
@ControllerAdvice
public class AppExceptionHandler {

    // handle the userService exception
    @ExceptionHandler(value = UserServiceException.class)
    public ResponseEntity<String> handleUserServiceException(UserServiceException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    // handle the other exception
    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<String> handleOtherException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
