package edu.ncsu.csc.itrust.model.officeVisit;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitSQLLoader;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitValidator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PatientPrescriptionMySQL {
    private DataSource ds;

    public PatientPrescriptionMySQL() throws DBException {
        try {
            Context ctx = new InitialContext();
            this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
        } catch (NamingException e) {
            throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
        }
    }

    public PatientPrescriptionMySQL(DataSource ds) {
        this.ds = ds;
    }

    public long add(PatientPrescription prescription) throws DBException {
        if(prescription == null) {
            throw new DBException(new SQLException("prescription can't be null" ));
        }
        return -1;
    }

    public void edit(PatientPrescription prescription) throws DBException {
        if(prescription == null) {
            throw new DBException(new SQLException("prescription can't be null" ));
        }
    }

    public void delete(PatientPrescription prescription) throws DBException {
        if(prescription == null) {
            throw new DBException(new SQLException("prescription can't be null" ));
        }
    }

    public List<PatientPrescription> getPrescriptionForPatient(long patientID) {
        return fakeData(patientID);
    }

    public PatientPrescription getByPrescriptionID(long id) {
        return dummyPrescription(id, 1, LocalDate.now(), "drug1", 1, "notes");
    }

    public static PatientPrescription dummyPrescription(long id, long patientID, LocalDate time, String name, double dosage, String notes ) {
        PatientPrescription prescription = new PatientPrescription();
        prescription.setID(id);
        prescription.setPatientID(patientID);
        prescription.setCreatedDate(time);
        prescription.setName(name);
        prescription.setNotes(notes);
        return prescription;
    }

    public static List<PatientPrescription> fakeData(long patientID) {
        List<PatientPrescription> result = new ArrayList<>();
        result.add(dummyPrescription(1, patientID, LocalDate.now(), "drug 1", 1, "notes1"));
        result.add(dummyPrescription(2, patientID, LocalDate.now(), "drug 2", 2, "notes2"));
        result.add(dummyPrescription(3, patientID, LocalDate.now(), "drug 3", 3, "notes3"));
        result.add(dummyPrescription(4, patientID, LocalDate.now(), "drug 4", 4, "notes4"));
        return result;
    }
}
