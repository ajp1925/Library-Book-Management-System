package lbms.command;

import lbms.models.SystemDateTime;

/**
 * GetDateTime class that calls the APIContoller to get the system time.
 */
public class GetDateTime implements Command {

    private SystemDateTime instance;

    public GetDateTime(SystemDateTime instance) {
        this.instance = instance;
    }

    /**
     * Gets the system date and time.
     */
    public void execute() {

    }

}
