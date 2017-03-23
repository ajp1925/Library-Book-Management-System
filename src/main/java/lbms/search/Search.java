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

    Predicate<? super T> createPredicate(String s);

    Stream<T> filterStream(Predicate<? super T> condition);

    default List<T> findAll(Long l) {
        return findAll(l.toString());
    }

    default List<T> findAll(String s) {
        return filterStream(createPredicate(s)).collect(Collectors.toList());
    }

    default T findFirst(Long l) {
        return findFirst(l.toString());
    }

    default T findFirst(String s) {
        return filterStream(createPredicate(s)).findFirst().orElse(null);
    }

}
