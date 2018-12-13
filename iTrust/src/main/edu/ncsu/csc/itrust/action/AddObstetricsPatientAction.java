package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.logger.TransactionLogger;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsPatientDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsPatientValidator;

public class AddObstetricsPatientAction {
    private ObstetricsPatientDAO obstetricsPatientDAO;
//    private long loggedMID;

    public AddObstetricsPatientAction(DAOFactory factory/*, long loggedMID*/) {
        this.obstetricsPatientDAO = factory.getObDAO();
//        this.loggedMID = loggedMID;
    }

    public long addObstetricsPatient(ObstetricsPatientBean p/*, long loggedInMID*/) throws FormValidationException, ITrustException {
        new ObstetricsPatientValidator().validate(p);
        long newID = obstetricsPatientDAO.addObstetricsRecord(p);
        p.setID(newID);
        //TransactionLogger.getInstance().logTransaction(TransactionType.PATIENT_CREATE, loggedInMID, p.getID(), "");
        return newID;
    }
}


