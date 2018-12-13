package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.logger.TransactionLogger;
import edu.ncsu.csc.itrust.model.old.beans.Email;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundRecordValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Edits a patient Used by editPatient.jsp
 * 
 * 
 */
public class EditUltrasoundRecordAction extends PatientBaseAction {
	private UltrasoundRecordValidator validator = new UltrasoundRecordValidator();
	private UltrasoundRecordDAO ultrasoundRecordDAO;
	private AuthDAO authDAO;
	private long loggedInMID;

	/**
	 * The super class validates the patient id
	 *
	 * @param factory The DAOFactory used to create the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The MID of the patient being edited.
	 * @throws ITrustException
	 */
	public EditUltrasoundRecordAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.ultrasoundRecordDAO = factory.getUltrasoundecordDAO();
		this.authDAO = factory.getAuthDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Takes the information out of the PatientBean param and updates the patient's information
	 * 
	 * @param p
	 *            the new patient information
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void updateInformation(UltrasoundRecordBean p) throws ITrustException, FormValidationException {
		validator.validate(p);
		ultrasoundRecordDAO.editUltrasoundRecord(p, loggedInMID);
	}

	public void deleteInforamtion(long id) throws  ITrustException {
		ultrasoundRecordDAO.deleteUltrasoundRecord(id);
	}

}
