package edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit;

import javax.faces.bean.ManagedBean;
import java.time.LocalDateTime;

@ManagedBean(name="obstetrics_office_visit")
public class ObstetricsOfficeVisit {
    private long ID;
    private long patientID;
    private LocalDateTime createdDate;
    private String locationID;
    private long apptTypeID;
    private String notes;
    private boolean sendBill;

    private LocalDateTime LMP;
    private double weightInPounds;
    private String bloodPressure;
    private int FHR;
    private int numberOfBaby;
    private boolean lowLyingPlacenta;

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public long getApptTypeID() {
        return apptTypeID;
    }

    public void setApptTypeID(long apptTypeID) {
        this.apptTypeID = apptTypeID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isSendBill() {
        return sendBill;
    }

    public void setSendBill(boolean sendBill) {
        this.sendBill = sendBill;
    }

    public LocalDateTime getLMP() {
        return LMP;
    }

    public void setLMP(LocalDateTime LMP) {
        this.LMP = LMP;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public double getWeightInPounds() {
        return weightInPounds;
    }

    public void setWeightInPounds(double weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getFHR() {
        return FHR;
    }

    public void setFHR(int FHR) {
        this.FHR = FHR;
    }

    public int getNumberOfBaby() {
        return numberOfBaby;
    }

    public void setNumberOfBaby(int numberOfBaby) {
        this.numberOfBaby = numberOfBaby;
    }

    public boolean isLowLyingPlacenta() {
        return lowLyingPlacenta;
    }

    public void setLowLyingPlacenta(boolean lowLyingPlacenta) {
        this.lowLyingPlacenta = lowLyingPlacenta;
    }
}
