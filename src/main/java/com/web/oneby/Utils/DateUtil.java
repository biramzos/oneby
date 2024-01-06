package com.web.oneby.Utils;

import com.web.oneby.Enums.Month;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

    public static String getStringDateTimeFromDateTime(LocalDateTime localDateTime, int language){
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        return day + " " + Month.fromId(month).getName(language) + " "
                + year + " " + hour + ":" + minute;
    }

    public static String getStringDateFromDate(LocalDate localDate, int language){
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return day + " " + Month.fromId(month).getName(language) + " " + year;
    }

}
