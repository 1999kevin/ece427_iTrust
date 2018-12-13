package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.CheckChildHospitalVisitAction;
import edu.ncsu.csc.itrust.action.SearchChildHospitalVisitAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.List;

public class SearchChildHospitalVisitActionTest extends TestCase {
    private TestDataGenerator gen = new TestDataGenerator();
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    private DAOFactory evil = EvilDAOFactory.getEvilInstance();
    //private PatientDAO evilDAO = EvilDAOFactory.getEvilInstance().getPatientDAO();
    private ChildHospitalVisitDAO evilDAO = EvilDAOFactory.getEvilInstance().getChildHospitalVisitDAO();


    @Override
    public void setUp() throws Exception {
        gen.clearAllTables();
        gen.standardData();
    }

    public void testsearchForObstetricsWithName() throws Exception{
        SearchChildHospitalVisitAction act = new SearchChildHospitalVisitAction(factory);
        List<ChildHospitalVisitBean> res = act.fuzzySearchForChildHospitalVisit(1,14);
        assertEquals("vaginal delivery",res.get(0).getActualDeliveryType());
    }

    public void testAddEmptyPatientException() throws Exception {
        try {
            evilDAO.addChildHospitalVisit(new ChildHospitalVisitBean());
            fail("DBException should have been thrown");
        } catch (DBException e) {
            assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }
}
