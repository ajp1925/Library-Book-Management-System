package lbms.command;

import lbms.LBMS;

import static lbms.LBMS.SearchService.GOOGLE;
import static lbms.LBMS.SearchService.LOCAL;

/**
 * SetBookService class for the set book service command.
 * @author Team B
 */
public class SetBookService implements Command {

    private Long clientID;
    private LBMS.SearchService search;

    /**
     * Constructor for a SetBookService class object.
     * @param request: the request string to be processed
     * @throws MissingParametersException: when the request format is invalid
     */
    public SetBookService(Long clientID, String request) throws MissingParametersException {
        this.clientID = clientID;
        String s = request.replaceAll(";", "").replaceAll(",", "");
        switch (s) {
            case "local":
                this.search = LOCAL;
                break;
            case "google":
                this.search = GOOGLE;
                break;
            default:
                throw new MissingParametersException("invalid-info-service;");
        }
    }

    /**
     * Executes the command by interacting with the backend in the LBMS.
     * @return the response as a string to the system
     */
    @Override
    public String execute() {
        LBMS.getSessions().get(this.clientID).setSearch(this.search);
        return ",success;";
    }
}
