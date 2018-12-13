/**
 * Tests for AddPatientAction
 */

package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.AddPatientPrescriptionAction;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.sql.Date;
import java.time.LocalDateTime;

public class AddPatientPrescriptionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddPatientPrescriptionAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();

		gen.transactionLog();
		gen.hcp0();
		action = new AddPatientPrescriptionAction(factory);
	}

	/**
	 * Tests adding a new patient
	 * 
	 * @throws Exception
	 */
	public void testAddPatient() throws Exception {
		AuthDAO authDAO = factory.getAuthDAO();

		// Add a dependent
		PatientPrescriptionBean p = new PatientPrescriptionBean();
		p.setPatientID(3);
		p.setCreatedDate(Date.valueOf("2017-11-11").toLocalDate());
		p.setName("med3");
		p.setNotes("test_add");
		p.setDosage(3.3);
		action.addPatientPrescription(p);
		assertEquals(p.getPatientID(), 3);
	}

}
