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

}
