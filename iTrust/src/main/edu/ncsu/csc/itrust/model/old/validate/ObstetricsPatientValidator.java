package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;

import org.apache.commons.validator.CreditCardValidator;
import java.util.Date;

public class ObstetricsPatientValidator extends  BeanValidator<ObstetricsPatientBean> {
    public ObstetricsPatientValidator() {
    }

    @Override
    public void validate(ObstetricsPatientBean p) throws FormValidationException {
        ErrorList errorList = new ErrorList();

        errorList.addIfNotNull(checkFormat("createdDate", p.getCreatedDate(), ValidationFormat.DATE, false));
        errorList.addIfNotNull(checkFormat("patientID", p.getPatientID(), ValidationFormat.MID, false));
        errorList.addIfNotNull(checkFormat("LMP", p.getLMP(), ValidationFormat.DATE, false));
        if (p.getYearOfConception() != 0) {
            errorList.addIfNotNull(checkFormat("yearOfConception", p.getYearOfConception(), ValidationFormat.YEAR, false));
        }
        if (!p.getNumberOfWeeksPreg().isEmpty()) {
            errorList.addIfNotNull(checkFormat("numberOfWeeksPreg", p.getNumberOfWeeksPreg(), ValidationFormat.WEEKS_PREGNANT, false));
        }

        if (errorList.hasErrors())
            throw new FormValidationException(errorList);

    }
}
