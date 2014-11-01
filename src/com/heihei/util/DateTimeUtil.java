
package com.heihei.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static final SimpleDateFormat DF_WEEK = new SimpleDateFormat("E");
    public static final SimpleDateFormat DF_YEAR_MONTH_DAY = new SimpleDateFormat("yyyy年M月d日");
    public static final SimpleDateFormat DF_MONTH_DAY = new SimpleDateFormat("M月d日");
    public static final SimpleDateFormat DF_FULL_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DF_HOUR_MIN = new SimpleDateFormat("HH:mm");

    private static final long ONE_DAY = 3600 * 24 * 1000;
    private static final long TWO_DAY = 2 * ONE_DAY;
    private static final long ONE_WEEK = 7 * ONE_DAY;
    private static final long ONE_YEAR = 365 * ONE_DAY;

    public static String getTimeDisplay(long milliseconds) {
        long now = System.currentTimeMillis();
        long delta = now - milliseconds;
        if (delta < ONE_DAY) {
            Date d = new Date();
            d.setTime(milliseconds);
            return DF_HOUR_MIN.format(d);
        } else if (delta < TWO_DAY) {
            return "昨天";
        } else if (delta < ONE_WEEK) {
            Date d = new Date();
            d.setTime(milliseconds);
            return DF_WEEK.format(d);
        } else if (delta < ONE_YEAR) {
            Date d = new Date();
            d.setTime(milliseconds);
            return DF_MONTH_DAY.format(d);
        }

        Date d = new Date();
        d.setTime(milliseconds);
        return DF_YEAR_MONTH_DAY.format(d);
    }

    public static String getDayInWeekDisplay(long milliseconds) {
        Date d = new Date();
        d.setTime(milliseconds);
        return DF_WEEK.format(d);
    }

    public static String getMonthDay(long milliseconds) {
        Date d = new Date();
        d.setTime(milliseconds);
        return DF_MONTH_DAY.format(d);
    }

    public static String getYearMonthDay(long milliseconds) {
        Date d = new Date();
        d.setTime(milliseconds);
        return DF_YEAR_MONTH_DAY.format(d);
    }
}
