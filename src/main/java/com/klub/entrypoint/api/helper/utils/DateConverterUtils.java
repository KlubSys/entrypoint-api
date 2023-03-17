package com.klub.entrypoint.api.helper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverterUtils {

    private static final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

    public static Date fromDefaultFormat(String value) throws ParseException {
        return s.parse(value);
    }

    public static  String toDefaultFormat(Date date){
        return s.format(date);
    }
}
