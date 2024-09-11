package team9.gccoffee.global.exception.product;

public enum ProductException {

    NOT_FOUND("Product not found error", 404),
    BAD_REQUEST("Product bad request error", 400);

    private ProductTaskException productTaskException;

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get() {
        return productTaskException;
    }
}
