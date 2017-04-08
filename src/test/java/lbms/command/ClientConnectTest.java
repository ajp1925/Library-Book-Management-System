package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;

/**
 * Created by 15bar on 4/8/2017.
 */
public class ClientConnectTest extends TestCase{

    @Override
    protected void setUp() {}

    @Override
    protected void tearDown() {
        LBMS.getSessions().clear();
    }

    public void testConnect() {
        Command command = new ClientConnect();
        assertEquals("1;", command.execute());
    }

    public void testTwoConnections() {
        Command command = new ClientConnect();
        assertEquals("1;", command.execute());
        Command command2 = new ClientConnect();
        assertEquals("2;", command2.execute());
    }
}
