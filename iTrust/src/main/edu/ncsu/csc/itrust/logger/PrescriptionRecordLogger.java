package edu.ncsu.csc.itrust.logger;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

public class PrescriptionRecordLogger {
    private TransactionDAO transDAO;

    /**
     * Set up
     *
     * @param factory The DAOFactory used to create the DAOs used in this action.
     */
    public PrescriptionRecordLogger(DAOFactory factory) {
        this.transDAO = factory.getTransactionDAO();
    }

    /**
     * Log a transaction of Create Initial Prescription Record, with all of the info.
     *
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logCreatePrescriptionRecord(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.CREATE_INITIAL_PRESCRIPTION_RECORD, hcpMID, patientMID, "");
    }

    /**
     * Log a transaction of View Initial Prescription Record, with all of the info.
     *
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logViewPrescriptionRecord(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.VIEW_INITIAL_PRESCRIPTION_RECORD, hcpMID, patientMID, "");
    }

    /**
     * Log a transaction of Edit Initial Prescription Record, with all of the info.
     *
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logEditPrescriptionRecord(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.EDIT_INITIAL_PRESCRIPTION_RECORD, hcpMID, patientMID, "");
    }

    /**
     * Log a transaction of Delete Initial Prescription Record, with all of the info.
     *
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logDeletePrescriptionRecord(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.DELETE_INITIAL_PRESCRIPTION_RECORD, hcpMID, patientMID, "");
    }
}
