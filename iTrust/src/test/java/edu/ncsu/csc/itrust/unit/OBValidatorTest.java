package edu.ncsu.csc.itrust.unit;

import org.junit.Before;
import org.junit.Test;
import java.text.*;
import java.util.Date;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import  edu.ncsu.csc.itrust.ObstetricsValidator;

public class OBValidatorTest {
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void testValid() {
        ObstetricsValidator  validator = new ObstetricsValidator();
        validator.isValidYear("2000");
        //validator.isDouble("1.2");
        Date now=new Date();
        validator.isValidDate(now.toString(),"yy/MM/dd HH:mm");

        String record = "Tue Oct 30 12:22:58 CDT 2018";
        String lmp = "Tue Oct 10 12:22:58 CDT 2018";

        validator.isValidLMP(record,lmp);
        validator.isValidDeliveryType("vaginal delivery");

    }


}
