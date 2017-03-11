package lbms.command;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BookPurchaseTest {

    @Test
    public void buyOneBook() {
        BookPurchase bp = new BookPurchase("3,9780865473362");
        assertEquals("success,1,9780865473362;", bp.execute());
    }
}
