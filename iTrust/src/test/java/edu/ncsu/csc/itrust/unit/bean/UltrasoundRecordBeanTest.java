package edu.ncsu.csc.itrust.unit.bean;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UltrasoundRecordBeanTest extends TestCase{
    public void testUltrasoundRecordBean(){
        UltrasoundRecordBean bean= new UltrasoundRecordBean();
        bean.setID(123);
        bean.setPatientID(456);
        bean.setVisitID(789);
        LocalDateTime time1=LocalDateTime.of(2018, 5, 1,12,24,1);
        bean.setCreatedDate(time1);
        bean.setURL("a.a.a");
        bean.setCRL(1.1);
        bean.setEFW(1.2);
        bean.setBPD(1.3);
        bean.setAC(1.4);
        bean.setFL(1.5);
        bean.setHC(1.6);
        bean.setHL(1.7);
        bean.setOFD(1.8);


        assertEquals(123,bean.getID());
        assertEquals(456,bean.getPatientID());
        assertEquals(time1,bean.getCreatedDate());
        assertEquals("a.a.a",bean.getURL());
        assertEquals(1.1,bean.getCRL());
        assertEquals(1.2,bean.getEFW());
        assertEquals(1.3,bean.getBPD());
        assertEquals(1.4,bean.getAC());
        assertEquals(1.5,bean.getFL());
        assertEquals(1.6,bean.getHC());
        assertEquals(1.7,bean.getHL());
        assertEquals(1.8,bean.getOFD());


    }
}