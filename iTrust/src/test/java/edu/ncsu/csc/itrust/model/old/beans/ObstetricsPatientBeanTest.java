package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;

public class ObstetricsPatientBeanTest extends TestCase{
    public void testObstetricsPatientBean(){
        ObstetricsPatientBean bean= new ObstetricsPatientBean();
        bean.setID(123);
        bean.setPatientID(456);
        bean.setCreatedDate("10/09/2018");
        bean.setYearOfConception(2018);
        bean.setLMP("10/01/2018");
        bean.setNumberOfWeeksPreg("2");
        bean.setNumberOfLaborHour(12.0);
        bean.setDeliveryType("vaginal delivery");
        bean.setWeightGain(1.2);
        bean.setNumberofBaby(1);

        assertEquals(123,bean.getID());
        assertEquals(456,bean.getPatientID());
        assertEquals("10/09/2018",bean.getCreatedDate());
        assertEquals(2018,bean.getYearOfConception());
        assertEquals("10/01/2018",bean.getLMP());
        assertEquals("2",bean.getNumberOfWeeksPreg());
        assertEquals(12.0,bean.getNumberOfLaborHour());
        assertEquals("vaginal delivery",bean.getDeliveryType());
        assertEquals(1.2,bean.getWeightGain());
        assertEquals(1,bean.getNumberofBaby());


    }
}
