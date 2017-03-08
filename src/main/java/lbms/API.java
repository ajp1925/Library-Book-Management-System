package lbms;

import lbms.models.Visit;
import lbms.models.Visitor;

/**
 * Facade class for interacting with the system.
 * @author Team B
 */
@Deprecated
public class API {

    /**
     * Registers a visitor with the system, if they are not already registered
     * @param visitor: The visitor to register
     * @return true if successfully registered, false if duplicate
     */
    public static boolean registerVisitor(Visitor visitor) {
        if (!visitorIsRegisteredID(visitor.getVisitorID()) && !visitorIsRegisteredName(visitor.getName()) &&
                !visitorIsRegisteredAddress(visitor.getAddress())) {
            LBMS.getVisitors().add(visitor);
            return true;
        }
        return false;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param id: the id of the visitor
     * @return True if a visitor is registered
     */
    public static boolean visitorIsRegisteredID(long id) {
        return getVisitorByID(id) != null;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param name: the name of the visitor
     * @return True if a visitor is registered
     */
    private static boolean visitorIsRegisteredName(String name) {
        return getVisitorByName(name) != null;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param address: the address of the visitor
     * @return True if a visitor is registered
     */
    private static boolean visitorIsRegisteredAddress(String address) {
        return getVisitorByAddress(address) != null;
    }

    /**
     * Gets a visitor from their unique ID
     * @param visitorID: the visitor ID
     * @return The Visitor if it exists, or null
     */
    public static Visitor getVisitorByID(long visitorID) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getVisitorID() == visitorID)
                .findFirst().orElse(null);
    }

    /**
     * Gets the visitor with the same name.
     * @param name: the first and last name combined of the visitor
     * @return the visitor object
     */
    private static Visitor getVisitorByName(String name) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getName().equals(name))
                .findFirst().orElse(null);
    }

    /**
     * Gets a visitor by their address.
     * @param address: the address of a visitor
     * @return the visitor object of the visitor with the given address
     */
    private static Visitor getVisitorByAddress(String address) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getAddress().equals(address))
                .findFirst().orElse(null);
    }

    /**
     * Ends and removes the visit from LBMS
     * @return the visit object removed
     */
    public static Visit endVisit(Visitor visitor) {
        Visit visit = LBMS.getCurrentVisits().remove(visitor.getVisitorID());
        visit.depart();
        LBMS.getTotalVisits().add(visit);
        return visit;
    }
}
