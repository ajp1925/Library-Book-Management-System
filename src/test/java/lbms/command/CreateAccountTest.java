package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.PhoneNumber;
import lbms.models.Visitor;

/**
 * Created by 15bar on 4/4/2017.
 */
public class CreateAccountTest extends TestCase{

    @Override
    protected void setUp() {
        LBMS.getVisitors().put(
                Long.parseLong("0000000001"),
                new Visitor("first_name", "last_name", null, null, "address", new PhoneNumber(123, 123, 1234))
                );
        LBMS.getVisitors().put(
                Long.parseLong("0000000002"),
                new Visitor("first_name", "last_name", "JohnSmith", null, "address", new PhoneNumber(123, 123, 1234))
        );
        LBMS.getVisitors().put(
                Long.parseLong("0000000003"),
                new Visitor("first_name", "last_name", "username", "password", "address", new PhoneNumber(123, 123, 1234))
        );
    }

    @Override
    protected void tearDown() {
        LBMS.getVisitors().clear();
        LBMS.getEmployees().clear();
    }

    public void testCleanVisitorCreation() throws MissingParametersException {
        Command command = new CreateAccount("user,pass,visitor,0000000001");
        assertEquals(",success;", command.execute());
        assertNotNull(LBMS.getVisitors().get(Long.parseLong("0000000001")));
        assertEquals("user", LBMS.getVisitors().get(Long.parseLong("0000000001")).getUsername());
        assertEquals("pass", LBMS.getVisitors().get(Long.parseLong("0000000001")).getPassword());
        assertEquals(0, LBMS.getEmployees().size());
    }

    public void testCleanEmployeeCreation() throws MissingParametersException {
        Command command = new CreateAccount("user,pass,employee,0000000001");
        assertEquals(",success;", command.execute());
        assertNotNull(LBMS.getVisitors().get(Long.parseLong("0000000001")));
        assertNotNull(LBMS.getEmployees().get(Long.parseLong("0000000001")));
        assertEquals("user", LBMS.getVisitors().get(Long.parseLong("0000000001")).getUsername());
        assertEquals("pass", LBMS.getVisitors().get(Long.parseLong("0000000001")).getPassword());
        assertEquals(1, LBMS.getEmployees().size());
    }

    public void testDuplicateUsername() throws MissingParametersException {
        Command command = new CreateAccount("username,pass,visitor,0000000001");
        assertEquals(",duplicate-username;", command.execute());
        assertNotNull(LBMS.getVisitors().get(Long.parseLong("0000000001")));
        assertNull(LBMS.getVisitors().get(Long.parseLong("0000000001")).getUsername());
        assertNull(LBMS.getVisitors().get(Long.parseLong("0000000001")).getPassword());
        assertEquals(0, LBMS.getEmployees().size());
    }

    public void testAccountAlreadyExists() throws MissingParametersException {
        Command command = new CreateAccount("user,pass,visitor,0000000003");
        assertEquals(",duplicate-visitor;", command.execute());
        assertNotNull(LBMS.getVisitors().get(Long.parseLong("0000000003")));
        assertEquals("username", LBMS.getVisitors().get(Long.parseLong("0000000003")).getUsername());
        assertEquals("password", LBMS.getVisitors().get(Long.parseLong("0000000003")).getPassword());
        assertEquals(0, LBMS.getEmployees().size());
    }

    public void testInvalidVisitorID() throws MissingParametersException {
        Command command = new CreateAccount("user,pass,visitor,0000000099");
        assertEquals(",invalid-visitor;", command.execute());
    }

    public void testInvalidRole() throws MissingParametersException {
        Command command = new CreateAccount("user,pass,visitoremployee,000000001");
        assertEquals(",invalid-role;", command.execute());
    }

    public void testMissingParameter() {
        try {
            Command command = new CreateAccount("user,pass,visitor");
            fail("Expected Exception not thrown");
        } catch (MissingParametersException e) {
            assertEquals("missing-parameters,username,password,role,visitorID;", e.getMessage());
        }
    }
}
