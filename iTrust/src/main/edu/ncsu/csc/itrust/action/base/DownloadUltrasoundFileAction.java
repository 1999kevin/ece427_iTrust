package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO;

import java.io.InputStream;

public class DownloadUltrasoundFileAction {
    private long recordID;
    private UltrasoundRecordDAO ultrasoundRecordDAO;

    public DownloadUltrasoundFileAction(DAOFactory factory, long recordID) {
        this.recordID = recordID;
        this.ultrasoundRecordDAO = factory.getUltrasoundecordDAO();
    }

    public UltrasoundFile getFile() throws DBException {
        return ultrasoundRecordDAO.getFile(recordID);
    }
}
