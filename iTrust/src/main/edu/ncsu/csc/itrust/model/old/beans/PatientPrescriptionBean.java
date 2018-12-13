package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class PatientPrescriptionBean implements Serializable,Comparable<PatientPrescriptionBean> {
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
    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    @Override
    public int compareTo(PatientPrescriptionBean o) {
        return (int) (o.ID - this.ID);
    }


    public int equals(PatientPrescriptionBean o) {
        return (int) (o.ID - this.ID);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do

    }
}
