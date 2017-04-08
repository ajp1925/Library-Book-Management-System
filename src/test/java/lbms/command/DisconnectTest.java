package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.Session;

/**
 * Created by 15bar on 4/8/2017.
 */
public class DisconnectTest extends TestCase{

    Session session = new Session();

    @Override
    protected void setUp() {
        session = new Session();
        LBMS.getSessions().put(session.getClientID(), session);
    }

    @Override
    protected void tearDown() {
        LBMS.getSessions().clear();
    }

    public void testDisconnect() throws MissingParametersException {
        assertEquals(1, LBMS.getSessions().size());
        Command command = new Disconnect(session.getClientID());
        command.execute();
        assertEquals(0, LBMS.getSessions().size());
    }

    public void testInvalidClientID() throws MissingParametersException {
        Command command = new Disconnect(session.getClientID() + 99);
        assertEquals("invalid-client-id;", command.execute());
    }
}
