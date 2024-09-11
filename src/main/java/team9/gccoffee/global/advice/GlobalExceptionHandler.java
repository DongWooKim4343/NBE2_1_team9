package team9.gccoffee.global.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team9.gccoffee.global.exception.ErrorResponse;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GcCoffeeCustomException.class)
    public ResponseEntity<ErrorResponse> handleArgsException(GcCoffeeCustomException e) {
        ErrorResponse response = ErrorResponse.from(e.getErrorCode().getHttpStatus(), e.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
