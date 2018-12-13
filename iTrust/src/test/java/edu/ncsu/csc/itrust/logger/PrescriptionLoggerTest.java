package edu.ncsu.csc.itrust.logger;

import edu.ncsu.csc.itrust.model.old.beans.TransactionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PrescriptionLoggerTest {
    private PrescriptionRecordLogger logger = new PrescriptionRecordLogger(TestDAOFactory.getTestInstance());
    private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TestLogCreatePrescriptionRecord() throws Exception {
        logger.logCreatePrescriptionRecord(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
//        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.CREATE_INITIAL_PRESCRIPTION_RECORD, list.get(0).getTransactionType());
    }

    @Test
    public void TestLogViewPrescriptionRecord() throws Exception {
        logger.logViewPrescriptionRecord(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
//        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.VIEW_INITIAL_PRESCRIPTION_RECORD, list.get(0).getTransactionType());
    }

    @Test
    public void TestLogEditPrescriptionRecord() throws Exception {
        logger.logEditPrescriptionRecord(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
//        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.EDIT_INITIAL_PRESCRIPTION_RECORD, list.get(0).getTransactionType());
    }

    @Test
    public void TestLogDeletePrescriptionRecord() throws Exception {
        logger.logDeletePrescriptionRecord(1, 2);
        List<TransactionBean> list = tranDAO.getAllTransactions();
        Assert.assertEquals(1L, list.get(0).getLoggedInMID());
        Assert.assertEquals(2L, list.get(0).getSecondaryMID());
//        Assert.assertEquals("Office Visit ID: 111", list.get(0).getAddedInfo());
        Assert.assertEquals(TransactionType.DELETE_INITIAL_PRESCRIPTION_RECORD, list.get(0).getTransactionType());
    }


}
