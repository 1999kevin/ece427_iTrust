package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.AddDeliveryAction;
import edu.ncsu.csc.itrust.action.SearchChildHospitalVisitAction;
import edu.ncsu.csc.itrust.action.SearchDeliveryAction;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.List;

public class SearchDeliveryActionTest extends TestCase {
    private TestDataGenerator gen = new TestDataGenerator();
    private DAOFactory factory = TestDAOFactory.getTestInstance();

    @Override
    public void setUp() throws Exception {
        gen.clearAllTables();
        gen.standardData();
    }

    public void testsearchForObstetricsWithName() throws Exception{
    	AddDeliveryAction action = new AddDeliveryAction(this.factory, 9000000000L);
    	DeliveryBean deliveryBean = new DeliveryBean();
        deliveryBean.setMotherID(12);
        deliveryBean.setPatientID(2);
        deliveryBean.setMotherID(1);
        deliveryBean.setSex("Male");
        deliveryBean.setChildHospitalVisitID(1);
        deliveryBean.setDeliverDate("2018-11-13");
        deliveryBean.setDeliverTime("08:00 A.M");
        action.addDelivery(deliveryBean);
        
    	SearchDeliveryAction act = new SearchDeliveryAction(factory);
        List<DeliveryBean> res = act.searchByMotherID(1);
        assertEquals("2018-11-13",res.get(0).getDeliverDate());
    }
}
