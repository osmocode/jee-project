package dev.osmocode.codehub.utils;

public class SinceFormater {

    private final static long HOUR_IN_MILLISECOND = 3_600_000;
    private final static long DAY_IN_MILLISECOND = 24 * HOUR_IN_MILLISECOND;
    private final static long WEEK_IN_MILLISECOND = 7 * DAY_IN_MILLISECOND;
    private final static long MONTH_IN_MILLISECOND = 30 * DAY_IN_MILLISECOND;
    private final static long YEAR_IN_MILLISECOND = 12 * MONTH_IN_MILLISECOND;

    public static String formatSince(long timestamp) {
        return process(System.currentTimeMillis() - timestamp);
    }

    // No private for tests
    static String process(long elapsed) {
        if (elapsed < DAY_IN_MILLISECOND) {
            long nbHour = elapsed / HOUR_IN_MILLISECOND;
            return nbHour + " hour" + (nbHour > 1 ? "s" : "");
        }
        if (elapsed < WEEK_IN_MILLISECOND) {
            long nbDay = elapsed / DAY_IN_MILLISECOND;
            return nbDay + " day" + (nbDay > 1 ? "s" : "");
        }
        if (elapsed < MONTH_IN_MILLISECOND) {
            long nbWeek = elapsed / WEEK_IN_MILLISECOND;
            return nbWeek + " week" + (nbWeek > 1 ? "s" : "");
        }
        if (elapsed < YEAR_IN_MILLISECOND) {
            long nbMonth = elapsed / MONTH_IN_MILLISECOND;
            return nbMonth + " month" + (nbMonth > 1 ? "s" : "");
        }
        long nbYear = elapsed / YEAR_IN_MILLISECOND;
        return nbYear + " year" + (nbYear > 1 ? "s" : "");
    }
}
