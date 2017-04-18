package lbms.controllers.commandproxy;

import lbms.models.Book;
import lbms.search.BookSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class for parsing responses.
 * @author Team B
 */
public final class ParseResponseUtility {

    /**
     * Private constructor to prevent any instantiation of this class.
     */
    private ParseResponseUtility() {}

    /**
     * Takes in a response string and parses it into a HashMap.
     * The HashMap returned maps strings (eg. authors) to strings from the response.
     * Formatting Notes:
     *     --All HashMap keys are camelCased
     *     --The name of the command has the key "command"
     *     --Error messages and the success message have the key "message"
     *     --more information about the "message" has the key "invalidValue" (if applicable)
     *     --if the value returned is a large string of books, use parseBooks
     *     --the report from the report command is not parsed
     * @param response response string from a command
     * @return HashMap of the parsed response
     */
    public static HashMap<String, String> parseResponse(String response) {
        HashMap<String, String> parsed = new HashMap<>();

        String[] fields = response.replace(";", "").replace("\n", "\nBOOK:").split("[,\\n]");

        if (fields[0].equals("connect")) {
            parsed.put("command", fields[0]);
            parsed.put("clientID", fields[1]);
        } else {
            parsed.put("clientID", fields[0]);
            parsed.put("command", fields[1]);
            if (fields.length > 2 && isErrorMessage(fields[2])) {
                parsed.put("message", fields[2]);
                String invalidValue = ""; // invalid value may be a set of values
                for (int i = 3; i < fields.length; i++) {
                    if (invalidValue.length() != 0) {
                        invalidValue += ",";
                    }
                    invalidValue += fields[i];
                }
                parsed.put("invalidValue", invalidValue);
            } else {
                switch (fields[1]) {
                    case "advance":
                        parsed.put("message", fields[2]);
                        break;
                    case "arrive":
                        parsed.put("message", "success");
                        parsed.put("visitorID", fields[2]);
                        parsed.put("visitDate", fields[3]);
                        parsed.put("visitStartTime", fields[4]);
                        break;
                    case "borrow":
                        parsed.put("message", "success");
                        parsed.put("dueDate", fields[2]);
                        break;
                    case "borrowed":
                        parsed.put("message", "success");
                        parsed.put("numberOfBooks", fields[2]);
                        parsed.put("books", removeBooks(3,fields));
                        break;
                    case "buy":
                        parsed.put("message", fields[2]);
                        parsed.put("numberOfBooks", fields[3]);
                        parsed.put("books", removeBooks(4, fields));
                        break;
                    case "create":
                        parsed.put("message", fields[2]);
                        break;
                    case "datetime":
                        parsed.put("message", "success");
                        parsed.put("date", fields[2]);
                        parsed.put("time", fields[3]);
                        break;
                    case "depart":
                        parsed.put("message", "success");
                        parsed.put("visitorID", fields[2]);
                        parsed.put("visitEndTime", fields[3]);
                        parsed.put("visitDuration", fields[4]);
                        break;
                    case "disconnect":
                        parsed.put("message", "success");
                        break;
                    case "info":
                        parsed.put("message", "success");
                        parsed.put("numberOfBooks", fields[2]);
                        parsed.put("books", removeBooks(3,fields));
                        break;
                    case "login":
                    case "logout":
                        parsed.put("message", fields[2]);
                        break;
                    case "pay":
                        parsed.put("message", fields[2]);
                        parsed.put("balance",fields[3]);
                        break;
                    case "redo":
                        parsed.put("message", fields[2]);
                        break;
                    case "register":
                        parsed.put("message", "success");
                        parsed.put("visitorID", fields[2]);
                        parsed.put("registrationDate", fields[3]);
                        break;
                    case "report":
                        String[] reportFields = response.replace(";", "").split(",");
                        parsed.put("message", "success");
                        parsed.put("date",reportFields[2]);
                        parsed.put("report",reportFields[3]);
                        break;
                    case "reset":
                        parsed.put("message",fields[2]);
                        break;
                    case "return":
                        parsed.put("message",fields[2]);
                        if (fields.length > 3) {
                            parsed.put("fine",fields[3]);
                            String ids = "";
                            for (int i = 4; i < fields.length; i++) {
                                if (ids.length() != 0) {
                                    ids += ",";
                                }
                                ids += fields[i];
                            }
                            parsed.put("ids", ids); // comma separated list of ids
                        }
                        break;
                    case "search":
                        parsed.put("message", "success");
                        parsed.put("numberOfBooks", fields[2]);
                        parsed.put("books", removeBooks(3,fields));
                        break;
                    case "service":
                        parsed.put("message", fields[2]);
                        break;
                    case "undo":
                        parsed.put("message", fields[2]);
                        break;
                    default:
                        // remember to check for not-authorized
                        parsed.put("message", "failure");
                        System.out.println("Bad response to parse");
                        parsed.put("badResponse", response);
                        break;
                } // end switch
            } // end if error
        } // end if connect command

        return parsed;
    }

