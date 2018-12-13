package edu.ncsu.csc.itrust.unit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import junit.framework.TestCase;
import  edu.ncsu.csc.itrust.model.old.validate.PatientPrescriptionValidator;
public class PatientPrescriptionValidatorTest extends TestCase{
    public void testPatientPrescriptionValidator() throws Exception{
        PatientPrescriptionBean bean= new PatientPrescriptionBean();
        bean.setID(123);
        bean.setPatientID(456);
        LocalDate time1=LocalDate.of(2018, 5, 1);
        bean.setCreatedDate(time1);
        bean.setName("Tom");
        bean.setNotes("good");
        bean.setDosage(1.2);
        PatientPrescriptionValidator p=new PatientPrescriptionValidator();
        p.validate(bean);

    }
}
