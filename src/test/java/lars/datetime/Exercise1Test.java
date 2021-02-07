package lars.datetime;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

import static lars.datetime.LenientAssert.FIX_WITH_RIGHT_CALL;
import static lars.datetime.LenientAssert.assertAlmostEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Instant and Date interoperability.
 *
 * @see Instant
 * @see Date
 * @see LenientAssert
 */
public class Exercise1Test {

    private Date classicDate;
    private Instant java8Instant;
    private SimpleDateFormat classicDateFormatter = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");

    @BeforeEach
    public void setup() {

        classicDate = new Date();
        //-----------------------------------------
        java8Instant = Instant.now();
    }

    @Test
    public void instantToDate() {

        // *****************************************************
        // If the below conversion is not performed, the setup
        // assigns a value to the Instant some time after the
        // Date assignment, thus not being always equal.
        // In some tests below, we use an 'assertAlmostEquals()'
        // to circumvent this.
        // *****************************************************
        java8Instant = classicDate.toInstant();
    }

    @Test
    public void instantToMilliseconds() {

        // *****************************************************
        // Notice we use a tolerance when checking milliseconds.
        // We assigned the date and instant one after the other,
        // that could render a few millisecond difference. We
        // thus need to allow for a few milliseconds off.
        // *****************************************************

        // TODO: Assert that the Instant milliseconds form epoch almost equals the date milliseconds.
        // REPLACE the FIX_WITH_RIGHT_CALL below with a method call on Instant.
        //-----------------------------------------
        assertAlmostEqual("Date and Instant milliseconds should almost match", classicDate.getTime(), java8Instant.toEpochMilli(), 10);
    }

    @Test
    public void instantToSeconds() {

        // *****************************************************
        // Notice we use a tolerance when checking seconds.
        // We assigned the date and instant one after the other,
        // that could render a few milliseconds difference. We
        // thus need to allow for at least a second off.
        // *****************************************************

        long classicDateInSeconds = classicDate.getTime() / 1000;
        // TODO: Assert that the Instant seconds form epoch almost equals the date seconds from epoch calculation.
        // REPLACE the FIX_WITH_RIGHT_CALL below with a method call on Instant.
        assertAlmostEqual("Date and Instant seconds should almost match", classicDateInSeconds, java8Instant.getEpochSecond(), 1);
    }

    @Test
    public void instantHasNanoseconds() {

        // TODO: Assert that instant has nano seconds > 0
        //-----------------------------------------
         assertTrue(java8Instant.getNano() > 0, "Instant should have nanoseconds");
    }

    @Test
    public void instantStringFormatting() {

        classicDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        // TODO: Assert that instant default toString matches the ISO8601 full date format.
        // REPLACE the FIX_WITH_RIGHT_CALL below with a method call on Instant.
        assertEquals(classicDateFormatter.format(classicDate), java8Instant.toString(), "Instant toString() should match ISO8601 format");
    }

    @Test
    public void instantDateInteroperability() {

        // *****************************************************
        // Converting Date to an Instant.
        // *****************************************************

        // TODO: Verify interoperability between classic and java8 styles
        // REPLACE with conversion from Date to Instant below
        //-----------------------------------------
        Instant anotherInstant = classicDate.toInstant();
        //-----------------------------------------
        assertEquals(classicDate.getTime(), anotherInstant.toEpochMilli(), "The milliseconds from classic date and the anotherInstant should match");


        // *****************************************************
        // Converting an Instant to a Date.
        // *****************************************************

        // REPLACE with conversion from Instant to Date below
        //-----------------------------------------
        Date anotherDate = Date.from(java8Instant);
        //-----------------------------------------
        assertEquals(classicDate, anotherDate, "Classic date should match the converted Date");

        // *****************************************************
        // Think about why all conversions and inter-ops are
        // built into Date and not the java.time API.
        // *****************************************************
    }

}
