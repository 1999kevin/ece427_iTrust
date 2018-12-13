package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;

public class PatientPrescriptionValidator extends  BeanValidator<PatientPrescriptionBean> {
    public PatientPrescriptionValidator() {
    }

    @Override
    public void validate(PatientPrescriptionBean p) throws FormValidationException {
        ErrorList errorList = new ErrorList();

        errorList.addIfNotNull(checkFormat("patientID", p.getPatientID(), ValidationFormat.MID, false));
        
        if (errorList.hasErrors())
            throw new FormValidationException(errorList);

    }
}
