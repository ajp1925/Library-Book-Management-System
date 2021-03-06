package lbms.models;

import lbms.LBMS;
import lbms.LBMS.SearchService;
import lbms.command.Undoable;

import java.util.ArrayList;
import java.util.Stack;

import static lbms.LBMS.SearchService.LOCAL;

/**
 * Session class for the LBMS
 * @author Team B
 */
public class Session {

    private long clientID;
    private Visitor v;
    private SearchService search;
    private Stack<Undoable> undoStack;
    private Stack<Undoable> redoStack;
    private ArrayList<Book> bookSearch;

    /**
     * Constructor for a Session.
     */
    public Session() {
        LBMS.incrementSessions();
        this.clientID = LBMS.getTotalSessions();
        this.v = null;
        this.search = LOCAL;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.bookSearch = new ArrayList<>();
    }

    /**
     * Getter for the clientID.
     * @return the clientID
     */
    public long getClientID() {
        return this.clientID;
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
        return this.v;
    }

    /**
     * Adds an undoable command to the undostack.
     * @param u: the undoable command to be added
     */
    public void addUndoable(Undoable u) {
        this.undoStack.push(u);
    }

    public void popUndoable() {
        this.undoStack.pop();
    }

    /**
     * Pops an undoable off the stack and un-executes it.
     * @return failure message or null if success
     */
    public String undoUndoable() {
        Undoable u;
        if (this.undoStack.size() > 0) {
            u = this.undoStack.pop();
        } else {
            return "failure";
        }
        u.unExecute();
        this.redoStack.push(u);
        return null;
    }

    /**
     * Pops an undoable off the redo stack and executes it.
     * @return failure message or null if success
     */
    public String redoUndoable() {
        Undoable u;
        if (this.redoStack.size() > 0) {
            u = this.redoStack.pop();
        } else {
            return "failure";
        }
        u.execute();
        this.undoStack.push(u);
        return null;
    }

    /**
     * Sets the search service for the session.
     * @param ss: the search service to be set
     */
    public void setSearch(SearchService ss) {
        this.search = ss;
    }

    /**
     * Getter for the search service.
     * @return the search type being used
     */
    public SearchService getSearch() {
        return this.search;
    }

    /**
     * Clears the undo and redo stacks.
     */
    public void clearStacks() {
        this.undoStack.clear();
        this.redoStack.clear();
    }

    /**
     * Getter for the book search ArrayList.
     * @return the list of books from the search
     */
    public ArrayList<Book> getBookSearch() {
        return this.bookSearch;
    }
}
