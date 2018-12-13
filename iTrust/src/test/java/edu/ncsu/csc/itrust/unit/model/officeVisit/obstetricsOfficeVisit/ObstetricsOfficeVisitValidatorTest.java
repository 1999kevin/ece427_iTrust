package edu.ncsu.csc.itrust.unit.model.officeVisit.obstetricsOfficeVisit;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.apptType.ApptType;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeData;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeMySQLConverter;
import edu.ncsu.csc.itrust.model.hospital.Hospital;
import edu.ncsu.csc.itrust.model.hospital.HospitalData;
import edu.ncsu.csc.itrust.model.hospital.HospitalMySQLConverter;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitValidator;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class ObstetricsOfficeVisitValidatorTest extends TestCase {
	private ApptTypeData apptData;
	private TestDataGenerator gen;
	private DataSource ds;
	private HospitalData hData;
	private ObstetricsOfficeVisitValidator validator;

	@Override
	protected void setUp() throws Exception {

		ds = ConverterDAO.getDataSource();
		apptData = new ApptTypeMySQLConverter(ds);
		hData = new HospitalMySQLConverter(ds);
		gen = new TestDataGenerator();
		validator = new ObstetricsOfficeVisitValidator(ds);
		gen.clearAllTables();

	}
	@Test
	public void testValidOfficeVisit() throws FileNotFoundException, IOException, SQLException, DBException{
		gen.patient1();
		gen.appointmentType();
		ObstetricsOfficeVisit ov = new ObstetricsOfficeVisit();
		List<ApptType> apptList = apptData.getAll();
		if(apptList.size()>0){
			ov.setApptTypeID(apptList.get(0).getID());
		}
		ov.setCreatedDate(LocalDateTime.now());
		gen.hospitals();
		List<Hospital> hList = hData.getAll();
		ov.setLocationID(hList.get(hList.size()-1).getHospitalID());
		ov.setNotes("Hello World!");
		ov.setSendBill(false);
		ov.setPatientID(1L);
		ov.setFHR(123);
		ov.setBloodPressure("140/80");
		ov.setLowLyingPlacenta(true);
		ov.setWeightInPounds(1.1);
		LocalDateTime time1 = LocalDateTime.of(2018, 4, 20, 12, 24, 1);
		ov.setLMP(time1);
		LocalDateTime time2 = LocalDateTime.of(2018, 3, 20, 12, 24, 1);
		ov.setCreatedDate(time2);
		ov.setNumberOfBaby(2);
		try{
			validator.validate(ov);
			Assert.assertTrue(true);
		}
		catch(FormValidationException f){
			Assert.fail("Should be no error!");

		}

	}

	@Test
	public void testInvalidMID() throws FileNotFoundException, IOException, SQLException, DBException{
		gen.patient1();
		gen.appointmentType();
		ObstetricsOfficeVisit ov = new ObstetricsOfficeVisit();
		List<ApptType> apptList = apptData.getAll();
		if(apptList.size()>0){
			ov.setApptTypeID(apptList.get(0).getID());
		}
		ov.setCreatedDate(LocalDateTime.now());
		gen.hospitals();
		List<Hospital> hList = hData.getAll();
		ov.setLocationID(hList.get(hList.size()-1).getHospitalID());
		ov.setNotes("Hello World!");
		ov.setSendBill(false);
		ov.setPatientID(9000000000L);
		try{
			validator.validate(ov);
			Assert.fail("No error thrown");
		}
		catch(FormValidationException f){
			Assert.assertTrue(true); //error thrown

		}

	}
	public void testInvalidApptID() throws FileNotFoundException, IOException, SQLException, DBException{
		ObstetricsOfficeVisit ov = new ObstetricsOfficeVisit();
		ov.setApptTypeID(10L);
		ov.setCreatedDate(LocalDateTime.now());
		gen.patient1();
		gen.hospitals();
		List<Hospital> hList = hData.getAll();
		ov.setLocationID(hList.get(hList.size()-1).getHospitalID());
		ov.setNotes("Hello World!");
		ov.setSendBill(false);
		ov.setPatientID(1L);
		try{
			validator.validate(ov);
			Assert.fail("No error thrown");
		}
		catch(FormValidationException f){
			Assert.assertTrue(true); //error thrown

		}

	}
	@Test
	public void testSetInvalidHospital() throws FileNotFoundException, IOException, SQLException, DBException {
		gen.clearAllTables();
		gen.hospitals();
		String hID = "-1";
		gen.patient1();
		gen.appointmentType();

		ObstetricsOfficeVisit ov = new ObstetricsOfficeVisit();
		List<ApptType> apptList = apptData.getAll();
		if(apptList.size()>0){
			ov.setApptTypeID(apptList.get(0).getID());
		}
		ov.setCreatedDate(LocalDateTime.now());

		ov.setLocationID(hID);
		ov.setNotes("Hello World!");
		ov.setSendBill(false);
		ov.setPatientID(1L);

		try{
			validator.validate(ov);
			Assert.fail("No error thrown");
		}
		catch(FormValidationException f){
			Assert.assertTrue(true); //error thrown

		}

	}

}
