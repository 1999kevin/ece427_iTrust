package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientPrescriptionDAO;
import edu.ncsu.csc.itrust.model.old.validate.PatientPrescriptionValidator;


/**
 * Edits a patient Used by editPatient.jsp
 * 
 * 
 */
public class EditPatientPrescriptionAction extends PatientBaseAction {
	private PatientPrescriptionValidator validator = new PatientPrescriptionValidator();
	private PatientPrescriptionDAO patientPrescriptionDAO;
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
	public EditPatientPrescriptionAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientPrescriptionDAO = factory.getPatientPrescriptionDAO();
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
	public void editPatientPrescription(PatientPrescriptionBean p) throws ITrustException, FormValidationException {
		validator.validate(p);
		patientPrescriptionDAO.edit(p);
	}

	public void deletePatientPrescription(long id) throws ITrustException, FormValidationException {
		patientPrescriptionDAO.delete(id);
	}

}
