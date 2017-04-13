package lbms.models;

/**
 * LibraryStatus class created to used the state pattern.
 * @author Team B
 */
public interface LibraryState {

    /**
     * Determines if the library is open.
     * @return true if the library is open, false if not
     */
    boolean isOpen();

}
