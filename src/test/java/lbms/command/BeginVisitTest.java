package lbms.command;

import lbms.LBMS;
import lbms.models.Visitor;
import org.junit.Before;

import java.util.HashMap;

public class BeginVisitTest {

    HashMap<Long, Visitor> visitors;

    @Before
    public void setup() {
        visitors = LBMS.getVisitors();
        visitors.clear();
        visitors.put(1L, new Visitor("Johnny", "Test", "123 Test", "password", "123 Street", 1234567890));
//        visitors.put(1L, new Visitor("Johnny", "Test", "123 Test", 1234567890));
    }

    @Test
    public void invalidID() throws Exception {
        BeginVisit command = new BeginVisit("2");
        assertEquals("invalid-id;", command.execute());
    }

//    @Test
//    public void invalidID() throws Exception {
//        BeginVisit command = new BeginVisit("2");
//        assertEquals("invalid-id;", command.execute());
//    }

//    @Test
//    public void beginVisit() throws Exception {
//        BeginVisit command = new BeginVisit("1");
//
//        String visitDate = SystemDateTime.getInstance().getDateTime().toLocalDate().format(SystemDateTime.DATE_FORMAT);
//        String visitStartTime = SystemDateTime.getInstance().getDateTime().toLocalTime().format(SystemDateTime.TIME_FORMAT);
//        assertEquals(String.format("1,%s,%s;", visitDate, visitStartTime), command.execute());
//    }

//    @Test
//    public void duplicate() throws Exception {
//        BeginVisit command = new BeginVisit("1");
//        // didn't finish this one
//        assertEquals(5,5);
//    }
}
