package lbms.models;

import java.io.Serializable;

/**
 * PhoneNumber class used in the Library Book Management System.
 * @author Team B
 */
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    private int areaCode;
    private int exchangeCode;
    private int extension;

    /**
     * Constructor for a PhoneNumber.
     * @param areaCode: the area code
     * @param exchangeCode: the first three digits
     * @param extension: the last 4 digits
     */
    public PhoneNumber(int areaCode, int exchangeCode, int extension) {
        this.areaCode = areaCode;
        this.exchangeCode = exchangeCode;
        this.extension = extension;
    }

    /**
     * Alternate constructor for a phone number.
     * @param s: the string to be converted to a phone number
     * @throws IllegalArgumentException: when the string cannot be turned into a phone number
     */
    public PhoneNumber(String s) throws IllegalArgumentException {
        if (isValid(s)) {
            this.areaCode = Integer.valueOf(s.substring(0, 3));
            this.exchangeCode = Integer.valueOf(s.substring(3, 6));
            this.extension = Integer.valueOf(s.substring(6, 10));
        } else {
            throw new IllegalArgumentException("Not a valid phone number");
        }
    }

    /**
     * Creates a string of the phone number.
     * @return a string representation for the phone number
     */
    @Override
    public String toString() {
        return String.format("%03d%03d%03d", this.areaCode, this.exchangeCode, this.extension);
    }

    /**
     * Determines if the phone number is a valid phone number or not.
     * @param number the string of the phone number
     * @return true if valid, false if not
     */
    private static boolean isValid(String number) {
        return (number.length() == 10 && number.matches("-?\\d+?"));
    }
}
