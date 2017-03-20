package lbms.search;

import java.util.List;

/**
 * Interface to model search classes on.
 * @author Team B
 */
public interface Search<T> {

    /**
     * Finds objects that fit the search criteria
     * @param s: the string to search for
     * @return a list of objects that match
     */
    List<T> search(String s);

    /**
     * Finds the first instance of l.
     * @param l: the long to be searched for
     * @return the first instance of l
     */
    default T findFirst(Long l) {
        return findFirst(Long.toString(l));
    }

    /**
     * Finds the first instance of s.
     * @param s: the string to be searched for
     * @return the first instance of s
     */
    default T findFirst(String s) {
        List<T> results = search(s);
        return results.isEmpty() ? null : results.get(0);
    }

}
