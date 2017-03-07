package lbms.command;

import lbms.models.SystemDateTime;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

public class AdvanceTimeTest {

    @Test
    public void tooFewDays() {
        AdvanceTime command = new AdvanceTime("-1;");
        assertEquals("invalid-number-of-days,-1;", command.execute());
    }

    @Test
    public void tooManyDays() {
        AdvanceTime commmand = new AdvanceTime("8;");
        assertEquals("invalid-number-of-days,8;", commmand.execute());
    }

    @Test
    public void tooFewHours() {
        AdvanceTime command = new AdvanceTime("1,-1");
        assertEquals("invalid-number-of-hours,-1;", command.execute());
    }

    @Test
    public void tooManyHours() {
        AdvanceTime command = new AdvanceTime("1,24");
        assertEquals("invalid-number-of-hours,24;", command.execute());
    }

    @Test
    public void advancesTime() {
        AdvanceTime command = new AdvanceTime("1,1;");
        LocalDateTime time = SystemDateTime.getInstance().getDateTime();
        command.execute();
        LocalDateTime newTime = SystemDateTime.getInstance().getDateTime();

        // 90000 seconds = 1 day 1 hour
        assertEquals(90000, newTime.toEpochSecond(ZoneOffset.UTC) - time.toEpochSecond(ZoneOffset.UTC));
    }
}
