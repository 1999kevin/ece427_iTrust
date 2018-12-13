package edu.ncsu.csc.itrust;
import edu.ncsu.csc.itrust.helper.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ObstetricsValidator {
    public static String DEFAULT_DATE_PATTERN = "MM/dd/yyyy";

    public static String[] DELIVERY_TYPES = new String[] {"vaginal delivery", "vaginal delivery vacuum assist", "vaginal delivery forceps assist", "caesarean section", "miscarriage"};

    public static boolean isValidYear(String s) {
        if(!InputValidator.isInteger(s)){
            return false;
        }
        int year = Integer.parseInt(s);
        return year >= 1900 && year <= 2050;
    }

    public static boolean isValidDate(String s, String pattern) {
        if(InputValidator.isNullOrEmpty(s)){
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try{
            Date date = formatter.parse(s);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidLMP(String record, String lmp) {
        long diffInDays = getDiffInDays(record, lmp, DEFAULT_DATE_PATTERN);
        return diffInDays >= 0 && diffInDays <= 280;
    }

    public static long daysOfPreg(String record, String lmp) {
        long diffInDays = getDiffInDays(record, lmp, DEFAULT_DATE_PATTERN);
        return diffInDays;
    }

    public static long getDiffInDays(String record, String lmp, String pattern) {
        if(!isValidDate(record, pattern) || !isValidDate(lmp, pattern)){
            return -1;
        }
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date recordDate = formatter.parse(record);
            Date lmpDate = formatter.parse(lmp);
            long diffInMillies = recordDate.getTime() - lmpDate.getTime();
            if(diffInMillies < 0){
                return -1;
            }
            long diff = diffInMillies / 86400000;
            return diff;
        } catch (ParseException ex) {
            return -1;
        }
    }

    public static String getNumOfWeeksPreg(String record, String lmp) {
        if(!isValidLMP(record, lmp)){
            return "N/A";
        }
        long diffInDays = getDiffInDays(record, lmp, DEFAULT_DATE_PATTERN);
        long weeks = diffInDays / 7;
        long days = diffInDays % 7;
        return (weeks > 9 ? "" + weeks : "0" + weeks) + "-" + days;
    }

    public static int getNumOfWeeksPregnant(String record, String lmp) {
        String weeksAndDays = getNumOfWeeksPreg(record, lmp);
        return Integer.parseInt(weeksAndDays.substring(0, 2));
    }

    public static boolean isValidDeliveryType(String s) {
        if(InputValidator.isNullOrEmpty(s)){
            return false;
        }
        for(String type: DELIVERY_TYPES){
            if(s.equals(type)){
                return true;
            }
        }
        return false;
    }

    public static boolean eligibleForObsVisit(String latestLMP, String today) {
        // no LMP provided
        if(latestLMP == null || latestLMP.equals("")){
            return false;
        }
        long diffInDays = DateHelper.diffInDays(latestLMP, today);
        // date format mismatched
        if(diffInDays == -1) {
            return false;
        }
        // not within 49 weeks 0 days
        if (diffInDays >= 0 && diffInDays <= 49 * 7) {
            return true;
        }
        return false;
    }
}
