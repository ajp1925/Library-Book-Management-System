package lbms.models;

/**
 * OpenState class used for the state of the library when it is open.
 * @author Team B
 */
public class OpenState implements LibraryState {

    /**
     * Returns true because when the library is in this state it is always open.
     * @return true
     */
    public boolean isOpen() {
        return true;
    }

}
