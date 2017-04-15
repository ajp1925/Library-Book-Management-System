package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * StatisticsReport class implements the statistics report command.
 * @author Team B TODO -> update for R2
 */
public class StatisticsReport implements Command {

    private Integer days;

    /**
     * Constructor for a StatisticsReport command.
     * @param request: the request string to be processed
     */
    public StatisticsReport(String request) throws MissingParametersException {
        try {
            if (!request.equals("")) {
                this.days = Integer.parseInt(request);
            }
        } catch (NumberFormatException e) {
            throw new MissingParametersException("incorrect-value-for-days;");
        }
    }

    /**
     * Executes the command on the system.
     * @return a string of the response
     */
    @Override
    public String execute() {
        return "," + SystemDateTime.getInstance(null).getDate().format(SystemDateTime.DATE_FORMAT) + ",\n" +
                generateReport(this.days);
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
        for (Visitor v: LBMS.getVisitors().values()) {
            outstandingFines += v.getFines();
        }

        //calculate payed fines
        for (Visitor v: LBMS.getVisitors().values()) {
            collectedFines += v.getPayedFines();
        }

        if (days != null) {

            LocalDate reportStartDate = SystemDateTime.getInstance(null).getDate().minusDays(days);
            LocalDate reportEndDate = SystemDateTime.getInstance(null).getDate();

            // grabbing relevant visits
            ArrayList<Visit> visitsInReport = new ArrayList<>();
            for (Visit v: LBMS.getTotalVisits()) {
                if (v.getDate().isBefore(reportEndDate) && v.getDate().isAfter(reportStartDate)) {
                    visitsInReport.add(v);
                }
            }
            // calculating average visit time for all visits in system
            for (Visit v: visitsInReport) {
                totalVisitTime.plus(v.getDuration());
            }
            if (visitsInReport.size() != 0) {
                averageVisitTime = totalVisitTime.dividedBy(visitsInReport.size());
            }

            // determine number of books purchased in timeframe
            booksPurchased = 0;
            for (Book b: LBMS.getBooks().values()) {
                if (b.getPurchaseDate().isBefore(reportEndDate) && b.getPurchaseDate().isAfter(reportStartDate)) {
                    booksPurchased++;
                }
            }
        } else {
            // calculating average visit time for all visits in system
            for (Visit v : LBMS.getTotalVisits()) {
                totalVisitTime.plus(v.getDuration());
            }
            if (LBMS.getTotalVisits().size() != 0) {
                averageVisitTime = totalVisitTime.dividedBy(LBMS.getTotalVisits().size());
            }
        }

        report += ("Number of Books: " + LBMS.getBooks().size() + "\n" +
                "Number of Visitors: " + LBMS.getVisitors().size() + "\n" +
                "Average Length of Visit: " + formatDuration(averageVisitTime) + "\n" +
                "Number of Books Purchased: " + booksPurchased + "\n" +
                "Fines Collected: " + collectedFines + "\n" +
                "Fines Outstanding: " + outstandingFines);

        return report + ";";
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
