package edu.ncsu.csc.itrust.unit.dao.ChildHospitalVisit;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ChildHospitalVisitTest extends TestCase {
    private DAOFactory factory;
    private long mid = 1L;
    private long hcpId = 9000000000L;
    TestDataGenerator gen;


    @Override
    protected void setUp() throws Exception {
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.standardData();

        this.factory = TestDAOFactory.getTestInstance();
    }

    public void testChildHospitalVisit1() throws Exception {
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
        
        a.setMagnesiumSulfate(2);
        a.setEpiduralAnaesthesia(2);
        a.setPethidine(2);
        a.setNitrousOxide(2);
        a.setPitocin(2);
        a.setRHImmuneGlobulin(2);
        
        test.editChildHospitalVisit(a);
        
        ChildHospitalVisitBean b = test.searchByPatientID(1, 13).get(0);
        assertEquals(b.getID(), id);
        assertEquals(b.getActualDeliveryType(), "vaginal delivery vacuum assist");
        assertEquals(b.getMagnesiumSulfate(), 2);
        assertEquals(b.getPreferredDeliveryType(), "vaginal delivery vacuum assist");
        assertEquals(b.getMagnesiumSulfate(), 2);
        assertEquals(b.getEpiduralAnaesthesia(), 2);
        assertEquals(b.getPethidine(), 2);
        assertEquals(b.getPitocin(), 2);
        assertEquals(b.getRHImmuneGlobulin(), 2);
    }


    public void testCheckChildHospitalVisit() throws DBException {
        ChildHospitalVisitDAO test = factory.getChildHospitalVisitDAO();
        boolean t = test.checkChildHospitalVisitExists(20);
        assertEquals(false, t);

    }
     
    public void testDeleteChildHospitalVisit() throws DBException {
        ChildHospitalVisitDAO test = TestDAOFactory.getTestInstance().getChildHospitalVisitDAO();
        test.delete(1, 1);
        assertEquals(0, test.searchByPatientID(1, 1).size());
    }

    
}
