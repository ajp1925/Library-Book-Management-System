package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.PhoneNumber;
import lbms.models.Session;
import lbms.models.Visitor;

/**
 * Created by 15bar on 4/7/2017.
 */
public class LogInTest extends TestCase{

    Session s;
    Visitor v;

    @Override
    protected void setUp() {
        s = new Session();
        v = new Visitor("first_name", "last_name", "username", "password", "address", new PhoneNumber(123, 123, 1234));
        LBMS.getSessions().put(s.getClientID(), s);
        LBMS.getVisitors().put(v.getVisitorID(), v);
    }

    @Override
    protected void tearDown() {
        LBMS.getVisitors().clear();
        LBMS.getSessions().clear();
    }

    public void testCleanLogin() throws MissingParametersException {
        Command command = new LogIn(s.getClientID() + "," + "username,password");
        assertEquals(",success;", command.execute());
        assertEquals(LBMS.getSessions().get(s.getClientID()).getV().getVisitorID(), v.getVisitorID());
    }

    public void testInvalidUsername() throws MissingParametersException{
        Command command = new LogIn(s.getClientID() + "," + "user,password");
        assertEquals(",bad-username-or-password;", command.execute());
        assertNull(LBMS.getSessions().get(s.getClientID()).getV());
    }

    public void testIncorrectPassword() throws MissingParametersException {
        Command command = new LogIn(s.getClientID() + "," + "username,passwordx");
        assertEquals(",bad-username-or-password;", command.execute());
        assertNull(LBMS.getSessions().get(s.getClientID()).getV());
    }

}
