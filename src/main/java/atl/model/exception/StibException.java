package atl.model.exception;

/**
 * <code>StibException</code> is thrown when a resources can't be access.
 */

public class StibException extends Exception {

    /**
     * Creates a new instance of <code>StibException</code> without detail
     * message.
     */
    public StibException() {
        super();
    }

    /**
     * Constructs an instance of <code>StibException</code> with the specified
     * detail message.
     *
     * @param msg message of the exception.
     */
    public StibException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>StibException</code> and wrapped the
     * source exception.
     *
     * @param exception wrapped exception.
     */
    public StibException(Exception exception) {
        super(exception);
    }


}
