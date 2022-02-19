package com.jpro.DatabaseManagementGUI.Utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jeremy Hennessy
 *
 * Time Conversion Class
 */
public class TimeConversion {
    /**
     * Converts a date from local time to UTC
     *
     * @param date date to be converted to UTC
     * @return returns the date as UTC
     */
    public static LocalDateTime toUTC(LocalDateTime date){
       ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
       ZonedDateTime utc = zdt.withZoneSameInstant(ZoneOffset.UTC);
       return utc.toLocalDateTime();
    }

    /**
     * Formats a date
     *
     * @param date date to be formatted
     * @return a formatted string of the date passed in
     */
    public static String formatDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy  HH:mm");
        return formatter.format(date);
    }

}
