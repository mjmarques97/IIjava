package order;

public class DuplicateOrderException extends Exception {
    public DuplicateOrderException() {
    }

    public DuplicateOrderException(String message) {
        super(message);
    }
}
