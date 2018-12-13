package edu.ncsu.csc.itrust;

public class childVisitValidotor {

    public static boolean isIntValid(String in){
        if (!InputValidator.isInteger(in)){
            return false;
        }
        else
            return true;
    }
}
