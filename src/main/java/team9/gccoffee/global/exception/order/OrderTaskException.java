package team9.gccoffee.global.exception.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderTaskException extends RuntimeException {

    private String message;
    private int code;
}