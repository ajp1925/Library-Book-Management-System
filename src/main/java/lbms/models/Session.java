package lbms.models;

import lbms.LBMS;
import lbms.command.Undoable;
import lbms.models.Visitor;

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

    public Session() {
        LBMS.incrementSessions();
        this.clientID = LBMS.getTotalSessions();
        this.v = null;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public long getClientID() {
        return clientID;
    }

    public void setV(Visitor v) {
        this.v = v;
    }

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

    public String undoUndoable() {
        Undoable u = undoStack.pop();
        u.unExecute();
        redoStack.push(u);
        return null; // TODO return a string if failure
    }

    public String redoUndoable() {
        Undoable u = redoStack.pop();
        u.execute();
        undoStack.push(u);
        return null; // TODO return a string if failure
    }
}
