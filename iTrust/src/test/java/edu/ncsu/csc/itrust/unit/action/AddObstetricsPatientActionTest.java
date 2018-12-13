/**
 * Tests for AddPatientAction
 */

package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.AddObstetricsPatientAction;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddObstetricsPatientActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddObstetricsPatientAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();

		gen.transactionLog();
		gen.hcp0();
		action = new AddObstetricsPatientAction(factory);
	}

	/**
	 * Tests adding a new patient
	 * 
	 * @throws Exception
	 */
	public void testAddPatient() throws Exception {
		AuthDAO authDAO = factory.getAuthDAO();

		// Add a dependent
		ObstetricsPatientBean p = new ObstetricsPatientBean();
		p.setPatientID(1);
		p.setCreatedDate("10/10/2018");
		p.setLMP("10/10/2017");
		p.setNumberofBaby(1);
		p.setNumberOfLaborHour(1);
		p.setNumberOfWeeksPreg("22-3");
		p.setWeightGain(2.2);
		p.setYearOfConception(2017);
		p.setDeliveryType("vaginal delivery");
		long newID = action.addObstetricsPatient(p);
		assertEquals(p.getID(), newID);

	}
}
