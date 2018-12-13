/**
 * Tests for AddPatientAction
 */

package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.AddUltrasoundRecordAction;
import edu.ncsu.csc.itrust.action.EditUltrasoundRecordAction;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.time.LocalDateTime;

public class AddUltrasoundRecordTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddUltrasoundRecordAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();

		gen.transactionLog();
		gen.hcp0();
		action = new AddUltrasoundRecordAction(factory);
	}

	/**
	 * Tests adding a new patient
	 * 
	 * @throws Exception
	 */
	public void testAddPatient() throws Exception {
		AuthDAO authDAO = factory.getAuthDAO();

		// Add a dependent
		UltrasoundRecordBean p = new UltrasoundRecordBean();
		p.setPatientID(5);
		p.setVisitID(3);
		p.setCreatedDate(LocalDateTime.of(2018, 5, 1,12,24,1));
		p.setURL("test add1");
		p.setCRL(11.1);
		p.setBPD(22.2);
		p.setHC(33.3);
		p.setFL(44.4);
		p.setOFD(55.5);
		p.setAC(66.6);
		p.setHL(77.7);
		p.setEFW(88.8);
		action.addUltrasoundRecord(p);
		assertEquals(p.getPatientID(), 5);
	}

}
