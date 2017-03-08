package lbms.command;

/**
 * StatisticsReport class implements the statistics report command.
 * @author Team B
 */
public class StatisticsReport implements Command {

    private int days;

    public StatisticsReport(String request) {
        String[] arguments = request.split(",");

    }

    @Override
    public String execute() {
        // TODO Charles

        return "NOT DONE";
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
