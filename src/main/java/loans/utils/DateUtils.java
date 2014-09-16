package loans.utils;

import java.util.Calendar;

public class DateUtils {


    private static Calendar calendarForTests = null;

    public static Calendar getCalendarInstance() {
        if (null != calendarForTests) {
            return (Calendar) calendarForTests.clone();
        }
        return Calendar.getInstance();
    }

    public static void setCalendarInstance(final Calendar calendarForFITs) {
        calendarForTests = calendarForFITs;
    }
}
