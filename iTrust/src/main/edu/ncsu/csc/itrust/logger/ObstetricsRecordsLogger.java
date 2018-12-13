package edu.ncsu.csc.itrust.logger;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

/**
 * Handles generating the logging of obstetrics records used by xxx.jsp
 * 
 * 
 */
public class ObstetricsRecordsLogger {
	private TransactionDAO transDAO;

	/**
	 * Set up
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 */
	public ObstetricsRecordsLogger(DAOFactory factory) {
		this.transDAO = factory.getTransactionDAO();
	}

	/**
	 * Log a transaction of Create Initial Obstetric Record, with all of the info. 
	 * 
	 * @param hcpMID The MID of the user HCP who is logged in.
	 * @param patientMID Typically, the MID of the user patient who is being acted upon.
	 * @param EDD A note about the estimated due date
	 * @throws DBException
	 */
	public void logCreateObstetricsRecord(long hcpMID, long patientMID, String EDD)
			throws DBException {
		this.transDAO.logTransaction(TransactionType.CREATE_INITIAL_OBSTETRIC_RECORD, hcpMID, patientMID, "Estimated Due Date: " + EDD);
	}
	
	/**
	 * Log a transaction of View Initial Obstetric Record, with all of the info. 
	 * 
	 * @param hcpMID The MID of the user HCP who is logged in.
	 * @param patientMID Typically, the MID of the user patient who is being acted upon.
	 * @param EDD A note about the estimated due date
	 * @throws DBException
	 */
	public void logViewObstetricsRecord(long hcpMID, long patientMID, String EDD)
			throws DBException {
		this.transDAO.logTransaction(TransactionType.VIEW_INITIAL_OBSTETRIC_RECORD, hcpMID, patientMID, "Estimated Due Date: " + EDD);
	}
}
