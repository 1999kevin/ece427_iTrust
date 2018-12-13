package edu.ncsu.csc.itrust.helper;
import edu.ncsu.csc.itrust.Constants;
import org.apache.james.mime4j.field.datetime.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateHelper {
    public static long diffInDays(String dateString1, String dateString2) {
        return diffInDays(dateString1, dateString2, Constants.DEFAULT_DATE_PATTERN);
    }

    public static Date add(Date date, int amount, String unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(unit.equals("weeks")) { //TODO: Handle other unit
            calendar.add(Calendar.WEEK_OF_YEAR, amount);
        }
        if(unit.equals("days")) { //TODO: Handle other unit
            calendar.add(Calendar.DATE, amount);
        }
        date = calendar.getTime();
        return date;
    }

    public static long diffInDays(String dateString1, String dateString2, String pattern) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date1 = formatter.parse(dateString1);
            Date date2 = formatter.parse(dateString2);
            return diffInDays(date1, date2);
        } catch (ParseException ex) {
            return -1;
        }
    }

    public static long diffInDays(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        if(diffInMillies < 0){
            return -1;
        }
        long diff = diffInMillies / 86400000; // 1000ms * 60s * 60min * 24h
        return diff;
    }

    public static String latest(List<String> dateStrings) {
        if(dateStrings == null || dateStrings.size() == 0) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT_DATE_PATTERN);
        List<Date> dates = new ArrayList<>();
        try{
            for(String s: dateStrings) {
                dates.add(formatter.parse(s));
            }
            Date result = dates.get(0);
            for(Date d: dates) {
                if(d.after(result)){
                    result = d;
                }
            }
            return formatter.format(result);
        } catch (Exception e) {
            return "";
        }
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
