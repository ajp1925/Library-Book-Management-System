package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.SystemDateTime;

/**
 * Created by 15bar on 4/7/2017.
 */
public class RegisterTest extends TestCase{

    @Override
    protected void setUp() {}

    @Override
    protected void tearDown() {
        LBMS.getVisitors().clear();
    }

    public void testCleanRegister() throws MissingParametersException{
        Command command = new RegisterVisitor("first_name,last_name,address,1231231234");
        assertEquals("0000000001," + SystemDateTime.getInstance(null).getDate().format(SystemDateTime.DATE_FORMAT) + ";",
                command.execute());
        assertEquals(LBMS.getVisitors().size(), 1);
        assertNotNull(LBMS.getVisitors().get(Long.parseLong("0000000001")));
    }

    public void testDuplicateRegister() throws MissingParametersException {
        Command command = new RegisterVisitor("first_name,last_name,address,1231231234");
        command.execute();
        Command command2 = new RegisterVisitor("first_name,last_name,address,1231231234");
        assertEquals("duplicate;", command2.execute());
        assertEquals(LBMS.getVisitors().size(), 1);
    }

    public void testAlmostDuplicate() throws MissingParametersException {
        Command command = new RegisterVisitor("first_name,last_name,address,1231231234");
        command.execute();
        Command command2 = new RegisterVisitor("first_name,last_name,address,1234567890");
        assertEquals("0000000002," + SystemDateTime.getInstance(null).getDate().format(SystemDateTime.DATE_FORMAT) + ";",
                command2.execute());
        assertEquals(LBMS.getVisitors().size(), 2);
    }
}
