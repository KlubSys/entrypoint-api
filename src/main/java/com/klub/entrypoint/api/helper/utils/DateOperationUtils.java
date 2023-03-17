package com.klub.entrypoint.api.helper.utils;

import java.util.Calendar;
import java.util.Date;

public class DateOperationUtils {

    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, days);
        return calendar.getTime();
    }
}
