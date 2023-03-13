package dev.osmocode.codehub.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinceFormaterTest {

    private final static long HOUR_IN_MILLISECOND = 3_600_000;
    private final static long DAY_IN_MILLISECOND = 24 * HOUR_IN_MILLISECOND;
    private final static long WEEK_IN_MILLISECOND = 7 * DAY_IN_MILLISECOND;
    private final static long MONTH_IN_MILLISECOND = 30 * DAY_IN_MILLISECOND;
    private final static long YEAR_IN_MILLISECOND = 12 * MONTH_IN_MILLISECOND;

    @Test
    public void lessThanOneHour() {
        assertEquals("0 hour", SinceFormater.process(0L));
    }

    @Test
    public void oneHourAndOneSecond() {
        assertEquals("1 hour", SinceFormater.process(HOUR_IN_MILLISECOND + 1));
    }

    @Test
    public void twoHours() {
        assertEquals("2 hours", SinceFormater.process(2 * HOUR_IN_MILLISECOND));
    }

    @Test
    public void lessThanOneDay() {
        assertEquals("23 hours", SinceFormater.process(DAY_IN_MILLISECOND - 1));
    }

    @Test
    public void _25HoursShouldBeOneDay() {
        assertEquals("1 day", SinceFormater.process(25 * HOUR_IN_MILLISECOND));
    }

    @Test
    public void oneDay() {
        assertEquals("1 day", SinceFormater.process(DAY_IN_MILLISECOND));
    }

    @Test
    public void twoDays() {
        assertEquals("2 days", SinceFormater.process(2 * DAY_IN_MILLISECOND));
    }

    @Test
    public void lessThanOneWeek() {
        assertEquals("6 days", SinceFormater.process(WEEK_IN_MILLISECOND - 1));
    }

    @Test
    public void heightDaysShouldBeOneWeek() {
        assertEquals("1 week", SinceFormater.process(8 * DAY_IN_MILLISECOND));
    }

    @Test
    public void oneWeek() {
        assertEquals("1 week", SinceFormater.process(WEEK_IN_MILLISECOND));
    }

    @Test
    public void fourWeeks() {
        assertEquals("4 weeks", SinceFormater.process(4 * WEEK_IN_MILLISECOND));
    }

    @Test
    public void fiveWeeksShouldBeOneMonth() {
        assertEquals("1 month", SinceFormater.process(5 * WEEK_IN_MILLISECOND));
    }

    @Test
    public void oneMonth() {
        assertEquals("1 month", SinceFormater.process(MONTH_IN_MILLISECOND));
    }

    @Test
    public void elevenMonths() {
        assertEquals("11 months", SinceFormater.process(11 * MONTH_IN_MILLISECOND));
    }

    @Test
    public void _13monthsShouldBeOneYear() {
        assertEquals("1 year", SinceFormater.process(13 * MONTH_IN_MILLISECOND));
    }

    @Test
    public void twoYears() {
        assertEquals("2 years", SinceFormater.process(2 * YEAR_IN_MILLISECOND));
    }

}