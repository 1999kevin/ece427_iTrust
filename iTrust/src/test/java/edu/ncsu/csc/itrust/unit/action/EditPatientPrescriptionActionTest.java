package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.EditPatientPrescriptionAction;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.sql.Date;
import java.time.LocalDateTime;

public class EditPatientPrescriptionActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPatientPrescriptionAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		action = new EditPatientPrescriptionAction(factory, 9000000000L, "2");
	}


	public void testEditPatientPrescription() throws Exception {
		AuthDAO authDAO = factory.getAuthDAO();

		// Add a dependent
		PatientPrescriptionBean p = new PatientPrescriptionBean();
		p.setID(3);
		p.setPatientID(4);
		p.setCreatedDate(Date.valueOf("2018-11-11").toLocalDate());
		p.setName("med4");
		p.setNotes("test_edit");
		p.setDosage(4.4);
		action.editPatientPrescription(p);
		assertEquals(p.getID(), 3);
	}

	public void testDeletePatientPrescription() throws Exception {
		AuthDAO authDAO = factory.getAuthDAO();

		// Add a dependent
		PatientPrescriptionBean p = new PatientPrescriptionBean();
		p.setID(3);
		p.setPatientID(4);
		p.setCreatedDate(Date.valueOf("2018-11-11").toLocalDate());
		p.setName("med4");
		p.setNotes("test_edit");
		p.setDosage(4.4);
		action.deletePatientPrescription(3);
		assertEquals(p.getID(), 3);
	}
}
