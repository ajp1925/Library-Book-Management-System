package lbms.command;

import junit.framework.TestCase;
import lbms.LBMS;
import lbms.models.Book;
import lbms.models.ISBN;
import lbms.search.BookSearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 15bar on 4/4/2017.
 */
public class BookPurchaseTest extends TestCase{

    ArrayList<Book> books;

    @Override
    protected void setUp() {

        books = new ArrayList<>();

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

        books.add( new Book(new ISBN("9781450431835"),
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
        books.add( new Book(new ISBN("9780936070278"),
                   "Galloway's Book on Running",
                   authors,
                   "Shelter Publications",
                   calendar,
                   275,
                   0,
                   0
        ));

        LBMS.getLastBookSearch().addAll(books);
    }

    @Override
    protected void tearDown() {
        LBMS.getLastBookSearch().clear();
        LBMS.getBooks().clear();
        books = null;
    }

    public void testOneBook() throws MissingParametersException {
        BookPurchase command = new BookPurchase("5,1");
        assertEquals(",success,1" +
                "\n9781450431835,Daniels' Running Formula-3rd Edition,{Jack Daniels},12/31/2013,5;",
                command.execute());

        List<Book> b = BookSearch.BY_ISBN.findAll(Long.parseLong("9781450431835"));
        assertEquals(5, b.get(0).getNumberOfCopies());
    }

    public void testTwoBooks() throws MissingParametersException {
        BookPurchase command = new BookPurchase("5,1,2");
        assertEquals(",success,2" +
                "\n9781450431835,Daniels' Running Formula-3rd Edition,{Jack Daniels},12/31/2013,5," +
                "\n9780936070278,Galloway's Book on Running,{Jeff Galloway},12/31/2013,5;",
                command.execute());

        List<Book> b = BookSearch.BY_ISBN.findAll(Long.parseLong("9781450431835"));
        assertEquals(5, b.get(0).getNumberOfCopies());

        b = BookSearch.BY_ISBN.findAll(Long.parseLong("9780936070278"));
        assertEquals(5, b.get(0).getNumberOfCopies());
    }

    public void testIDTooLow() throws MissingParametersException {
        BookPurchase command = new BookPurchase("5,-1");
        assertEquals(",failure;", command.execute());
    }

    public void testIDTooHigh() throws MissingParametersException {
        BookPurchase command = new BookPurchase("5,1000");
        assertEquals(",failure;", command.execute());
    }

    public void testMissingOneParameter() {
        try {
            BookPurchase command = new BookPurchase("1");
            fail("Exception not thrown");
        }
        catch (MissingParametersException e) {
            assertEquals(",missing-parameters,quantity,id[,ids]", e.getMessage());
        }
    }

    public void testMissingAllParameters() {
        try {
            BookPurchase command = new BookPurchase("");
            fail("Exception not thrown");
        }
        catch (MissingParametersException e) {
            assertEquals(",missing-parameters,quantity,id[,ids]", e.getMessage());
        }
    }
}
