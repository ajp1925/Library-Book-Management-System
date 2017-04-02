package lbms.command;

/**
 * CloseLibrary class closes the library.
 * @author Team B TODO -> update for R2
 */
public class CloseLibrary implements Command {

    /**
     * Executes the close library command
     * @return a response
     */
    @Override
    public String execute() {
        return "library-closed;";
    }

    /**
     * Parses the response for library closed
     * @param response: the response string from execute
     * @return output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if (fields[1].equals("library-closed;")) {
            return "\nThe library is now closed.";
        }
        return "\nThere's some kind of error";
    }
}
