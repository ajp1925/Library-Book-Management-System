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

    @Override
    public Predicate<? super Visitor> createPredicate(String s) {
        switch(this) {
            case BY_ID:      return visitor -> Long.toString(visitor.getVisitorID()).equals(s);
            case BY_NAME:    return visitor -> visitor.getName().equals(s);
            case BY_ADDRESS: return visitor -> visitor.getAddress().equals(s);
            case BY_PHONE:   return visitor -> visitor.getPhoneNumber() == Long.parseLong(s);
            default:         return visitor -> true;
        }
    }

    @Override
    public Stream<Visitor> filterStream(Predicate<? super Visitor> condition) {
        return LBMS.getVisitors().values().parallelStream().filter(condition);
    }
}
