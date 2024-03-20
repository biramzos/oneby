package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.Month;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getStringDateTimeFromDateTime(LocalDateTime localDateTime, int language){
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        return day + " " + Month.fromId(month).getName(language) + " "
                + year + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    public static String modifyStringDateForDocument(String str, int language) {
        LocalDate date = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return "«" + date.getDayOfMonth() + "» " + Month.fromId(date.getMonthValue()).getName(language) + " " + date.getYear();
    }

    public static String getStringDateFromDate(LocalDate localDate, int language){
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return day + " " + Month.fromId(month).getName(language) + " " + year;
    }

}
