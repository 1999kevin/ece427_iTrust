package edu.ncsu.csc.itrust;

public class InputValidator {
    public static boolean isNull(String s){
        return s == null;
    }

    public static boolean isEmpty(String s) {
        return s.isEmpty();
    }

    public static boolean isNullOrEmpty(String s) {
        return isNull(s) || isEmpty(s);
    }

    public static boolean isInteger(String s){
        if(isNullOrEmpty(s)) {
            return false;
        }
        try{
            int num = Integer.parseInt(s);
        }catch (NumberFormatException ex){
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s){
        if(isNullOrEmpty(s)) {
            return false;
        }
        try{
            double num = Double.valueOf(s);
        }catch (NumberFormatException ex){
            return false;
        }
        return true;
    }
}
