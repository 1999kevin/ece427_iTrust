package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;

public class ChildHospitalVisitValidator extends BeanValidator<ChildHospitalVisitBean> {

    @Override
    public void validate(ChildHospitalVisitBean p) throws FormValidationException {
        ErrorList errorList = new ErrorList();

        errorList.addIfNotNull(checkFormat("pitocin", p.getPitocin(), ValidationFormat.DRUG_DOSE, true));
        errorList.addIfNotNull(checkFormat("nitrousOxide", p.getNitrousOxide(), ValidationFormat.DRUG_DOSE, true));
        errorList.addIfNotNull(checkFormat("pethidine", p.getPethidine(), ValidationFormat.DRUG_DOSE, true));
        errorList.addIfNotNull(checkFormat("epiduralAnaesthesia", p.getEpiduralAnaesthesia(), ValidationFormat.DRUG_DOSE, true));
        errorList.addIfNotNull(checkFormat("magnesiumSulfate", p.getMagnesiumSulfate(), ValidationFormat.DRUG_DOSE, true));
        errorList.addIfNotNull(checkFormat("RHImmuneGlobulin", p.getRHImmuneGlobulin(), ValidationFormat.DRUG_DOSE, true));

        if (errorList.hasErrors()) {
            throw new FormValidationException(errorList);
        }
    }
}
