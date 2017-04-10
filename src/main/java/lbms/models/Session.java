package lbms.models;

import lbms.LBMS;
import lbms.command.Undoable;

import java.util.Stack;

/**
 * Session class for the LBMS
 * @author Team B
 */
public class Session {

    private long clientID;
    private Visitor v;
    private Stack<Undoable> undoStack;
    private Stack<Undoable> redoStack;

    /**
     * Constructor for a Session.
     */
    public Session() {
        LBMS.incrementSessions();
        this.clientID = LBMS.getTotalSessions();
        this.v = null;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Getter for the clientID.
     * @return the clientID
     */
    public long getClientID() {
        return clientID;
    }

    /**
     * Setter for the visitor.
     * @param v: the visitor to be set
     */
    public void setV(Visitor v) {
        this.v = v;
    }

    /**
     * Getter for the visitor.
     * @return the visitor for the session
     */
    public Visitor getV() {
        return v;
    }

    /**
     * Adds an undoable command to the undostack.
     * @param u: the undoable command to be added
     */
    public void addUndoable(Undoable u) {
        undoStack.push(u);
    }

    /**
     * Pops an undoable off the stack and un-executes it.
     * @return failure message or null if success
     */
    public String undoUndoable() {
        Undoable u = undoStack.pop();
        u.unExecute();
        redoStack.push(u);
        return null; // TODO return a string if failure
    }

    /**
     * Pops an undoable off the redo stack and executes it.
     * @return failure message or null if success
     */
    public String redoUndoable() {
        Undoable u = redoStack.pop();
        u.execute();
        undoStack.push(u);
        return null; // TODO return a string if failure
    }
}
