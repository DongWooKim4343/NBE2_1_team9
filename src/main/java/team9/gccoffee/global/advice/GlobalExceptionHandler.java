package team9.gccoffee.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.global.exception.member.MemberTaskException;
import team9.gccoffee.global.exception.order.OrderTaskException;
import team9.gccoffee.global.exception.product.ProductException;
import team9.gccoffee.global.exception.product.ProductTaskException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<String> handleMemberTaskException(MemberTaskException e) {
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        return ResponseEntity.status(status).body(e.getMessage());
    }

    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<String> handleMemberTaskException(OrderTaskException e) {
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        return ResponseEntity.status(status).body(e.getMessage());
    }

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<String> handleMemberTaskException(ProductTaskException e) {
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
