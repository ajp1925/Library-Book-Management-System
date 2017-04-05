package lbms.models.sessions;

import lbms.models.Visitor;

/**
 * Created by anthony on 4/4/17.
 */
public interface ISession {

    Visitor getV();

    long getClientID();

    void setV(Visitor v);
}
