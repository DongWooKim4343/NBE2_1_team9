package team9.gccoffee.global.exception;

import lombok.Getter;

@Getter
public class GcCoffeeCustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public GcCoffeeCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
