package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.PhoneNumber;
import lbms.models.Visitor;

/**
 * Created by 15bar on 4/7/2017.
 */
public class LogInTest extends TestCase{

    @Override
    protected void setUp() {
        LBMS.getVisitors().put(Long.parseLong("0000000001"),
                new Visitor("first_name", "last_name", "username", "password", "address", new PhoneNumber(123, 123, 1234)));
    }

    @Override
    protected void tearDown() {
        LBMS.getVisitors().clear();
    }

    public void testCleanLogin() throws MissingParametersException {
        Command command = new LogIn("username,password");
        assertEquals("success;", command.execute());
    }

    public void testInvalidUsername() throws MissingParametersException{
        Command command = new LogIn("user,password");
        assertEquals("bad-username-or-password;", command.execute());
    }

    public void testIncorrectPassword() throws MissingParametersException {
        Command command = new LogIn("username,passwordx");
        assertEquals("bad-username-or-password;", command.execute());
    }

}
