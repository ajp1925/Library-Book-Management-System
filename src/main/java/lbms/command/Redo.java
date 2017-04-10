package lbms.command;

import lbms.LBMS;

/**
 * Redo class for the redo command.
 * @author Team B
 */
public class Redo implements Command {

    private Long clientID;

    /**
     * Constructor for a Redo class object.
     * @param clientID: the ID of the client
     */
    public Redo(Long clientID) {
        this.clientID = clientID;
    }

    /**
     * Processes the command by interacting with the backend in the LBMS.
     * @return a string of the response to the system
     */
    @Override
    public String execute() {
        if (null != LBMS.getSessions().get(this.clientID).redoUndoable()) {
            return ",cannot-redo;";
        }
        return ",success;";
    }
}
