package lbms.models;

import java.io.Serializable;

public class ISBN implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String value;

    public ISBN(String isbn) {
        this.value = isbn.replaceAll("-", "").trim();
    }

    @Override
    public String toString() {
        return this.value;
    }

    public boolean isValid() {
        switch (value.length()) {
            case 10:
                return isValidISBN10();
            case 13:
                return isValidISBN13();
            default:
                return false;
        }
    }

    private boolean isValidISBN10() {
        int sum = 0;
        int n;
        String s;

        for (int i = 10; i > 0; i--) {
            s = value.substring(10 - i, 10 - i + 1);
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

    private boolean isValidISBN13() {
        int sum = 0;
        int n;
        int m;
        String s;

        for (int i = 1; i < 13; i++) {
            s = value.substring(i - 1, i);
            try {
                n = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }

            m = ((i % 2 == 1) ? 1 : 3);
            sum += n * m;
        }

        try {
            n = Integer.parseInt(value.substring(12, 13));
        } catch (NumberFormatException e) {
            return false;
        }

        return (((10 - sum % 10) % 10) - n == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ISBN))
            return false;

        ISBN isbn = (ISBN) o;
        return value.equals(isbn.toString());
    }
}
