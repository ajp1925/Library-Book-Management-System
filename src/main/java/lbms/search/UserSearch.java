package lbms.search;

import lbms.LBMS;
import lbms.models.Visitor;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * UserSearch class finds users in the system.
 * @author Team B
 */
public enum UserSearch implements Search<Visitor> {
    BY_ID,
    BY_NAME,
    BY_ADDRESS,
    BY_PHONE;

    /**
     * Creates a predicate condition for the stream.
     * @param s: the condition for the predicate
     * @return a predicate condition
     */
    @Override
    public Predicate<? super Visitor> createPredicate(String s) {
        switch (this) {
            case BY_ID:
                return visitor -> Long.toString(visitor.getVisitorID()).contains(s);
            case BY_NAME:
                return visitor -> visitor.getName().contains(s);
            case BY_ADDRESS:
                return visitor -> visitor.getAddress().contains(s);
            case BY_PHONE:
                return visitor -> visitor.getPhoneNumber().toString().contains(s);
            default:
                return visitor -> true;
        }
    }

    /**
     * Searches the users in the Library Book Management System.
     * @param condition: the condition for the stream
     * @return a stream of visitors that match the condition
     */
    @Override
    public Stream<Visitor> filterStream(Predicate<? super Visitor> condition) {
        return LBMS.getVisitors().values().parallelStream().filter(condition);
    }
}
