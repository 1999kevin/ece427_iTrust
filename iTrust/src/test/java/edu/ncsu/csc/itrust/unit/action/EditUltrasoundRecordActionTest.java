package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.EditUltrasoundRecordAction;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.time.LocalDateTime;

public class EditUltrasoundRecordActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditUltrasoundRecordAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		action = new EditUltrasoundRecordAction(factory, 9000000000L, "2");
	}


	public void testAddPatient() throws Exception {
		AuthDAO authDAO = factory.getAuthDAO();

		// Add a dependent
		UltrasoundRecordBean p = new UltrasoundRecordBean();
		p.setID(2);
		p.setPatientID(3);
		p.setVisitID(3);
		p.setCreatedDate(LocalDateTime.of(2018, 5, 1,12,24,1));
		p.setURL("test update");
		p.setCRL(11.1);
		p.setBPD(22.2);
		p.setHC(33.3);
		p.setFL(44.4);
		p.setOFD(55.5);
		p.setAC(66.6);
		p.setHL(77.7);
		p.setEFW(88.8);
		action.updateInformation(p);
		assertEquals(p.getID(), 2);
	}

	public void testDeletePatient() throws Exception {
		action.deleteInforamtion(1);
	}
}
