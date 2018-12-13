package edu.ncsu.csc.itrust.model.officeVisit;

import javax.faces.bean.ManagedBean;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="patient_prescription")
public class PatientPrescription {
    private long ID;
    private long patientID;
    private LocalDate createdDate;
    private String name;
    private String notes;
    private double dosage;

    public long getID(){return ID;}
    public void setID(long id) {this.ID = id;}

    public long getPatientID(){return patientID;}
    public void setPatientID(long id) {this.patientID = id;}

    public LocalDate getCreatedDate(){return createdDate;}
    public void setCreatedDate(LocalDate time) {this.createdDate = time;}

    public String getNotes(){return notes;}
    public void setNotes(String notes) {this.notes = notes;}

    public String getName(){return name;}
    public void setName(String name) {this.name = name;}

    public double getDosage(){return dosage;}
    public void setDosage(double dosage) {this.dosage = dosage;}


}