    /**
     * Takes an arbitrarily long string of books and creates an ArrayList with an
     * entry for each book.
     * Each index is a HashMap mapping string of types of Book information (eg. title)
     * to a string of the associated value.
     * IMPORTANT: this function was only meant to parse strings that were
     *            part of a command response
     * @return ArrayList(HashMap[bookInfoType, bookInfo])
     */
    public static ArrayList<HashMap<String,String>> parseBooks(String booksString) {
        ArrayList<HashMap<String,String>> books = new ArrayList<>();

        String[] booksArray = booksString.replaceAll("^BOOK:", "").split("BOOK:");

        for (String book : booksArray) {
            String[] bookPieces = book.replace("BOOK:", "").split(",");
            String publishDate = "";
            for (String piece : bookPieces) {
                if (piece.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    publishDate = piece;
                }
            }
            HashMap<String,String> bookInfo = new HashMap<>();
            if (bookPieces.length == 4) { //FindBorrowed
                bookInfo.put("id", bookPieces[0]);
                bookInfo.put("isbn", bookPieces[1]);
                bookInfo.put("title", bookPieces[2]);
                bookInfo.put("dateBorrowed", bookPieces[3]);
                books.add(bookInfo);
            } else if (bookPieces[0].length() == 13) { // buy
                Book b = BookSearch.BY_ISBN.toBuy().findFirst(bookPieces[0]);
                bookInfo.put("isbn", b.getIsbn().toString());
                bookInfo.put("title", b.getTitle());
                bookInfo.put("authors", b.getAuthorsString());
                bookInfo.put("publishDate", publishDate);
                bookInfo.put("quantity", bookPieces[bookPieces.length-1]);
                books.add(bookInfo);
            } else if (bookPieces[1].length() > 3) { // search
                bookInfo.put("id", bookPieces[0]);
                if(BookSearch.BY_ISBN.toBuy().findFirst(bookPieces[1]) != null) {
                    Book b = BookSearch.BY_ISBN.toBuy().findFirst(bookPieces[1]);
                    bookInfo.put("isbn", b.getIsbn().toString());
                    bookInfo.put("title", b.getTitle());
                    bookInfo.put("authors", b.getAuthorsString());
                    bookInfo.put("publishDate", publishDate);
                    bookInfo.put("publisher", b.getPublisher());
                    bookInfo.put("pageCount", b.getPageCount() + "");
                    books.add(bookInfo);
                }
                else { // from GoogleAPI
                    bookInfo.put("isbn", bookPieces[1]);
                    bookInfo.put("title", bookPieces[2]);
                    String authorString = "";
                    for(int index = 3; index < bookPieces.length-2; index++) {
                        authorString += bookPieces[index].replaceAll("[{}]", "");
                        if(index != bookPieces.length-3) {
                            authorString += ",";
                        }
                    }
                    bookInfo.put("authors", authorString);
                    bookInfo.put("publishDate", bookPieces[bookPieces.length-2]);
                    bookInfo.put("publisher", "Unknown");
                    bookInfo.put("pageCount", bookPieces[bookPieces.length-1]);
                    books.add(bookInfo);
                }
            } else { // info
                bookInfo.put("quantity", bookPieces[0]);
                bookInfo.put("id", bookPieces[1]);
                Book b = BookSearch.BY_ISBN.toBuy().findFirst(bookPieces[2]);
                bookInfo.put("isbn", b.getIsbn().toString());
                bookInfo.put("title", b.getTitle());
                bookInfo.put("authors", b.getAuthorsString());
                bookInfo.put("publishDate", publishDate);
                bookInfo.put("publisher", b.getPublisher());
                bookInfo.put("pageCount", b.getPageCount() + "");
                books.add(bookInfo);
            }
        }
        return books;
    }

    /**
     * Some commands provide a response ending with an arbitrary number of books.
     * This function removes all of them from startingIndex to the end and
     * creates one large string of them.
     * Pass this string to parseBooks for further parsing.
     * @param startIndex the index where the first book lives
     * @param fields fields from the split response string
     * @return large book string
     */
    private static String removeBooks(int startIndex, String[] fields) {
        String books = "";
        for (int i = startIndex; i < fields.length; i++) {
            if (books.length() != 0) {
                books += ",";
            }
            books += fields[i];
        }
        return books.replace(",,", ",");
    }

    /**
     * Error messages are handled fairly similarly by all command responses.
     * This function checks if the provided message is an error response.
     * @param message the message in question
     * @return true if the message is an error response, false otherwise
     */
    private static boolean isErrorMessage(String message) {
        ArrayList<String> errorMessages = new ArrayList<>(Arrays.asList(
                "not-authorized", "invalid-number-of-days", "invalid-number-of-hours", "duplicate",
                "invalid-id", "invalid-visitor-id", "invalid-book-id", "book-limit-exceeded",
                "outstanding-fine", "duplicate-username", "duplicate-visitor", "invalid-sort-order",
                "bad-username-or-password", "invalid-amount", "cannot-redo", "cannot-undo",
                "invalid-visitor", "missing-parameters", "library-closed", "incorrect-value-for-days",
                "no-more-copies"
        ));
        return errorMessages.contains(message);
    }
}
