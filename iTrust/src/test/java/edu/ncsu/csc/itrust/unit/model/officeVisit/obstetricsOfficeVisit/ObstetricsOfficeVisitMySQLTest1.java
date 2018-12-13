package edu.ncsu.csc.itrust.unit.model.officeVisit.obstetricsOfficeVisit;

//import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Connection;

public class ObstetricsOfficeVisitMySQLTest1 extends TestCase {
	
	private DataSource ds;
	private ObstetricsOfficeVisitMySQL ovsql;
	
	@Override
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		ovsql = new ObstetricsOfficeVisitMySQL(ds);
	}

	@Test
	public void testupdate() throws Exception {
		ObstetricsOfficeVisit oov= new ObstetricsOfficeVisit();
		oov.setID(1);
		oov.setPatientID(1);
		oov.setApptTypeID(7);
		oov.setBloodPressure("90/120");
		oov.setFHR(10);
		oov.setLocationID("1");
		oov.setLowLyingPlacenta(false);
		oov.setNumberOfBaby(1);
		oov.setNotes("good");
		oov.setSendBill(true);
		oov.setWeightInPounds(123);
		LocalDateTime time1=LocalDateTime.of(2018, 5, 1,12,24,1);
		oov.setCreatedDate(time1);
		LocalDateTime time2=LocalDateTime.of(2018, 4, 20,12,24,1);
		oov.setLMP(time2);
//		mockOvsql = new ObstetricsOfficeVisitMySQL(ds);
		assertTrue(ovsql.update(oov));
//		Connection conn = mockDataSource.getConnection();
//		if (conn == null) {
//			assertTrue(false);
//		}
//		assertTrue(true);
	}

	@Test
	public void testdelete() throws Exception {

//		mockOvsql = new ObstetricsOfficeVisitMySQL(ds);
		assertTrue(ovsql.delete(ovsql.getVisitsForPatient(2L).get(0).getID()));
//		Connection conn = mockDataSource.getConnection();
//		if (conn == null) {
//			assertTrue(false);
//		}
//		assertTrue(true);
	}

	@Test
	public void testAddObVisitsForPatient() throws Exception{
		LocalDateTime localdatetime = LocalDateTime.now();
		ObstetricsOfficeVisit ov = new ObstetricsOfficeVisit();
		ov.setPatientID(2);
		ov.setCreatedDate(localdatetime.now());
		ov.setLocationID("2");
		ov.setApptTypeID(7);
		ov.setNotes("this is for test");
		ov.setSendBill(false);
		ov.setLMP(localdatetime.now());
		ov.setWeightInPounds(11.0);
		ov.setBloodPressure("80/110");
		ov.setFHR(110);
		ov.setNumberOfBaby(2);
		ov.setLowLyingPlacenta(false);
		ovsql.add(ov);

	}
}
