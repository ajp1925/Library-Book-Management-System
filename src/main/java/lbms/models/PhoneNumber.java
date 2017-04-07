package lbms.models;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
  private static final long serialVersionUID = 1L;

  private int areaCode;
  private int exchangeCode;
  private int extension;

  public PhoneNumber(int areaCode, int exchangeCode, int extension) {
    this.areaCode = areaCode;
    this.exchangeCode = exchangeCode;
    this.extension = extension;
  }

  public PhoneNumber(String s) throws IllegalArgumentException {
    if (isValid(s)) {
      this.areaCode     = Integer.valueOf(s.substring(0, 3));
      this.exchangeCode = Integer.valueOf(s.substring(3, 6));
      this.extension    = Integer.valueOf(s.substring(6, 10));
    } else {
      throw new IllegalArgumentException("Not a valid phone number");
    }
  }

  @Override
  public String toString() {
    return String.format("%03d%03d%03d", areaCode, exchangeCode, extension);
  }


  public static boolean isValid(String number) {
    return (number.length() == 10 && number.matches("-?\\d+?"));
  }
}
