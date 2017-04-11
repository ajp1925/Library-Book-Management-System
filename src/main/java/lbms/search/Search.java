package lbms.search;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface to model search classes on.
 * @author Team B
 */
public interface Search<T> {

    /**
     * Creates the predicate for searching.
     * @param s: the condition for the predicate
     * @return a predicate with the given condition
     */
    Predicate<? super T> createPredicate(String s);

    /**
     * Creates a filtered stream with the given condition.
     * @param condition: the condition for the stream
     * @return a stream
     */
    Stream<T> filterStream(Predicate<? super T> condition);

    /**
     * Calls the findAll(String s) method by converting the long to a string.
     * @param l: the long to be converted to a string
     * @return a list of the results
     */
    default List<T> findAll(Long l) {
        return findAll(l.toString());
    }

    /**
     * Finds all the objects from a given search.
     * @param s: the predicate condition
     * @return a list of the results
     */
    default List<T> findAll(String s) {
        return filterStream(createPredicate(s)).collect(Collectors.toList());
    }

    /**
     * Searches for the first match, this method calls the other findFirst that takes a string.
     * @param l: the long to be converted to a string
     * @return the first matching object
     */
    default T findFirst(Long l) {
        return findFirst(l.toString());
    }

    /**
     * Finds the first match for the search.
     * @param s: the predicate condition
     * @return the first matching object
     */
    default T findFirst(String s) {
        return filterStream(createPredicate(s)).findFirst().orElse(null);
    }

}
