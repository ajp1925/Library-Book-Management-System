package lbms.search;

import lbms.LBMS;
import lbms.models.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * UserSearch class finds users in the system.
 * @author Team B
 */
public enum UserSearch implements Search<Visitor> {
    BY_ID,
    BY_NAME,
    BY_ADDRESS;

    /**
     * Searches the users by id.
     * @param l: the id of the users
     * @return the list of visitors that match
     */
    public List<Visitor> search(long l) {
        return search(Long.toString(l));
    }

    /**
     * Searches the visitors by id, name, or address.
     * @param s: the string to search for
     * @return a list of visitors that match
     */
    @Override
    public List<Visitor> search(String s) {
        switch(this) {
            case BY_ID:
                return find(visitor -> Long.toString(visitor.getVisitorID()).equals(s));
            case BY_NAME:
                return find(visitor -> visitor.getName().equals(s));
            case BY_ADDRESS:
                return find(visitor -> visitor.getAddress().equals(s));
        }
        return new ArrayList<>();
    }

    /**
     * Finds the list of visitors that meet the condition.
     * @param condition: the condition that must be true
     * @return the list of visitors that match
     */
    private List<Visitor> find(Predicate<? super Visitor> condition) {
        return LBMS.getVisitors().parallelStream().filter(condition).collect(Collectors.toList());
    }
}
