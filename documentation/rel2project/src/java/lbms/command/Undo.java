package lbms.command;

import lbms.LBMS;

/**
 * Undo class for the undo command.
 * @author Team B
 */
public class Undo implements Command {

    private Long clientID;

    /**
     * Constructor for an Undo class object.
     * @param clientID: the id of the client to undo
     */
    public Undo(Long clientID) {
        this.clientID = clientID;
    }

    /**
     * Executes the command by interacting with the backend in the LBMS.
     * @return the response as a string to the system
     */
    @Override
    public String execute() {
        if (null != LBMS.getSessions().get(this.clientID).undoUndoable()) {
            return ",cannot-undo;";
        }
        return ",success;";
    }
}
