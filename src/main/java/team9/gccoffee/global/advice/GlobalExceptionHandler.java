package team9.gccoffee.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team9.gccoffee.global.exception.MemberTaskException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<String> handleMemberTaskException(MemberTaskException e) {
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
