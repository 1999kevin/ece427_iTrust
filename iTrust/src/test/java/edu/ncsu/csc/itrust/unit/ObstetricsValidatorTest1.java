package edu.ncsu.csc.itrust.unit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import  edu.ncsu.csc.itrust.ObstetricsValidator;
public class ObstetricsValidatorTest1 extends TestCase{

    public static String DEFAULT_DATE_PATTERN = "MM/dd/yyyy";

    public void testisValidYear() throws Exception{
        String s="2000";
        assertTrue(ObstetricsValidator.isValidYear(s));
    }

    public void testisValidDate() throws Exception{
        String s="10/01/2018";
        assertTrue(ObstetricsValidator.isValidDate(s,DEFAULT_DATE_PATTERN));
    }

    public void testisValidLMP() throws Exception{
        String record = "10/10/2018";
        String LMP = "10/01/2018";
        assertTrue(ObstetricsValidator.isValidLMP(record, LMP));
    }

    public void testisValidDeliveryType(){
        String s = "vaginal delivery vacuum assist";
        assertTrue(ObstetricsValidator.isValidDeliveryType(s));
    }

    public void testgetDiffInDays(){
        String record = "10/10/2018";
        String LMP = "10/01/2018";
        assertEquals(9,ObstetricsValidator.getDiffInDays(record,LMP,DEFAULT_DATE_PATTERN));
    }
}
