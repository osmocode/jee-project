package dev.osmocode.codehub.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinceFormaterTest {

    private final static long HOUR_IN_MILLISECOND = 3_600_000;
    private final static long DAY_IN_MILLISECOND = 24 * HOUR_IN_MILLISECOND;
    private final static long WEEK_IN_MILLISECOND = 7 * DAY_IN_MILLISECOND;
    private final static long MONTH_IN_MILLISECOND = 30 * DAY_IN_MILLISECOND;
    private final static long YEAR_IN_MILLISECOND = 12 * MONTH_IN_MILLISECOND;
    
    private final SinceFormater sinceFormater = new SinceFormater();

    @Test
    public void lessThanOneHour() {
        assertEquals("0 hour", sinceFormater.process(0L));
    }

    @Test
    public void oneHourAndOneSecond() {
        assertEquals("1 hour", sinceFormater.process(HOUR_IN_MILLISECOND + 1));
    }

    @Test
    public void twoHours() {
        assertEquals("2 hours", sinceFormater.process(2 * HOUR_IN_MILLISECOND));
    }

    @Test
    public void lessThanOneDay() {
        assertEquals("23 hours", sinceFormater.process(DAY_IN_MILLISECOND - 1));
    }

    @Test
    public void _25HoursShouldBeOneDay() {
        assertEquals("1 day", sinceFormater.process(25 * HOUR_IN_MILLISECOND));
    }

    @Test
    public void oneDay() {
        assertEquals("1 day", sinceFormater.process(DAY_IN_MILLISECOND));
    }

    @Test
    public void twoDays() {
        assertEquals("2 days", sinceFormater.process(2 * DAY_IN_MILLISECOND));
    }

    @Test
    public void lessThanOneWeek() {
        assertEquals("6 days", sinceFormater.process(WEEK_IN_MILLISECOND - 1));
    }

    @Test
    public void heightDaysShouldBeOneWeek() {
        assertEquals("1 week", sinceFormater.process(8 * DAY_IN_MILLISECOND));
    }

    @Test
    public void oneWeek() {
        assertEquals("1 week", sinceFormater.process(WEEK_IN_MILLISECOND));
    }

    @Test
    public void fourWeeks() {
        assertEquals("4 weeks", sinceFormater.process(4 * WEEK_IN_MILLISECOND));
    }

    @Test
    public void fiveWeeksShouldBeOneMonth() {
        assertEquals("1 month", sinceFormater.process(5 * WEEK_IN_MILLISECOND));
    }

    @Test
    public void oneMonth() {
        assertEquals("1 month", sinceFormater.process(MONTH_IN_MILLISECOND));
    }

    @Test
    public void elevenMonths() {
        assertEquals("11 months", sinceFormater.process(11 * MONTH_IN_MILLISECOND));
    }

    @Test
    public void _13monthsShouldBeOneYear() {
        assertEquals("1 year", sinceFormater.process(13 * MONTH_IN_MILLISECOND));
    }

    @Test
    public void twoYears() {
        assertEquals("2 years", sinceFormater.process(2 * YEAR_IN_MILLISECOND));
    }

}