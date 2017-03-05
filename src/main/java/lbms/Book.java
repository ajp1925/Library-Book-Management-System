package lbms;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;

/**
 * Class for a Book object, used in the library book management system.
 */
public class Book implements Serializable {

    private String title, publisher;
    private ArrayList<String> authors;
    private Long isbn;
    private int pageCount, numberOfCopies, copiesCheckedOut;
    private Calendar publishDate;

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
    public Book(Long isbn, String title, ArrayList<String> authors, String publisher, Calendar publishDate,
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
     * Getter for the ISBN.
     * @return the ISBN number of the book
     */
    public Long getIsbn() {
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

    @Override
    public String toString() {
        return "Book(" + isbn + ", " + title + ", " + authors + ", " + publisher + ", " + publishDate + ", " + pageCount + ")";
    }
}
