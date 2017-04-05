package lbms.models.sessions;

import lbms.models.Visitor;

/**
 * Session class for the LBMS
 * @author Team B
 */
class Session implements ISession {

    private long clientID;
    private Visitor v;

    Session(long clientID) {
        this.clientID = clientID;
        this.v = null;
    }

    public long getClientID() {
        return clientID;
    }

    public void setV(Visitor v) {
        this.v = v;
    }

    public Visitor getV() {
        return v;
    }
}
