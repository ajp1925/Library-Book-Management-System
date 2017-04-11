package lbms.controllers.commandproxy;

/**
 * Interface for the command controller and the proxy command controller.
 * @author Team B
 */
public interface ICommandController {

    /**
     * Processes a request string, manipulates the backend of the program, and generates a response string.
     * @param requestString: the request string being read
     * @return a response string
     */
    String processRequest(String requestString);

}
