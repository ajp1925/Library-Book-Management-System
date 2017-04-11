package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 15bar on 4/4/2017.
 */
public class BorrowTest extends TestCase{

    HashMap<ISBN, Book> books;
    Visitor v, v2;
    Session s, s2;

    @Override
    protected void setUp() {
        v = new Visitor("John", "Smith", "JSmith", "password", "address", new PhoneNumber(123, 123, 1234));
        LBMS.getVisitors().put(Long.parseLong("0000000001"), v);

        s = new Session();
        s.setV(v);
        LBMS.getSessions().put(s.getClientID(), s);

        v2 = new Visitor("Jane", "Smith", "JaneSmith", "password", "address", new PhoneNumber(123, 123, 1234));
        LBMS.getVisitors().put(Long.parseLong("0000000002"), v2);

        s2 = new Session();
        s2.setV(v2);
        LBMS.getSessions().put(s2.getClientID(), s2);

        books = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse("2013-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        ArrayList<String> authors = new ArrayList<String>();
        authors.add("Jack Daniels");

        books.put(new ISBN("9781450431835"),
                new Book(new ISBN("9781450431835"),
                "Daniels' Running Formula-3rd Edition",
                authors,
                "Human Kinetics",
                calendar,
                306,
                1,
                0
        ));

        authors = new ArrayList<>();
        authors.add("Jeff Galloway");
        books.put(new ISBN("9780936070278"),
                new Book(new ISBN("9780936070278"),
                "Galloway's Book on Running",
                authors,
                "Shelter Publications",
                calendar,
                275,
                1,
                0
        ));

        LBMS.getLastBookSearch().addAll(books.values());
        LBMS.getBooks().putAll(books);
    }

    @Override
    protected void tearDown() {
        LBMS.getLastBookSearch().clear();
        LBMS.getBooks().clear();
        LBMS.getVisitors().clear();
        LBMS.getTransactions().clear();
        LBMS.getSessions().clear();
        books = null;
        v = null;
        s = null;
    }

    public void testOneBookVisitorExplicit() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1}," + v.getVisitorID()); // "book id (list), visitor id (optional)"
        // reponse: YYYY/MM/DD; (due date)
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals("," + systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testTwoBooksVisitorExplicit() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1,2}," + v.getVisitorID());
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals("," + systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testOneBookNoVisitor() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1}");
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals("," + systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testTwoBooksNoVisitor() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1,2}");
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals("," + systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testOneBookWrongVisitor() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1},0000000099");
        assertEquals(",not-authorized;", command.execute());
    }

    public void testTwoBooksWrongVisitor() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1,2},0000000099");
        assertEquals(",not-authorized;", command.execute());
    }

    public void testMissingBookID() {
        try {
            Command command = new Borrow(s.getClientID() + "," + v.getVisitorID());
            fail("Expected exception not thrown");
        }
        catch(MissingParametersException e) {
            assertEquals("missing-parameters,{ids};", e.getMessage());
        }
    }

    public void testMissingAllParams() {
        try {
            Command command = new Borrow("");
            fail("Expected exception not thrown");
        }
        catch(MissingParametersException e) {
            assertEquals("missing-parameters,clientID,{ids};", e.getMessage());
        }
    }

    public void testInvalidBookID() throws MissingParametersException{
        Command command = new Borrow(s.getClientID() + "," + "{99}");
        assertEquals(",invalid-book-id,99;", command.execute());
    }

    public void testOutstanding() throws MissingParametersException {
        Transaction t = new Transaction(new ISBN("9781450431835"), v.getVisitorID());
        LBMS.getVisitors().get(v.getVisitorID()).checkOut(t);
        SystemDateTime.getInstance(null).plusDays(8);

        Command command = new Borrow(s.getClientID() + "," + "{2}");
        assertEquals(",outstanding-fine,10.00;", command.execute());
    }

    public void testNoBooksRemaining() throws MissingParametersException {
        Command command = new Borrow(s.getClientID() + "," + "{1}," + v.getVisitorID());
        command.execute();
        Command command2 = new Borrow(s2.getClientID() + "," + "{1}," + v2.getVisitorID());
        assertEquals(",book-limit-exceeded;", command2.execute());
    }

//    public void testBookLimitExceeded() throws MissingParametersException {
//
//    }
//
//    public void testBookLimitExceededDuringExecution() throws MissingParametersException {
//
//    }
}
