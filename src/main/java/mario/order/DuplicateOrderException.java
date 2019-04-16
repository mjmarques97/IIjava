package mario.order;

/***
 * Não pode haver ordens com o mesmo número, isso é uma exceção, a mesma está definida aqui
 */
public class DuplicateOrderException extends Exception {
    public DuplicateOrderException() {
    }

    public DuplicateOrderException(String message) {
        super(message);
    }
}
