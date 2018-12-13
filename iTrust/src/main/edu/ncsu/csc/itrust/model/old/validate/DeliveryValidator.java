package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;

public class DeliveryValidator extends BeanValidator<DeliveryBean> {
    @Override
    public void validate(DeliveryBean p) throws FormValidationException {
        ErrorList errorList = new ErrorList();

        errorList.addIfNotNull(checkFormat("sex", p.getSex(), ValidationFormat.GENDERCOD, true));

        if (errorList.hasErrors()) {
            throw new FormValidationException(errorList);
        }

    }
}
