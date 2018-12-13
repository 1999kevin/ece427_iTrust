package edu.ncsu.csc.itrust.logger;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

public class ChildbirthHospitalVisitLogger {
    private TransactionDAO transDAO;

    /**
     * Set up
     *
     * @param factory The DAOFactory used to create the DAOs used in this action.
     */
    public ChildbirthHospitalVisitLogger(DAOFactory factory) {
        this.transDAO = factory.getTransactionDAO();
    }

    /**
     * Log a transaction of Create childbirth visit, with all of the info.
     * logCreateChildbirthVisit
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logCreateChildbirthVisit(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.CREATE_CHILDBIRTH_VISIT, hcpMID, patientMID, "");
    }

    /**
     * Log a transaction of add childbirth visit, with all of the info.
     * logAddChildbirthDrugs
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logAddChildbirthDrugs(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.ADD_CHILDBIRTH_DRUGS, hcpMID, patientMID, "");
    }

    /**
     * Log a transaction of a baby is born, with all of the info.
     * logBabyBorn
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logBabyBorn(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.A_BABY_IS_BORN, hcpMID, patientMID, "");
    }

    /**
     * Log a transaction of a baby is born, with all of the info.
     * logCreateBabyRecord
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @param MID_of_baby The Mid of the baby
     * @throws DBException
     */
    public void logCreateBabyRecord(long hcpMID, long patientMID, String MID_of_baby)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.CREATE_BABY_RECORD, hcpMID, patientMID, "Mid of the baby: " + MID_of_baby);
    }

    /**
     * Log a transaction of a baby is born, with all of the info.
     * logEditChildbirthVisit
     * @param hcpMID The MID of the user HCP who is logged in.
     * @param patientMID Typically, the MID of the user patient who is being acted upon.
     * @throws DBException
     */
    public void logEditChildbirthVisit(long hcpMID, long patientMID)
            throws DBException {
        this.transDAO.logTransaction(TransactionType.EDIT_CHILDBIRTH_VISIT, hcpMID, patientMID, "");
    }

}
