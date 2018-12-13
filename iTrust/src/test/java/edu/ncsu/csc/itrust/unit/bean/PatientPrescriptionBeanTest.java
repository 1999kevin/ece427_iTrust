package edu.ncsu.csc.itrust.unit.bean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientPrescriptionBeanTest extends TestCase{
    public void testPatientPrescriptionBean(){
        PatientPrescriptionBean bean= new PatientPrescriptionBean();
        bean.setID(123);
        bean.setPatientID(456);
        LocalDate time1=LocalDate.of(2018, 5, 1);
        bean.setCreatedDate(time1);
        bean.setName("Tom");
        bean.setNotes("good");
        bean.setDosage(1.2);


        assertEquals(123,bean.getID());
        assertEquals(456,bean.getPatientID());
        assertEquals(time1,bean.getCreatedDate());
        assertEquals("Tom",bean.getName());
        assertEquals("good",bean.getNotes());
        assertEquals(1.2,bean.getDosage());



    }
}
