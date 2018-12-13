package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientPrescriptionDAO;
import edu.ncsu.csc.itrust.model.old.validate.PatientPrescriptionValidator;

public class AddPatientPrescriptionAction {
    private PatientPrescriptionDAO patientPrescriptionDAO;
//    private long loggedMID;

    public AddPatientPrescriptionAction(DAOFactory factory/*, long loggedMID*/) {
        this.patientPrescriptionDAO = factory.getPatientPrescriptionDAO();
//        this.loggedMID = loggedMID;
    }

    public long addPatientPrescription(PatientPrescriptionBean p/*, long loggedInMID*/) throws FormValidationException, ITrustException {
        new PatientPrescriptionValidator().validate(p);
        long newID = patientPrescriptionDAO.add(p);
        //TransactionLogger.getInstance().logTransaction(TransactionType.PATIENT_CREATE, loggedInMID, p.getID(), "");
        return newID;
    }
}


