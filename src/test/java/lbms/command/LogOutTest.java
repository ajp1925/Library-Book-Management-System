package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.PhoneNumber;
import lbms.models.Session;
import lbms.models.Visitor;

/**
 * Created by 15bar on 4/7/2017.
 */
public class LogOutTest extends TestCase{

    Session s;
    Visitor v;

    @Override
    protected void setUp() {
        s = new Session();
        v = new Visitor("first_name", "last_name", "username", "password", "address", new PhoneNumber(123, 123, 1234));
        LBMS.getSessions().put(s.getClientID(), s);
        LBMS.getVisitors().put(v.getVisitorID(), v);

        s.setV(v);
    }

    @Override
    protected void tearDown() {
        LBMS.getVisitors().clear();
        LBMS.getSessions().clear();
    }

    public void testCleanLogout() throws MissingParametersException {
        Command command = new LogOut(s.getClientID() + "");
        assertEquals("success;", command.execute());
        assertNull(s.getV());
    }

    public void testInvalidClientID() throws MissingParametersException {
        Command command = new LogOut("99");
        assertEquals("invalid-client-id;", command.execute());
    }

    public void testMissingParameters() {
        try {
            Command command = new LogOut("");
            fail("Expected exception not thrown");
        } catch (MissingParametersException e) {
            assertEquals("Invalid clientID passed to LogOut", e.getMessage());
        }
    }
}
