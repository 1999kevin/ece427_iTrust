package edu.ncsu.csc.itrust;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;


public class InputValidatorTest extends TestCase{
    public void testisNull(){
        String s=null;
        assertTrue(InputValidator.isNull(s));
    }

    public void testisEmpty(){
        String s="";
        assertTrue(InputValidator.isEmpty(s));
    }

    public void testisNullOrEmpty(){
        String s1=null;
        assertTrue(InputValidator.isNull(s1));

        String s2="";
        assertTrue(InputValidator.isEmpty(s2));
    }

    public void testisInteger(){
        String s="1234";
        assertTrue(InputValidator.isInteger(s));
    }

    public void testisDouble(){
        String s="0.001";
        assertTrue(InputValidator.isDouble(s));
    }
}
