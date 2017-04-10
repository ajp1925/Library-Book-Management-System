package lbms.command;

/**
 * Undoable interface for the commands that can be undone.
 * @author Team B
 */
public interface Undoable {

    /**
     * Executes the command.
     * @return any parameter errors or null for success
     */
    String execute();

    /**
     * Un-executes the command, reverses the execute for that given command.
     * @return a string if failure, null if success
     */
    String unExecute();

}
