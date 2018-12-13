package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundRecordValidator;

public class AddUltrasoundRecordAction {
    private UltrasoundRecordDAO ultrasoundRecordDAO;
//    private long loggedMID;

    public AddUltrasoundRecordAction(DAOFactory factory/*, long loggedMID*/) {
        this.ultrasoundRecordDAO = factory.getUltrasoundecordDAO();
//        this.loggedMID = loggedMID;
    }

    public long addUltrasoundRecord(UltrasoundRecordBean p/*, long loggedInMID*/) throws FormValidationException, ITrustException {
        new UltrasoundRecordValidator().validate(p);
        long newID = ultrasoundRecordDAO.addUltrasoundRecord(p);
        //TransactionLogger.getInstance().logTransaction(TransactionType.PATIENT_CREATE, loggedInMID, p.getID(), "");
        return newID;
    }
}


