package lbms.command;

import lbms.SystemDateTime;

/**
 * GetDateTime class that calls the API to get the system time.
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
