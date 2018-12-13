package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;

public class UltrasoundRecordValidator extends  BeanValidator<UltrasoundRecordBean> {
    public UltrasoundRecordValidator() {
    }

    @Override
    public void validate(UltrasoundRecordBean p) throws FormValidationException {
        ErrorList errorList = new ErrorList();

        errorList.addIfNotNull(checkFormat("patientID", p.getPatientID(), ValidationFormat.MID, false));
//        errorList.addIfNotNull(checkFormat("createdDate", p.getCreatedDate(), ValidationFormat.DATE, false));
//        if (p.getYearOfConception() != 0) {
//            errorList.addIfNotNull(checkFormat("yearOfConception", p.getYearOfConception(), ValidationFormat.YEAR, false));
//        }
//        if (!p.getNumberOfWeeksPreg().isEmpty()) {
//            errorList.addIfNotNull(checkFormat("numberOfWeeksPreg", p.getNumberOfWeeksPreg(), ValidationFormat.WEEKS_PREGNANT, false));
//        }

        if (errorList.hasErrors())
            throw new FormValidationException(errorList);

    }
}
