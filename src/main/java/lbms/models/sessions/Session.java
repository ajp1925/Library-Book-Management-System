package lbms.models.sessions;

import lbms.models.Visitor;

/**
 * Session class for the LBMS
 * @author Team B
 */
public class Session implements ISession {

    private long clientID;
    private Visitor v;

    // DO NOT CHANGE THIS TO PUBLIC!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    Session(long clientID) {
        this.clientID = clientID;
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
