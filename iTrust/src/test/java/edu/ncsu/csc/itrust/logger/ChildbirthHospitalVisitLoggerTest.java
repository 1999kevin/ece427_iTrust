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

public class ChildbirthHospitalVisitLoggerTest {
    private ChildbirthHospitalVisitLogger logger = new ChildbirthHospitalVisitLogger(TestDAOFactory.getTestInstance());
    private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLogCreateChildbirthVisit() throws Exception {
        logger.logCreateChildbirthVisit(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
        Assert.assertEquals(TransactionType.CREATE_CHILDBIRTH_VISIT, list.get(0).getTransactionType());
    }

    @Test
    public void testLogAddChildbirthDrugs() throws Exception {
        logger.logAddChildbirthDrugs(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
        Assert.assertEquals(TransactionType.ADD_CHILDBIRTH_DRUGS, list.get(0).getTransactionType());
    }

    @Test
    public void testLogBabyBorn() throws Exception {
        logger.logBabyBorn(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
        Assert.assertEquals(TransactionType.A_BABY_IS_BORN, list.get(0).getTransactionType());
    }

    @Test
    public void testLogCreateBabyRecord() throws Exception {
        logger.logCreateBabyRecord(1, 2, "111");
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
        Assert.assertEquals("Mid of the baby: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.CREATE_BABY_RECORD, list.get(0).getTransactionType());
    }

    @Test
    public void testLogEditChildbirthVisit() throws Exception {
        logger.logEditChildbirthVisit(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
        Assert.assertEquals(TransactionType.EDIT_CHILDBIRTH_VISIT, list.get(0).getTransactionType());
    }

    @Test
    public void testLogCreateChildbirthVisitException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ChildbirthHospitalVisitLogger logger = new ChildbirthHospitalVisitLogger(evilDAO);
        try {
            logger.logCreateChildbirthVisit(-12, -2);
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogAddChildbirthDrugsException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ChildbirthHospitalVisitLogger logger = new ChildbirthHospitalVisitLogger(evilDAO);
        try {
            logger.logAddChildbirthDrugs(-12, -2);
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogBabyBornException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ChildbirthHospitalVisitLogger logger = new ChildbirthHospitalVisitLogger(evilDAO);
        try {
            logger.logBabyBorn(-12, -2);
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogCreateBabyRecordException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ChildbirthHospitalVisitLogger logger = new ChildbirthHospitalVisitLogger(evilDAO);
        try {
            logger.logCreateBabyRecord(-12, -2,"111");
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }

    @Test
    public void testLogEditChildbirthVisitException() throws Exception {
        DAOFactory evilDAO = EvilDAOFactory.getEvilInstance();
        ChildbirthHospitalVisitLogger logger = new ChildbirthHospitalVisitLogger(evilDAO);
        try {
            logger.logEditChildbirthVisit(-12, -2);
            Assert.fail("DBException should have been thrown");
        } catch (DBException e) {
            Assert.assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
        }
    }
}
