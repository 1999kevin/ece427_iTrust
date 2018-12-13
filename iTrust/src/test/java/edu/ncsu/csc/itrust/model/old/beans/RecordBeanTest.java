package edu.ncsu.csc.itrust.model.old.beans;

import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import junit.framework.TestCase;
import junit.framework.TestCase;

public class RecordBeanTest extends TestCase {

    public void testRecordBean(){
        RecordBean bean= new RecordBean();
        bean.setBloodType(BloodType.NS);
        assertEquals("N/S", bean.getBloodType());

    }

}
