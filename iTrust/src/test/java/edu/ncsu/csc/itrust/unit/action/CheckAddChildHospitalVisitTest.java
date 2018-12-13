package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.CheckChildHospitalVisitAction;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class CheckAddChildHospitalVisitTest extends TestCase {
    private TestDataGenerator gen = new TestDataGenerator();
    private DAOFactory factory = TestDAOFactory.getTestInstance();

    @Override
    public void setUp() throws Exception {
        gen.clearAllTables();
        gen.standardData();
    }

    public void testCheckCHV() throws Exception{
        CheckChildHospitalVisitAction act = new CheckChildHospitalVisitAction(factory);
        boolean f = act.checkForChildHospitalVisit(1);
        assertEquals(false,f);
    }
    
    public void testCheckandActionCHV() throws Exception{
    	ChildHospitalVisitBean p = new ChildHospitalVisitBean();
        p.setPatientID(1);
        p.setPreferredDeliveryType("vaginal delivery vacuum assist");
        p.setMagnesiumSulfate(1);
        p.setEpiduralAnaesthesia(1);
        p.setPethidine(1);
        p.setNitrousOxide(1);
        p.setPitocin(1);
        p.setActualDeliveryType("vaginal delivery vacuum assist");
        p.setRHImmuneGlobulin(1);
        p.setApptID(13);

        ChildHospitalVisitDAO test = factory.getChildHospitalVisitDAO();

        long id = test.addChildHospitalVisit(p);
        CheckChildHospitalVisitAction act = new CheckChildHospitalVisitAction(factory);
        boolean f = act.checkForChildHospitalVisit(13);
        assertEquals(true,f);
        
        ChildHospitalVisitBean a = test.searchByPatientID(1, 13).get(0);
        
        assertEquals(a.getID(), id);
        assertEquals(a.getActualDeliveryType(), "vaginal delivery vacuum assist");
        assertEquals(a.getMagnesiumSulfate(), 1);
        assertEquals(a.getPreferredDeliveryType(), "vaginal delivery vacuum assist");
        assertEquals(a.getMagnesiumSulfate(), 1);
        assertEquals(a.getEpiduralAnaesthesia(), 1);
        assertEquals(a.getPethidine(), 1);
        assertEquals(a.getPitocin(), 1);
        assertEquals(p.getRHImmuneGlobulin(), 1);
    }
}
