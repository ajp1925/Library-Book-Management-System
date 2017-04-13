package lbms.models;

/**
 * ClosedState class used for the status of the library when it is closed.
 * @author Team B
 */
public class ClosedState implements LibraryState {

    /**
     * Returns false because when the library is in this state it is always closed.
     * @return false
     */
    public boolean isOpen() {
        return false;
    }

}
