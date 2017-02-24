package lbms;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chris on 2/23/17.
 */
public class Book {
    private String title, publisher;
    private ArrayList<String> authors;
    private int isbn, pageCount, numberOfCopies, copiesCheckedOut;
    private Calendar publishDate;

    public Book(int isbn, String title, ArrayList<String> authors, String publisher, Calendar publishDate,
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

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public int getIsbn() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public int getCopiesCheckedOut() {
        return copiesCheckedOut;
    }

    public Calendar getPublishDate() {
        return publishDate;
    }

    public boolean canCheckOut() {
        return copiesCheckedOut < numberOfCopies;
    }
    public void checkOut() {
        if (copiesCheckedOut < numberOfCopies) {
            copiesCheckedOut++;
        }
    }

    public void returnBook() {
        copiesCheckedOut--;
    }
}
