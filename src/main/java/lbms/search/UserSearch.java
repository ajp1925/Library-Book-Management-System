package lbms.search;

import lbms.LBMS;
import lbms.models.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum UserSearch implements Search<Visitor> {
    BY_ID,
    BY_NAME,
    BY_ADDRESS;

    public List<Visitor> search(long l) {
        return search(Long.toString(l));
    }

    @Override
    public List<Visitor> search(String s) {
        switch (this) {
            case BY_ID:      return find(visitor -> Long.toString(visitor.getVisitorID()).equals(s));
            case BY_NAME:    return find(visitor -> visitor.getName().equals(s));
            case BY_ADDRESS: return find(visitor -> visitor.getAddress().equals(s));
        }
        return new ArrayList<>();
    }

    private List<Visitor> find(Predicate<? super Visitor> condition) {
        return LBMS.getVisitors().parallelStream().filter(condition).collect(Collectors.toList());
    }
}
