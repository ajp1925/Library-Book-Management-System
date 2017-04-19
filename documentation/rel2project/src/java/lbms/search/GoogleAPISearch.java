package lbms.search;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lbms.models.Book;
import lbms.models.ISBN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Searches the Google Books api
 * @author Team B
 */
public final class GoogleAPISearch {

    private static final String baseURL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String saleableSuffix = "&filter=paid-ebooks";
    private static final String maxResultsSuffix = "&maxResults=20";

    /**
     * Searches the Google Books api by book title.
     * @param title string representing the title of the book
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByTitle(String title) {
        return parseJSON(query(baseURL + "intitle:\"" + title.replaceAll(" ", "+") + "\"" + saleableSuffix +
                maxResultsSuffix));
    }

    /**
     * Searches the Google Books api by author(s).
     * @param authors ArrayList of authors
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByAuthor(ArrayList<String> authors) {
        String authorString = "";
        for (String author : authors) {
            authorString += ("\"" + author + "\"");
        }
        String formattedAuthors = authorString.replaceAll(" ", "+").replaceAll("\"\"", "\"+\"");

        return parseJSON(query(baseURL + "inauthor:" + formattedAuthors + saleableSuffix + maxResultsSuffix));
    }

    /**
     * Searches the Google Books api by ISBN.
     * @param isbn string representing the ISBN of the book.
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByISBN(String isbn) {
        return parseJSON(query(baseURL + "isbn:" + isbn + saleableSuffix + maxResultsSuffix));
    }

    /**
     * Searches the Google Books api by publisher.
     * @param publisher string representing the publisher of the book
     * @return a list of book objects matching the search parameter
     */
    public static List<Book> searchByPublisher(String publisher) {
        return parseJSON(query(baseURL + "inpublisher:\"" + publisher.replaceAll(" ", "+" + "\"") + saleableSuffix
                + maxResultsSuffix));
    }

    /**
     * Uses the URL to gather the JSON data from Google books
     * @param url the complete URL to perform the GET  request
     * @return a String representation of the JSON
     */
    private static String query(String url) {
        String responseString = "";

        try {
            URL GoogleURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) GoogleURL.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            responseString = response.toString();

        } catch (IOException e) {
            System.out.println("Improper Google api Query");
        }

        return responseString;
    }

    /**
     * Parses the JSON into a List of Book objects
     * @param response a String representation of the JSON
     * @return a list of book objects matching the search parameter
     */
    private static List<Book> parseJSON(String response){
        List<Book> results = new ArrayList<>();

        JsonElement jelement = new JsonParser().parse(response);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray books = jobject.getAsJsonArray("items");

        for (int i = 0; i < books.size(); i++) {
            try {
                JsonObject book = books.get(i).getAsJsonObject();
                JsonObject volumeInfo = book.get("volumeInfo").getAsJsonObject();

                //getting title
                String title;
                if (volumeInfo.get("title") == null) {
                    continue; // skip book
                } else {
                    title = volumeInfo.get("title").toString();
                }

                // getting publisher
                String publisher;
                if (volumeInfo.get("publisher") == null) {
                    continue; // skip book
                } else {
                    publisher = volumeInfo.get("publisher").toString();
                }

                // getting pageCount
                int pageCount;
                if (volumeInfo.get("pageCount") == null) {
                    pageCount = 0;
                } else {
                    pageCount = Integer.parseInt(volumeInfo.get("pageCount").toString());
                }

                // getting ISBN
                ISBN isbn;
                if (volumeInfo.get("industryIdentifiers") == null) {
                    continue; // skip book
                } else {
                    JsonObject isbnInfo =
                            volumeInfo.get("industryIdentifiers").getAsJsonArray().get(0).getAsJsonObject();
                    isbn = new ISBN(isbnInfo.get("identifier").toString().replaceAll("\"", ""));
                }

                // getting publishDate
                Calendar publishDate = Calendar.getInstance();
                if (volumeInfo.get("publishedDate") == null) {
                    continue; // skips book
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatNoDay = new SimpleDateFormat("yyyy-MM");
                    SimpleDateFormat formatOnlyYear = new SimpleDateFormat("yyyy");
                    String publishedDateString = volumeInfo.get("publishedDate").toString().replaceAll("\"", "");
                    if (publishedDateString.length() == 4) {
                        publishedDateString += "-01-01";
                    }
                    Date date;
                    if (publishedDateString.length() > 7) {
                        date = format.parse(publishedDateString);
                    } else if (publishedDateString.length() > 4){
                        date = formatNoDay.parse(publishedDateString);
                    } else {
                        date = formatOnlyYear.parse(publishedDateString);
                    }

                    publishDate.setTime(date);
                }

                // getting authors
                ArrayList<String> authors;
                if (volumeInfo.get("authors") == null) {
                    continue; // skip book
                } else {
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<String>>(){}.getType();
                    authors = converter.fromJson(volumeInfo.get("authors").getAsJsonArray(), type );
                }


                // create Book object
                results.add(new Book(isbn, title, authors, publisher, publishDate, pageCount, 0, 0));

            } catch (ParseException e) {
                System.out.println("Improper JSON parse");
            }
        }

        return results;
    }
}
