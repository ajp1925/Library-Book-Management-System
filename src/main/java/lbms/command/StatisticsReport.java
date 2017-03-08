package lbms.command;

import lbms.LBMS;
import lbms.models.SystemDateTime;
import lbms.models.Visit;

import java.time.Duration;

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
        return SystemDateTime.getInstance().getDate().format(SystemDateTime.DATE_FORMAT) + '\n' + generateReport(days);
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }

    /**
     * Generates a Library report including the following information:
     *     total number of books in the library
     *     total number of registered library visitors
     *     average length of a visit (hh:mm:ss)
     *     number of books purchased
     *     amount of fines collected
     *     amount of fines outstanding
     * @param days: the number of days that the report should include in its statistics
     *            if null: report should include statistics using all data
     * @return a string of the response message
     */
    private static String generateReport(Integer days) {
        String report = "";

        // calculating average visit time for all visits in system
        Duration totalVisitTime = Duration.ZERO;
        for( Visit v: LBMS.getTotalVisits() ) { totalVisitTime.plus(v.getDuration()); }
        Duration averageVisitTime = totalVisitTime.dividedBy(LBMS.getTotalVisits().size());

        if( days != null ) {
            //TODO
        }
        else {
            report += ("Number of Books: " + LBMS.getBooks().size() + '\n' +
                    "Number of Visitors: " + LBMS.getVisitors().size() + '\n' +
                    "Average Length of Visit: " + averageVisitTime.toString() + '\n' + //TODO fix String for Duration
                    "Number of Books Purchased: " + LBMS.getBooks().size() + '\n' +
                    "Fines Collected: " + "" + '\n' + //TODO make Fine class?
                    "Fines Outstanding: " + "" + '\n' //TODO make Fine class?
            );
        }

        return report;
    }
}
