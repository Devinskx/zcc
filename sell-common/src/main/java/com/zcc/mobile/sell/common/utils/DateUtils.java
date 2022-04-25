package com.zcc.mobile.sell.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return dateFormat.format(date);
        } else {
            return "";
        }
    }


    public static Date stringToDate(String string) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            if (string != null && !string.equals("")) {
                date = dateFormat.parse(string);
            }
        } catch (Exception e) {
            log.error("string to date", e);
        }
        return date;
    }
}
