package edu.ncsu.csc.itrust.unit.validate.bean;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundFileValidator;
import junit.framework.TestCase;
import org.junit.Test;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;

public class UltrasoundFileValidatorTest extends TestCase {
    UltrasoundFileValidator validator = new UltrasoundFileValidator();

    public void testValidInput() throws SQLException, FormValidationException {
        UltrasoundFile file = new UltrasoundFile();
        file.setFilename("filename");
        file.setRecordID(1);
        byte[] bytes = new byte[] {1, 2, 3, 4};
        file.setContents(new SerialBlob(bytes));
        validator.validate(file);
    }

    @Test
    public void testFilenameMissing() throws SQLException {
        UltrasoundFile file = new UltrasoundFile();
        //file.setFilename("filename");
        file.setRecordID(1);
        byte[] bytes = new byte[] {1, 2, 3, 4};
        file.setContents(new SerialBlob(bytes));
        try{
            validator.validate(file);
            assertTrue(false);
        }catch (FormValidationException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testRecordIDMissing() throws SQLException {
        UltrasoundFile file = new UltrasoundFile();
        file.setFilename("filename");
        //file.setRecordID(1);
        byte[] bytes = new byte[] {1, 2, 3, 4};
        file.setContents(new SerialBlob(bytes));
        try{
            validator.validate(file);
            assertTrue(false);
        }catch (FormValidationException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testContentsMissing() throws SQLException {
        UltrasoundFile file = new UltrasoundFile();
        file.setFilename("filename");
        file.setRecordID(1);
        byte[] bytes = new byte[] {1, 2, 3, 4};
        //file.setContents(new SerialBlob(bytes));
        try{
            validator.validate(file);
            assertTrue(false);
        }catch (FormValidationException ex) {
            assertTrue(true);
        }
    }
}
