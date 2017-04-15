package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.controllers.commandproxy.CommandController;
import lbms.controllers.commandproxy.ProxyCommandController;
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

        e = new Employee(new Visitor("John", "Smith", "JESmith", "password", "address", new PhoneNumber(123, 123, 1234)));
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
//        Command command = new EndVisit(v.getVisitorID());
        Command command = new EndVisit(s.getClientID() + "");
        assertEquals(1, LBMS.getCurrentVisits().size());
        assertEquals(0, LBMS.getTotalVisits().size());
        assertEquals("," + String.format("%010d", v.getVisitorID()) + "," + visit.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + "," +"00:00:00;",
                command.execute());
        assertEquals(0, LBMS.getCurrentVisits().size());
        assertEquals(1, LBMS.getTotalVisits().size());
    }

//    public void testControllerTEMP() throws MissingParametersException {
//        System.out.println(new ProxyCommandController().processRequest(se.getClientID() + ",login,JESmith,password;"));
//        System.out.println(new ProxyCommandController().processRequest(se.getClientID() + ",advance,0,12;"));
//        System.out.println(new ProxyCommandController().processRequest(se.getClientID() + ",arrive;"));
//        System.out.println(new ProxyCommandController().processRequest(s.getClientID() + ",depart," + e.getVisitor().getVisitorID() + ";"));
//    }

    public void testVisitorAlreadyDeparted() throws MissingParametersException {
        //Command command = new EndVisit(s.getV().getVisitorID());
        Command command = new EndVisit(s.getClientID() + "," + v.getVisitorID());
        assertEquals("," + String.format("%010d", v.getVisitorID()) + "," + visit.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + "," +"00:00:00;",
                command.execute());
        assertEquals(",invalid-id;", command.execute());

    }

    public void testDepartInvalidVisitor() throws MissingParametersException {
        //Command command = new EndVisit(99L);
        Command command = new EndVisit("1,99");
        assertEquals(",invalid-id;", command.execute());
    }
}
