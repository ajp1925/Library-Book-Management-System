package lbms.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;

/**
 * Class for a Book object, used in the library book management system.
 */
public class Book implements Serializable, Comparable<Book> {

    private static int nextTempID = 0;

    private String title, publisher;
    private ArrayList<String> authors;
    private long isbn;
    private int pageCount, numberOfCopies, copiesCheckedOut;
    private Calendar publishDate;
    private int tempID;

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
    public Book(long isbn, String title, ArrayList<String> authors, String publisher, Calendar publishDate,
                int pageCount, int numberOfCopies, int copiesCheckedOut) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.numberOfCopies = numberOfCopies;
        this.copiesCheckedOut = copiesCheckedOut;
        this.tempID = ++nextTempID;
    }

    /**
     * Getter for the title.
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the publisher.
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Getter for the authors.
     * @return the list of authors of the book
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * Determines if the string is a partial author.
     * @param name: the author name
     * @return true if it is a partial author
     */
    public boolean hasAuthorPartial(String name) {
        return !getAuthors().parallelStream()
                .filter(author -> author.contains(name))
                .collect(Collectors.toList())
                .isEmpty();
    }

    /**
     * Getter for the ISBN.
     * @return the ISBN number of the book
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * Getter for the page count.
     * @return the number of pages in the book
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Getter for the number of copies.
     * @return the quantity of this book the library owns
     */
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     * Getter for copies checked out.
     * @return the number of this book that are unavailable
     */
    public int getCopiesCheckedOut() {
        return copiesCheckedOut;
    }

    /**
     * Getter for the published date.
     * @return the publishing date for the book
     */
    public Calendar getPublishDate() {
        return publishDate;
    }

    /**
     * Gets the temporary ID for the book.
     * @return the temp id
     */
    public int getTempID() {
        return tempID;
    }

    /**
     * Determines if a book is available to be checked out.
     * @return true if there is at least one copy to be checked out, false if not
     */
    public boolean canCheckOut() {
        return copiesCheckedOut < numberOfCopies;
    }

    /**
     * Checks out a book.
     */
    public void checkOut() {
        if (copiesCheckedOut < numberOfCopies) {
            copiesCheckedOut++;
        }
    }

    /**
     * Returns a book.
     */
    public void returnBook() {
        copiesCheckedOut--;
    }

    /**
     * String formatting used in API method to purchase books.
     * @return a string representation of the book in a specific format
     */
    @Override
    public String toString() {
        String output = this.isbn + "," + this.title + ",{";
        for(String author: this.authors) {
            output += author + ",";
        }
        output = output.substring(0, output.length() - 1);
        output += "}," + this.publishDate.getTime();
        return output;
    }

    @Override
    public int compareTo(Book book) {
        String compareTitle = book.getTitle();
        return this.title.compareTo(compareTitle);
    }

}
