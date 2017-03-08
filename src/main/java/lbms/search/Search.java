package lbms.search;

import java.util.List;

/**
 * Interface to model search classes on.
 * @author Team B
 */
public interface Search<T> {

    /**
     * Finds objects that fit the search criteria
     * @param s: The string to search for
     * @return a list of objects that match
     */
    List<T> search(String s);

    default T findFirst(String s) {
        return search(s).get(0);
    }

}
