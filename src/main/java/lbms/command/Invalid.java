package lbms.command;

/**
 * Invalid command class.
 * @author Team B TODO -> update for R2
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
}
