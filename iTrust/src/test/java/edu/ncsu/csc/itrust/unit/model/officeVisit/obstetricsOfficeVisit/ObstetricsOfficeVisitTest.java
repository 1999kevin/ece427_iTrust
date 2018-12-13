package edu.ncsu.csc.itrust.unit.model.officeVisit.obstetricsOfficeVisit;


import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.apptType.ApptType;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeData;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeMySQLConverter;
import edu.ncsu.csc.itrust.model.hospital.Hospital;
import edu.ncsu.csc.itrust.model.hospital.HospitalData;
import edu.ncsu.csc.itrust.model.hospital.HospitalMySQLConverter;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ObstetricsOfficeVisitTest extends TestCase {
	private ObstetricsOfficeVisit test;
	private ApptTypeData apptData;
	private TestDataGenerator gen;
	private DataSource ds;

	@Override
	protected void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		test = new ObstetricsOfficeVisit();
		apptData = new ApptTypeMySQLConverter(ds);
		gen = new TestDataGenerator();

	}

	@Test
	public void testApptTypeID() throws DBException, FileNotFoundException, IOException, SQLException {
		gen.appointmentType();
		List<ApptType> types = apptData.getAll();
		long apptTypeID = types.get((types.size() - 1)).getID();
		test.setApptTypeID(apptTypeID);
		long testID = test.getApptTypeID();
		Assert.assertEquals(apptTypeID, testID);
	}

	@Test
	public void testsetLMP() {
		LocalDateTime testTime = LocalDateTime.now();
		test.setLMP(testTime);
		Assert.assertTrue(ChronoUnit.MINUTES.between(testTime, test.getLMP()) < 1);
	}

	@Test
	public void testLocationID() throws FileNotFoundException, SQLException, IOException, DBException {
		gen.hospitals();
		HospitalData hData = new HospitalMySQLConverter(ds);
		List<Hospital> all = hData.getAll();
		String id = all.get(0).getHospitalID();
		test.setLocationID(id);
		Assert.assertEquals(id, test.getLocationID());
	}

	/**
	 * Tests notes methods.
	 */
	@Test
	public void testNotes() {
		String note = "ABCDEF123><$%> ";
		test.setNotes(note);
		Assert.assertEquals(note, test.getNotes());
	}

	/**
	 * Tests createdate methods.
	 */
	@Test
	public void testCreatedate() {
		LocalDateTime time1 = LocalDateTime.of(2018, 4, 20, 12, 24, 1);
		test.setCreatedDate(time1);
		Assert.assertEquals(time1, test.getCreatedDate());
	}

	/**
	 * Tests patientID methods.
	 */
	@Test
	public void testPatientID() throws FileNotFoundException, IOException, SQLException {
		gen.patient1();
		test.setPatientID(1);
		Assert.assertEquals(1, test.getPatientID());
	}

	/**
	 * Tests ID methods.
	 */
	@Test
	public void testID() {
		test.setID(1L);
		long check = test.getID();
		Assert.assertEquals(1L, check);
	}

	/**
	 * Tests height methods.
	 */
	@Test
	public void testHeight() {
		test.setWeightInPounds(1F);
		double check = test.getWeightInPounds();
		Assert.assertEquals(1F, check, .01);
	}

	/**
	 * Tests BloodPressure.
	 */
	@Test
	public void testBloodpressure() {
		test.setBloodPressure("140/90");
		String check = test.getBloodPressure();
		Assert.assertEquals("140/90", check);
	}

	/**
	 * Tests FHR.
	 */
	@Test
	public void testFHR() {
		test.setFHR(1);
		int check = test.getFHR();
		Assert.assertEquals(1, check);
	}

	/**
	 * Tests Numberofbaby methods.
	 */
	@Test
	public void testNumberofbaby() {
		test.setNumberOfBaby(2);
		int check = test.getNumberOfBaby();
		Assert.assertEquals(2, check);
	}

	/**
	 * Tests LowLyingPlacenta methods.
	 */
	@Test
	public void testLowLyingPlacenta() {
		test.setLowLyingPlacenta(false);
		boolean check = test.isLowLyingPlacenta();
		Assert.assertEquals(false, check);
	}

	/**
	 * Tests Sendbill methods.
	 */
	@Test
	public void testSendBill() {
		test.setSendBill(true);
		boolean check = test.isSendBill();
		Assert.assertEquals(true, check);
	}

}