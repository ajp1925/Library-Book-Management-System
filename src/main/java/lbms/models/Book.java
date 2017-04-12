package lbms.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;

/**
 * Class for a Book object, used in the library book management system.
 * @author Team B
 */
public class Book implements Serializable, Comparable<Book> {

    private String title, publisher;
    private ArrayList<String> authors;
    private ISBN isbn;
    private int pageCount, numberOfCopies, copiesCheckedOut;
    private Calendar publishDate;
    private LocalDate purchaseDate;

    /**
     * Constructor for a Book.
     * @param isbn: the isbn number
     * @param title: the title of the book
     * @param authors: the list of authors of the book
     * @param publisher: the publisher of the book
     * @param publishDate: the date the book was published
     * @param pageCount: the number of pages in the book
     * @param numberOfCopies: the quantity of this book the library owns
     * @param copiesCheckedOut: the total number of books that are not available
     */
    public Book(ISBN isbn, String title, ArrayList<String> authors, String publisher, Calendar publishDate,
                int pageCount, int numberOfCopies, int copiesCheckedOut) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.numberOfCopies = numberOfCopies;
        this.copiesCheckedOut = copiesCheckedOut;
    }

    /**
     * Getter for the title.
     * @return the title of the book
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for the page count
     * @return the page count
     */
    public int getPageCount() {
        return this.pageCount;
    }

    /**
     * Getter for the publisher.
     * @return the publisher of the book
     */
    public String getPublisher() {
        return this.publisher;
    }

    /**
     * Getter for the authors.
     * @return the list of authors of the book
     */
    public ArrayList<String> getAuthors() {
        return this.authors;
    }

    /**
     * Determines if the string is a partial author.
     * @param name: the author name
     * @return true if it is a partial author
     */
    public boolean hasAuthorPartial(String name) {
        return !getAuthors().parallelStream().filter(author -> author.contains(name))
                .collect(Collectors.toList()).isEmpty();
    }

    /**
     * Getter for the ISBN.
     * @return the ISBN number of the book
     */
    public ISBN getIsbn() {
        return this.isbn;
    }

    /**
     * Getter for the number of copies.
     * @return the quantity of this book the library owns
     */
    public int getNumberOfCopies() {
        return this.numberOfCopies;
    }

    /**
     * Calculates the number of copies currently available.
     * @return the number of copies of this book that are available
     */
    public int getCopiesAvailable() {
        return this.numberOfCopies - this.copiesCheckedOut;
    }

    /**
     * Getter for the published date.
     * @return the publishing date for the book
     */
    public Calendar getPublishDate() {
        return this.publishDate;
    }

    /**
     * Getter for the purchase date.
     * @return the latest date of purchase (desired?)
     */
    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    /**
     * Checks out a book.
     */
    public void checkOut() {
        if (this.copiesCheckedOut < this.numberOfCopies) {
            this.copiesCheckedOut++;
        }
    }

    /**
     * Undoes the checkout of a book.
     */
    public void undoCheckOut() {
        this.copiesCheckedOut--;
    }

    /**
     * Returns a book.
     */
    public void returnBook() {
        this.copiesCheckedOut--;
    }

    /**
     * Undoes the return of a book.
     */
    public void undoReturnBook() {
        this.copiesCheckedOut++;
    }

    /**
     * String formatting used in API method to purchase books.
     * @return a string representation of the book in a specific format
     */
    @Override
    public String toString() {
        String output = this.isbn + "," + this.title + ",{";
        for (String author: this.authors) {
            output += author + ",";
        }
        output = output.substring(0, output.length() - 1);
        output += "}," + dateFormat();
        return output;
    }

    /**
     * Formats the calendar date to a string format.
     * @return a string of the published date
     */
    public String dateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(this.publishDate.getTime());
    }

    /**
     * Compares a book to this instance of a book.
     * @param book: the book to be compared to
     * @return int representing the comparison of the book titles
     */
    @Override
    public int compareTo(Book book) {
        String compareTitle = book.getTitle();
        return this.title.compareTo(compareTitle);
    }

    /**
     * Sets the purchase date when a book is purchased.
     */
    public void purchase() {
        this.purchaseDate = SystemDateTime.getInstance(null).getDate();
        this.numberOfCopies++;
    }

    /**
     * Undoes the purchase of a book.
     */
    public void undoPurchase() {
        this.numberOfCopies--;
    }

    /**
     * Get all authors as a string
     * @return a string representation of all authors
     */
    public String getAuthorsString() {
        String authors = "";
        for (String author: this.authors) {
            authors += author + ",";
        }
        return authors.replaceAll(",$", "");
    }
}
