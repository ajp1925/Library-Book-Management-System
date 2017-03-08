package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;

/**
 * StatisticsReport class implements the statistics report command.
 * @author Team B
 */
public class StatisticsReport implements Command {

    private Integer days;

    public StatisticsReport(String request) {
        if(!request.equals("")) {
            request = request.replace(";", "");
            days = Integer.parseInt(request);
        }
    }

    @Override
    public String execute() {
        return API.getSystemDate().format(SystemDateTime.DATE_FORMAT) + '\n' + API.generateReport(days);
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
