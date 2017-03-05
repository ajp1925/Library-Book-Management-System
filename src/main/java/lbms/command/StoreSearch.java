package lbms.command;

import java.util.ArrayList;

/**
 * Created by anthony on 3/5/17.
 */
public class StoreSearch implements Command {

    private String title;
    private ArrayList<String> authors;
    private Long isbn;
    private String publisher;
    private String sortOrder;

    public StoreSearch(String title, ArrayList<String> authors, Long isbn, String publisher, String sortOrder) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.publisher = publisher;
        this.sortOrder = sortOrder;
    }

    public void execute() {
        // TODO Edward
    }

}
