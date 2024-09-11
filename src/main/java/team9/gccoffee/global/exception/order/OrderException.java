package team9.gccoffee.global.exception.order;

public enum OrderException {

    ORDER_NOT_FOUND("Order not found error", 404),
    ORDER_ITEM_NOT_FOUND("Order item not found error", 404),
    ORDER_CLOSED("Order already closed", 400),
    ORDER_COMPLETED("Order already completed", 400),
    ORDER_NOT_CANCELED("Order is not canceled", 400),
    STOCK_OVER("Order quantity is over than product stock", 400);

    private OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
