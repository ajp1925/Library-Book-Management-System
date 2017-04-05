package lbms.models.sessions;

import lbms.LBMS;
import lbms.command.Undoable;
import lbms.models.Visitor;

import java.util.Stack;

/**
 * SessionProxy class used to control the sessions.
 * @author Team B
 */
public class SessionProxy implements ISession {

    private Session s;
    private Stack<Undoable> undoStack;
    private Stack<Undoable> redoStack;

    /**
     * Constructor for a SessionProxy object.
     */
    public SessionProxy() {
        LBMS.incrementSessions();
        this.s = new Session(LBMS.getTotalSessions());
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Getter for the visitor of the session.
     * @return the visitor of the session
     */
    public Visitor getV() {
        return s.getV();
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

    public long getClientID() {
        return s.getClientID();
    }

    public void setV(Visitor v) {
        s.setV(v);
    }
}
