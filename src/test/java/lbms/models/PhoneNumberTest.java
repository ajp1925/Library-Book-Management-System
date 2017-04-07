package lbms.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneNumberTest {

  private static final PhoneNumber VALID_NUMBER = new PhoneNumber("1234567890");
  private static final String      VALID_NUMBER_STRING = "1234567890";

  @Test
  public void validNumber() {
    assertEquals("1234567890", VALID_NUMBER.toString());
  }

  @Test(expected=IllegalArgumentException.class)
  public void InvalidNumber() {
    PhoneNumber INVALID_NUMBER = new PhoneNumber("1234567");
  }
}
