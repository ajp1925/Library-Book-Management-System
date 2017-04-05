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
    Visitor v;

    @Override
    protected void setUp() {
        v = new Visitor("John", "Smith", "JSmith", "password", "address", 1231231234);
        LBMS.getVisitors().put(Long.parseLong("0000000001"), v);

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
                0,
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
                0,
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
        books = null;
        v = null;
    }

    public void testOneBookVisitorExplicit() throws MissingParametersException {
        Command command = new Borrow("{1},00000000001"); // "book id (list), visitor id (optional)"
        // reponse: YYYY/MM/DD; (due date)
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals(systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testTwoBooksVisitorExplicit() throws MissingParametersException {
        Command command = new Borrow("{1,2},00000000001");
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals(systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testOneBookNoVisitor() throws MissingParametersException {
        Command command = new Borrow("{1}");
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals(systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testTwoBooksNoVisitor() throws MissingParametersException {
        Command command = new Borrow("{1,2}");
        LocalDate systemNow = SystemDateTime.getInstance(null).getDate();
        assertEquals(systemNow.plusWeeks(1).format(SystemDateTime.DATE_FORMAT) + ";", command.execute());
    }

    public void testOneBookWrongVisitor() throws MissingParametersException {
        Command command = new Borrow("{1},0000000099");
        assertEquals("invalid-visitor-id;", command.execute());
    }

    public void testTwoBooksWrongVisitor() throws MissingParametersException {
        Command command = new Borrow("{1,2},0000000099");
        assertEquals("invalid-visitor-id;", command.execute());
    }

    public void testMissingBookID() {
        try {
            Command command = new Borrow("000000001");
            fail("Expected exception not thrown");
        }
        catch(MissingParametersException e) {
            assertEquals("missing-parameters,{ids}", e.getMessage());
        }
    }

    public void testMissingAllParams() {
        try {
            Command command = new Borrow("");
            fail("Expected exception not thrown");
        }
        catch(MissingParametersException e) {
            assertEquals("missing-parameters,{ids}", e.getMessage());
        }
    }

    public void testInvalidBookID() throws MissingParametersException{
        Command command = new Borrow("{99}");
        assertEquals("invalid-book-id;", command.execute());
    }

    public void testOutstanding() throws MissingParametersException {
        Transaction t = new Transaction(new ISBN("9781450431835"), v.getVisitorID());
        LBMS.getVisitors().get(v.getVisitorID()).checkOut(t);
        SystemDateTime.getInstance(null).plusDays(8);

        Command command = new Borrow("{2}");
        assertEquals("outstanding-fine,10.00;", command.execute());
    }

//    public void testBookLimitExceeded() throws MissingParametersException {
//
//    }
//
//    public void testBookLimitExceededDuringExecution() throws MissingParametersException {
//
//    }
}
