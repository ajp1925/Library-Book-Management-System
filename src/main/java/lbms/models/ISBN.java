package lbms.models;

import java.io.Serializable;

/**
 * ISBN class used to store the isbn for a book.
 * @author Team B
 */
public class ISBN implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String value;

    /**
     * Constructor for an ISBN
     * @param isbn: the isbn to be set
     */
    public ISBN(String isbn) {
        this.value = isbn.replaceAll("-", "").trim();
    }

    /**
     * Creates a string of the isbn.
     * @return the string representation of the isbn
     */
    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Determines if the isbn is valid or not.
     * @return true if valid, false if not
     */
    boolean isValid() {
        switch (this.value.length()) {
            case 10:
                return isValidISBN10();
            case 13:
                return isValidISBN13();
            default:
                return false;
        }
    }

    /**
     * Determines if a 10 digit isbn is valid.
     * @return true if valid, false if not
     */
    private boolean isValidISBN10() {
        int sum = 0;
        int n;
        String s;

        for (int i = 10; i > 0; i--) {
            s = this.value.substring(10 - i, 10 - i + 1);
            if (s.equals("X") || s.equals("x")) {
                n = 10;
            } else {
                try {
                    n = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            sum += i * n;
        }

        return (sum % 11 == 0);
    }

    /**
     * Determines if a 13 digit isbn is valid.
     * @return true if valid, false if not
     */
    private boolean isValidISBN13() {
        int sum = 0;
        int n;
        int m;
        String s;

        for (int i = 1; i < 13; i++) {
            s = this.value.substring(i - 1, i);
            try {
                n = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }

            m = ((i % 2 == 1) ? 1 : 3);
            sum += n * m;
        }

        try {
            n = Integer.parseInt(this.value.substring(12, 13));
        } catch (NumberFormatException e) {
            return false;
        }

        return (((10 - sum % 10) % 10) - n == 0);
    }

    /**
     * Used to determine if two ISBNs are equal
     * @param o: the other isbn
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ISBN)) return false;

        ISBN isbn = (ISBN)o;
        return this.value.equals(isbn.toString());
    }
}
