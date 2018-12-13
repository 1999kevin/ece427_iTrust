package edu.ncsu.csc.itrust.logger;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.junit.Assert;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.TransactionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsRecordsLoggerTest {
	private ObstetricsRecordsLogger logger = new ObstetricsRecordsLogger(TestDAOFactory.getTestInstance());
	private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogCreateObstetricsRecord() throws Exception {
		logger.logCreateObstetricsRecord(1, 2, "2018-09-10");
		List<TransactionBean> list = tranDAO.getAllTransactions();
		Assert.assertEquals(1L, list.get(0).getLoggedInMID());
		Assert.assertEquals(2L, list.get(0).getSecondaryMID());
		Assert.assertEquals("Estimated Due Date: 2018-09-10", list.get(0).getAddedInfo());
		Assert.assertEquals(TransactionType.CREATE_INITIAL_OBSTETRIC_RECORD, list.get(0).getTransactionType());
	}
	
	@Test
	public void testLogViewObstetricsRecord() throws Exception {
		logger.logViewObstetricsRecord(1, 3, "2018-09-11");
		List<TransactionBean> list = tranDAO.getAllTransactions();
		Assert.assertEquals(1L, list.get(0).getLoggedInMID());
		Assert.assertEquals(3L, list.get(0).getSecondaryMID());
		Assert.assertEquals("Estimated Due Date: 2018-09-11", list.get(0).getAddedInfo());
		Assert.assertEquals(TransactionType.VIEW_INITIAL_OBSTETRIC_RECORD, list.get(0).getTransactionType());
	}
	
	@Test
	public void testLogCreateObstetricsRecordException() throws Exception {
		DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
		ObstetricsRecordsLogger logger = new ObstetricsRecordsLogger(evilDAO);
		try {
			logger.logCreateObstetricsRecord(-12, -2, "");
			Assert.fail("DBException should have been thrown");
		} catch (DBException e) {
			Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	@Test
	public void testLogViewObstetricsRecordException() throws Exception {
		DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
		ObstetricsRecordsLogger logger = new ObstetricsRecordsLogger(evilDAO);
		try {
			logger.logViewObstetricsRecord(-12, -2, "");
			Assert.fail("DBException should have been thrown");
		} catch (DBException e) {
			Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
