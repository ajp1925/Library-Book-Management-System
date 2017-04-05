package lbms.models.sessions;

import lbms.LBMS;
import lbms.command.Undoable;
import lbms.models.Visitor;

import java.util.Stack;

/**
 * Created by anthony on 4/4/17.
 */
public class SessionProxy implements ISession {

    private Session s;
    private Stack<Undoable> undoStack;
    private Stack<Undoable> redoStack;

    public SessionProxy() {
        this.s = new Session(LBMS.getSessionProxies().size() + 1);
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public Visitor getV() {
        return s.getV();
    }

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
