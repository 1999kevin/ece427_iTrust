package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundFileValidator;
import org.apache.commons.io.IOUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;


public class UploadUltrasoundFileAction {
    private ErrorList errors;
    private PatientDAO patientDAO;
    private AuthDAO authDAO;
    private long loggedInMID;
    private String filename;
    private long recordID;
    private InputStream inputStream;
    private DAOFactory factory;
    private UltrasoundRecordDAO ultrasoundRecordDAO;

    private final String ULTRASOUND_FILE_FOLDER = "D://git//T810//iTrust//WebRoot//image//user//";

    public UploadUltrasoundFileAction(InputStream inputStream, DAOFactory factory, long loggedInMID, long recordID, String filename) {

        this.patientDAO = factory.getPatientDAO();
        this.loggedInMID = loggedInMID;
        this.authDAO = factory.getAuthDAO();
        this.filename = filename;
        this.inputStream = inputStream;
        this.recordID = recordID;
        this.factory = factory;
        this.ultrasoundRecordDAO = factory.getUltrasoundecordDAO();

    }

    public String save() throws IOException, DBException, FormValidationException {
        try{
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Blob blob = new SerialBlob(bytes);
            UltrasoundFile file = new UltrasoundFile();
            file.setContents(blob);
            file.setFilename(filename);
            file.setRecordID(recordID);
            UltrasoundFileValidator validator = new UltrasoundFileValidator();
            validator.validate(file);
            long newId = ultrasoundRecordDAO.saveFile(file);
            String url = "" + newId;
            new ObstetricsOfficeVisitMySQL().updateURL(recordID, url);
            return url;
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

}
