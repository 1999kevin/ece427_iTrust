package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.ObstetricsValidator;
import edu.ncsu.csc.itrust.action.AddApptAction;
import edu.ncsu.csc.itrust.action.AddDeliveryAction;
import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.action.SearchDeliveryAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.DeliveryDAO;
import edu.ncsu.csc.itrust.model.old.validate.DeliveryValidator;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.Date;
import java.util.List;

public class AddDeliveryActionTest extends TestCase {
    private AddDeliveryAction action;
    private DAOFactory factory;
    private long mid = 1L;
    private long hcpId = 9000000000L;
    TestDataGenerator gen;
    private AddPatientAction patientAction;
    private DeliveryDAO evilDAO = EvilDAOFactory.getEvilInstance().getDeliveryDAO();

    @Override
    protected void setUp() throws Exception {
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.standardData();

        this.factory = TestDAOFactory.getTestInstance();
        this.action = new AddDeliveryAction(this.factory, this.hcpId);
        this.patientAction = new AddPatientAction(this.factory, this.hcpId);
    }

    //public void testDeliveryAdd() throws FormValidationException, ITrustException {
    public void testGetEmptyPatient() throws Exception {
        try {
        	DeliveryBean d = new DeliveryBean();
        	d.setSex("Man");
            action.addDelivery(d);
            fail("exception should have been thrown");
        } catch (FormValidationException e) {
            assertEquals("This form has not been validated correctly. The following field are not properly filled in: [This form has not been validated correctly. The following field are not properly filled in: [sex: Only Male, Female, or All Patients]]", e.getMessage());
        }
    }


    public void testDeliveryAdd() throws Exception{
        DeliveryBean deliveryBean = new DeliveryBean();
        deliveryBean.setMotherID(12);
        deliveryBean.setPatientID(2);
        deliveryBean.setMotherID(1);
        deliveryBean.setSex("Male");
        deliveryBean.setChildHospitalVisitID(1);
        deliveryBean.setDeliverDate("2018-11-13");
        deliveryBean.setDeliverTime("08:00 A.M");

        long id = action.addDelivery(deliveryBean);
        SearchDeliveryAction act = new SearchDeliveryAction(factory);
        List<DeliveryBean> res = act.searchByMotherID(1);
        assertEquals("2018-11-13",res.get(0).getDeliverDate());

        DeliveryValidator validator = new DeliveryValidator();
        validator.validate(deliveryBean);
        //validator.isDouble("1.2");
    }
    public void testAddEmptyDeliverException() throws Exception {
        try {
        	DeliveryBean d = new DeliveryBean();
        	d.setSex("Man");
            evilDAO.addDelivery(d);
            fail("DBException should have been thrown");
        } catch (DBException e) {
            assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }
}
