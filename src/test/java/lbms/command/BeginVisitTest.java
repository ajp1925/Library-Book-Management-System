package lbms.command;

import lbms.LBMS;
import lbms.models.SystemDateTime;
import lbms.models.Visitor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BeginVisitTest {

    ArrayList<Visitor> visitors;

    @Before
    public void setup() {
        visitors = LBMS.getVisitors();
        visitors.clear();
        visitors.add(new Visitor("Johnny", "Test", "123 Test", 1234567890));
    }

    @Test
    public void invalidID() {
        BeginVisit command = new BeginVisit("2;");
        assertEquals("invalid-id;", command.execute());
    }

    @Test
    public void beginVisit() {
        BeginVisit command = new BeginVisit("1;");

        String visitDate = SystemDateTime.getInstance().getDateTime().toLocalDate().format(SystemDateTime.DATE_FORMAT);
        String visitStartTime = SystemDateTime.getInstance().getDateTime().toLocalTime().format(SystemDateTime.TIME_FORMAT);
        assertEquals(String.format("1,%s,%s;", visitDate, visitStartTime), command.execute());
    }

    @Test
    public void duplicate() {
        BeginVisit command = new BeginVisit("1;");
    }
}
