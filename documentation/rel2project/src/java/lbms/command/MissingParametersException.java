package lbms.command;

/**
 * Exception class used when the request given has missing parameters.
 * @author Team B
 */
public class MissingParametersException extends Exception {

    /**
     * Constructor for this exception.
     * @param message: the message for the exception.
     */
    public MissingParametersException(String message) {
        super(message);
    }

}
