package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by 15bar on 4/8/2017.
 */
public class EndVisitTest extends TestCase{

    Session s, se;
    Visitor v;
    Employee e;
    Visit visit;

    @Override
    protected void setUp() {
        v = new Visitor("John", "Smith", "JSmith", "password", "address", new PhoneNumber(123, 123, 1234));
        LBMS.getVisitors().put(v.getVisitorID(), v);

        s = new Session();
        s.setV(v);
        LBMS.getSessions().put(s.getClientID(), s);

        e = new Employee(v);
        LBMS.getEmployees().put(e.getVisitor().getVisitorID(), e);
        LBMS.getVisitors().put(e.getVisitor().getVisitorID(), e.getVisitor());

        se = new Session();
        se.setV(e.getVisitor());
        LBMS.getSessions().put(se.getClientID(), se);

        visit = new Visit(v);

        LBMS.getCurrentVisits().put(v.getVisitorID(), visit);
    }

    @Override
    protected void tearDown() {
        LBMS.getVisitors().clear();
        LBMS.getSessions().clear();
        LBMS.getCurrentVisits().clear();
        LBMS.getTotalVisits().clear();
        LBMS.getEmployees().clear();
    }

    public void testDepartVisitorExplicit() throws MissingParametersException{
        Command command = new EndVisit(v.getVisitorID());
        assertEquals(1, LBMS.getCurrentVisits().size());
        assertEquals(0, LBMS.getTotalVisits().size());
        assertEquals("," + v.getVisitorID() + "," + visit.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + "," +"00:00:00;",
                command.execute());
        assertEquals(0, LBMS.getCurrentVisits().size());
        assertEquals(1, LBMS.getTotalVisits().size());
    }

    public void testDepartNoVisitor() throws MissingParametersException {
        Command command = new EndVisit(s.getV().getVisitorID());
        assertEquals(1, LBMS.getCurrentVisits().size());
        assertEquals(0, LBMS.getTotalVisits().size());
        assertEquals("," + v.getVisitorID() + "," + visit.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + "," +"00:00:00;",
                command.execute());
        assertEquals(0, LBMS.getCurrentVisits().size());
        assertEquals(1, LBMS.getTotalVisits().size());

    }

    public void testVisitorAlreadyDeparted() throws MissingParametersException {
        Command command = new EndVisit(s.getV().getVisitorID());
        assertEquals("," + v.getVisitorID() + "," + visit.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + "," +"00:00:00;",
                command.execute());
        assertEquals(",invalid-id;", command.execute());

    }

    public void testDepartInvalidVisitor() throws MissingParametersException {
        Command command = new EndVisit(s.getV().getVisitorID());
        assertEquals(",invalid-id;", command.execute());

    }

    public void testInvalidClientID() throws MissingParametersException {
        Command command = new EndVisit(99);
        assertEquals(",invalid-client-id;", command.execute());
    }

    public void testDepartWrongVisitor() throws MissingParametersException {
        LBMS.getCurrentVisits().put(e.getVisitor().getVisitorID(), new Visit(e.getVisitor()));
        Command command = new EndVisit(s.getV().getVisitorID());
        assertEquals(",not-authorized;", command.execute());
    }

    public void testEmployeeDepartVisitor() throws MissingParametersException {
        Command command = new EndVisit(se.getV().getVisitorID());
        assertEquals("," + v.getVisitorID() + "," + visit.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + "," +"00:00:00;",
                command.execute());
    }

    public void testMissingParameters() {
        try {
            Command command = new EndVisit(0L);
            fail("Expected exception not thrown");
        }
        catch(MissingParametersException e) {
            assertEquals(",missing-parameters,clientID", e.getMessage());
        }


    }
}
