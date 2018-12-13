package edu.ncsu.csc.itrust.logger;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

public class ObstetricsOfficeVisitLogger {
    private TransactionDAO transDAO;

    /**
     * Set up
     *
     * @param factory The DAOFactory used to create the DAOs used in this action.
     */
    public ObstetricsOfficeVisitLogger(DAOFactory factory) {
        this.transDAO = factory.getTransactionDAO();
    }

    /**
     * Log a transaction of Create Obstetric Office Visit, with all of the info.
     * ObstetricsOfficeVisitLogger
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param Office_Visit_ID A note about the office visit ID
     * @throws DBException
     */
    public void logCreateObstetricsOfficeVisit(long hcpMID, long patientMID, String Office_Visit_ID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.CREATE_OBSTETRIC_OFFICE_VISIT, hcpMID, patientMID, "Office Visit ID: " + Office_Visit_ID);
    }

    /**
     * Log a transaction of View Obstetric Office Visit, with all of the info.
     * ObstetricsOfficeVisitLogger
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param Office_Visit_ID A note about the office visit ID
     * @throws DBException
     */
    public void logViewObstetricsOfficeVisit(long hcpMID, long patientMID, String Office_Visit_ID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.VIEW_OBSTETRIC_OFFICE_VISIT, hcpMID, patientMID, "Office Visit ID: " + Office_Visit_ID);
    }


    /**
     * Log a transaction of Edit Obstetric Office Visit, with all of the info.
     * ObstetricsOfficeVisitLogger
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param Office_Visit_ID A note about the office visit ID
     * @throws DBException
     */
    public void logEditObstetricsOfficeVisit(long hcpMID, long patientMID, String Office_Visit_ID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.EDIT_OBSTETRIC_OFFICE_VISIT, hcpMID, patientMID, "Office Visit ID: " + Office_Visit_ID);
    }

    /**
     * Log a transaction of ultrasound, with all of the info.
     * ObstetricsOfficeVisitLogger
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param Office_Visit_ID A note about the office visit ID
     * @throws DBException
     */
    public void logUltrasound(long hcpMID, long patientMID, String Office_Visit_ID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.ULTRASOUND, hcpMID, patientMID, "Office Visit ID: " + Office_Visit_ID);
    }

    /**
     * Log a transaction of schedule next office visit, with all of the info.
     * ObstetricsOfficeVisitLogger
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param Cur_OS_ID A note about the current office visit ID
     * @param Next_OS_ID A note about the next office visit ID
     * @throws DBException
     */
    public void logScheduleNextOfficeVisit(long hcpMID, long patientMID, String Cur_OS_ID, String Next_OS_ID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.SCHEDULE_NEXT_OFFICE_VISIT, hcpMID, patientMID, "Current Office Visit ID: " + Cur_OS_ID + ", Next Office Visit ID: " + Next_OS_ID);
    }

    /**
     * Log a transaction of schedule next office visit, with all of the info.
     * ObstetricsOfficeVisitLogger
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param Cur_OS_ID A note about the current office visit ID
     * @param ChildBirth_Visit_ID A note about the next office visit ID
     * @throws DBException
     */
    public void logScheduleChildBirth(long hcpMID, long patientMID, String Cur_OS_ID, String ChildBirth_Visit_ID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.SCHEDULE_CHILDBIRTH, hcpMID, patientMID, "Current Office Visit ID: " + Cur_OS_ID + ", ChildBirth ID: " + ChildBirth_Visit_ID);
    }
}
