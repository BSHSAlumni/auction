package com.bshsalumni.auction.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtility {
    private DateTimeUtility(){}

    public static long getCurrentTimeUnix(){
        return DateTime.now().getMillis();
    }


    public static String getCurrentDateTimeISTString(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss.SSS");
        return dateTime.toString(fmt);
    }

}
