package lbms.command;

/**
 * Undoable interface for the commands that can be undone.
 * @author Team B
 */
public interface Undoable extends Command {

    /**
     * Un-executes the command, reverses the execute for that given command.
     * @return a string if failure, null if success
     */
    String unExecute();

}
