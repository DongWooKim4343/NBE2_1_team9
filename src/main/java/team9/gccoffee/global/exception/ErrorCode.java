package team9.gccoffee.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Member
    MEMBER_NOT_FOUND(NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    MEMBER_NOT_MATCHED(BAD_REQUEST, "회원 정보가 일치하지 않습니다."),
    MEMBER_NOT_REMOVED(CONFLICT, "회원 삭제에 실패했습니다."),
    MEMBER_NOT_REGISTERED(CONFLICT, "회원 등록에 실패했습니다."),
    MEMBER_NOT_MODIFIED(CONFLICT, "회원 정보 수정에 실패했습니다."),
    MEMBER_NOT_VALID(BAD_REQUEST, "유효하지 않은 관리자 코드입니다."),

    // Product
    PRODUCT_NOT_FOUND(NOT_FOUND, "상품 정보를 찾을 수 없습니다."),
    PRODUCT_BAD_REQUEST(BAD_REQUEST, "상품 요청이 잘못되었습니다."),

    // Order
    ORDER_NOT_FOUND(NOT_FOUND, "주문 정보를 찾을 수 없습니다."),
    ORDER_ITEM_NOT_FOUND(NOT_FOUND, "주문 항목을 찾을 수 없습니다."),
    ORDER_ALREADY_CLOSED(CONFLICT, "주문이 이미 처리되었습니다."),
    ORDER_ALREADY_COMPLETED(CONFLICT, "주문이 이미 완료되었습니다."),
    ORDER_NOT_CANCELED(CONFLICT, "주문이 취소되지 않았습니다."),
    ORDER_QUANTITY_EXCEEDS_STOCK(BAD_REQUEST, "주문 수량이 재고를 초과했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
