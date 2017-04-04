package lbms.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ISBNTest {

    private static final ISBN VALID_ISBN_10 = new ISBN("0-14-020652-3");
    private static final ISBN INVALID_ISBN_10 = new ISBN("0-14-020652-2");
    private static final ISBN VALID_ISBN_13 = new ISBN("9780340560068");
    private static final ISBN INVALID_ISBN_13 = new ISBN("9780340560066");

    @Test
    public void validISBN10() {
        assertTrue(VALID_ISBN_10.isValid());
    }

    @Test
    public void invalidISBN10() {
        assertFalse(INVALID_ISBN_10.isValid());
    }

    @Test
    public void validISBN13() {
        assertTrue(VALID_ISBN_13.isValid());
    }

    @Test
    public void invalidISBN13() {
        assertFalse(INVALID_ISBN_13.isValid());
    }
}
