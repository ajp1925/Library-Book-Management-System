package lbms.command;

/**
 * Invalid command class.
 * @author Team B
 */
public class Invalid implements Command {

    /**
     * Constructor for an Invalid command.
     */
    public Invalid() {}

    /**
     * Executes the command.
     * @return string of the response
     */
    public String execute() {
        return "invalid-command;";
    }

    /**
     * Parses the response for standard output
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        if(response.equals("invalid-command;")) {
            return "Invalid Command.\n";
        }
        return null;
    }
}
