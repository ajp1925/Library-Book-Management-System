package lbms.command;

/**
 * Interface for the Command design pattern.
 * @author Team B
 */
public interface Command {

    /**
     * Executes the command.
     * @return any parameter errors or null for success
     */
    String execute();

    /**
     * Parses the response string for standard output.
     * @param response: the response string from execute
     * @return a string for output
     */
    String parseResponse(String response);

}
