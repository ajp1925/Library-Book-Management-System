package lbms.command;

import lbms.LBMS;
import lbms.models.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * StatisticsReport class implements the statistics report command.
 * @author Team B
 */
public class StatisticsReport implements Command {

    private Integer days;

    /**
     * Constructor for a StatisticsReport command.
     * @param request: the request string to be processed
     */
    public StatisticsReport(String request) {
        if(!request.equals("")) {
            days = Integer.parseInt(request);
        }
    }

    /**
     * Executes the command on the system.
     * @return a string of the response
     */
    @Override
    public String execute() {
        return SystemDateTime.getInstance().getDate().format(SystemDateTime.DATE_FORMAT) + '\n' + generateReport(days);
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        return fields[fields.length - 1];
    }

    /**
     * Generates a Library report including the following information:
     *     -total number of books in the library
     *     -total number of registered library visitors
     *     -average length of a visit (hh:mm:ss)
     *     -number of books purchased
     *     -amount of fines collected
     *     -amount of fines outstanding
     * @param days: the number of days that the report should include in its statistics
     *            if null: report should include statistics using all data
     * @return a string of the response message
     */
    private String generateReport(Integer days) {
        String report = "";
        Duration totalVisitTime = Duration.ZERO;
        Duration averageVisitTime = Duration.ZERO;
        int booksPurchased = LBMS.getBooks().size();
        double collectedFines = 0;
        double outstandingFines = 0;


        //calculate total outstanding fines
        for(Visitor v: LBMS.getVisitors().values()) {
            outstandingFines += v.getFines();
        }

        if(days != null) {

            LocalDate reportStartDate = SystemDateTime.getInstance().getDate().minusDays(days);
            LocalDate reportEndDate = SystemDateTime.getInstance().getDate();

            // grabbing relevant visits
            ArrayList<Visit> visitsInReport = new ArrayList<>();
            for(Visit v: LBMS.getTotalVisits()) {
                if(v.getDate().isBefore(reportEndDate) && v.getDate().isAfter(reportStartDate)) {
                    visitsInReport.add(v);
                }
            }
            // calculating average visit time for all visits in system
            for(Visit v: visitsInReport) {
                totalVisitTime.plus(v.getDuration());
            }
            if(visitsInReport.size() != 0) {
                averageVisitTime = totalVisitTime.dividedBy(visitsInReport.size());
            }

            // calculating collected fines
            for(Transaction t: LBMS.getTransactions()) {
                if(t.getCloseDate() != null &&
                        t.getCloseDate().isBefore(reportEndDate) && t.getCloseDate().isAfter(reportStartDate)) {
                    collectedFines += t.getFinePayed();
                }
            }

            // determine number of books purchased in timeframe
            booksPurchased = 0;
            for(Book b: LBMS.getBooks().values()) {
                if(b.getPurchaseDate().isBefore(reportEndDate) && b.getPurchaseDate().isAfter(reportStartDate)) {
                    booksPurchased++;
                }
            }
        }
        else {
            // calculating average visit time for all visits in system
            for(Visit v : LBMS.getTotalVisits()) {
                totalVisitTime.plus(v.getDuration());
            }
            if(LBMS.getTotalVisits().size() != 0) {
                averageVisitTime = totalVisitTime.dividedBy(LBMS.getTotalVisits().size());
            }

            // calculating collected fines
            for(Transaction t : LBMS.getTransactions()) {
                if(t.getCloseDate() != null) {
                    collectedFines += t.getFinePayed();
                }
            }
        }

        report += ("Number of Books: " + LBMS.getBooks().size() + "\n" +
                "Number of Visitors: " + LBMS.getVisitors().size() + "\n" +
                "Average Length of Visit: " + formatDuration(averageVisitTime) + "\n" +
                "Number of Books Purchased: " + booksPurchased + "\n" +
                "Fines Collected: " + collectedFines + "\n" +
                "Fines Outstanding: " + outstandingFines + "\n"
        );

        return report;
    }

    /**
     * Formats the durations.
     * @param duration: the duration to be formatted
     * @return a string of the formatted duration
     */
    private static String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }
}
