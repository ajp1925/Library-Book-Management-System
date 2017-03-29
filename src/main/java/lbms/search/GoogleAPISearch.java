package lbms.search;

import lbms.models.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches the Google Books API
 * @author TeamB
 */
public final class GoogleAPISearch{

    private static final String baseURL = "https://www.googleapis.com/books/v1/volumes?q=";

    private GoogleAPISearch() {}

    /**
     * Searches the Google Books API by book title.
     * @param title string representing the title of the book
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByTitle(String title) {
        return parseXML( query(baseURL + "intitle:\"" + title.replaceAll(" ", "+") + "\"") );
    }

    /**
     * Searches the Google Books API by author(s).
     * @param authors ArrayList of authors
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByAuthor(ArrayList<String> authors) {
        String authorString = "";
        for(String author : authors) {
            authorString += ("\"" + author + "\"");
        }

        return parseXML( query(baseURL + "inauthor:" + authorString.replaceAll(" ", "+").replaceAll("\"\"", "\"+\"")) );
    }

    /**
     * Searches the Google Books API by ISBN.
     * @param isbn string representing the ISBN of the book.
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByISBN(String isbn) {
        return parseXML( query(baseURL + "isbn:" + isbn) );
    }

    /**
     * Searches the Google Books API by publisher.
     * @param publisher string representing the publisher of the book
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByPublisher(String publisher) {

        return parseXML( query(baseURL + "inpublisher:\"" + publisher.replaceAll(" ", "+" + "\"")) );
    }

    /**
     * Uses the URL to gather the XML data from Google books
     * @param url the complete URL to perform the GET  request
     * @return a String representation of the XML
     */
    private static String query(String url) {
        String responseString = "";

        try {
            // Create a URL and open a connection
            URL GoogleURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) GoogleURL.openConnection();

            // Set the HTTP Request type method to GET (Default: GET)
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            // Created a BufferedReader to read the contents of the request.
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // MAKE SURE TO CLOSE YOUR CONNECTION!
            in.close();

            responseString = response.toString();

        } catch (IOException e) {
            System.out.println("Improper Google API Query");
        }

        // response is the contents of the XML
        return responseString;

    }

    // What a Book needs:
    // long isbn, String title, ArrayList<String> authors, String publisher, Calendar publishDate,
    // int pageCount, int numberOfCopies, int copiesCheckedOut

    /**
     * Parses the XML into a HashMap mapping ISBNs to Book objects
     * @param response a String representation of the XML
     * @return a list of book objects matching the search parameter
     */
    private static List<Book> parseXML(String response){
        System.out.println(response);
        // TODO
        return null;
    }
}
