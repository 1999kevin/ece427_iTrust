package edu.ncsu.csc.itrust.logger;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.TransactionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ObstetricsOfficeVisitLoggerTest {
    private ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(TestDAOFactory.getTestInstance());
    private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLogCreateObstetricsOfficeVisit() throws Exception {
        logger.logCreateObstetricsOfficeVisit(1, 2, "111");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.CREATE_OBSTETRIC_OFFICE_VISIT, list.get(0).getTransactionType());
    }

    @Test
    public void testLogViewObstetricsOfficeVisit() throws Exception {
        logger.logViewObstetricsOfficeVisit(1, 3, "111");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(3L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.VIEW_OBSTETRIC_OFFICE_VISIT, list.get(0).getTransactionType());
    }

    @Test
    public void testLogEditObstetricsOfficeVisit() throws Exception {
        logger.logEditObstetricsOfficeVisit(1, 4, "111");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(4L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.EDIT_OBSTETRIC_OFFICE_VISIT, list.get(0).getTransactionType());
    }

    @Test
    public void testLogUltrasound() throws Exception {
        logger.logUltrasound(1, 5, "111");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(5L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.ULTRASOUND, list.get(0).getTransactionType());
    }

    @Test
    public void testLogScheduleNextOfficeVisit() throws Exception {
        logger.logScheduleNextOfficeVisit(1, 6, "111", "112");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(6L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Current Office Visit ID: 111, Next Office Visit ID: 112", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.SCHEDULE_NEXT_OFFICE_VISIT, list.get(0).getTransactionType());
    }

    @Test
    public void testLogScheduleChildBirth() throws Exception {
        logger.logScheduleChildBirth(1, 7, "111", "112");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(7L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Current Office Visit ID: 111, ChildBirth ID: 112", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.SCHEDULE_CHILDBIRTH, list.get(0).getTransactionType());
    }

    @Test
    public void testLogCreateObstetricsOfficeVisitException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(evilDAO);
        try {
            logger.logCreateObstetricsOfficeVisit(-12, -2, "");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogViewObstetricsOfficeVisitException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(evilDAO);
        try {
            logger.logViewObstetricsOfficeVisit(-12, -2, "");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogEditObstetricsOfficeVisitException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(evilDAO);
        try {
            logger.logEditObstetricsOfficeVisit(-12, -2, "");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogUltrasoundException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(evilDAO);
        try {
            logger.logUltrasound(-12, -2, "");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogScheduleNextOfficeVisitException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(evilDAO);
        try {
            logger.logScheduleNextOfficeVisit(-12, -2, "", "");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogScheduleChildBirthException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ObstetricsOfficeVisitLogger logger = new ObstetricsOfficeVisitLogger(evilDAO);
        try {
            logger.logScheduleChildBirth(-12, -2, "", "");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }
}
