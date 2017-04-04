package lbms.models.sessions;

import lbms.models.Visitor;

/**
 * Session class for the LBMS
 * @author Team B
 */
class Session implements ISession {

    private long clientID;
    private Visitor v;

    // DO NOT CHANGE THIS TO PUBLIC!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    Session(long clientID) {
        this.clientID = clientID;
    }

    long getClientID() {
        return clientID;
    }

    void setV(Visitor v) {
        this.v = v;
    }

    Visitor getV() {
        return v;
    }
}
