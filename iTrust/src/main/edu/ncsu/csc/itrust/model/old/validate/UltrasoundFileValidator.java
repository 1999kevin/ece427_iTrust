package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;

import java.sql.SQLException;

public class UltrasoundFileValidator extends BeanValidator<UltrasoundFile> {

    @Override
    public void validate(UltrasoundFile p) throws FormValidationException {
        ErrorList errorList = new ErrorList();

        if(p.getFilename() == null || p.getFilename().isEmpty()) {
            errorList.addIfNotNull("File name is empty. ");
        }
        try {
            if(p.getContents() == null || p.getContents().length() == 0) {
                errorList.addIfNotNull("Contents is empty!");
            }
        }catch (SQLException ex) {
            errorList.addIfNotNull("Contents is empty!");
        }

        if(p.getRecordID() == 0) {
            errorList.addIfNotNull("Record Id is not set.");
        }
        if (errorList.hasErrors()){
            throw new FormValidationException(errorList);
        }
    }
}
