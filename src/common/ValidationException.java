package common;

/**
 * This exception is thrown when validation of entity fails.
 * 
 * @author Patrik
 */
public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String msg) {
        super(msg);
    }
}
