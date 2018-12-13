package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObstetricsPatientBean implements Serializable,Comparable<ObstetricsPatientBean> {

    private long ID = 0; // primary key
    private long patientID = 0; //MID foreign key
    private String createdDate = "";
    private int yearOfConception = 0;
    private String LMP = "";
    private String numberOfWeeksPreg = "";
    private double numberOfLaborHour = 0.0;
    private double weightGain = 0.0;
    private String deliveryType = "";
    private int numberofBaby = 0;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLMP() {
        return LMP;
    }

    public void setLMP(String LMP) {
        this.LMP = LMP;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public double getWeightGain() {
        return weightGain;
    }

    public void setWeightGain(double weightGain) {
        this.weightGain = weightGain;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patienID) {
        this.patientID = patienID;
    }

    public int getYearOfConception() {
        return yearOfConception;
    }

    public void setYearOfConception(int yearOfConception) {
        this.yearOfConception = yearOfConception;
    }

    public String getNumberOfWeeksPreg() {
        return numberOfWeeksPreg;
    }

    public void setNumberOfWeeksPreg(String numberOfWeeksPreg) {
        this.numberOfWeeksPreg = numberOfWeeksPreg;
    }

    public double getNumberOfLaborHour() {
        return numberOfLaborHour;
    }

    public void setNumberOfLaborHour(double numberOfLaborHour) {
        this.numberOfLaborHour = numberOfLaborHour;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public int getNumberofBaby() {
        return numberofBaby;
    }

    public void setNumberofBaby(int numberofBaby) {
        this.numberofBaby = numberofBaby;
    }

    @Override
    public int compareTo(ObstetricsPatientBean o) {
        return (int) (o.ID - this.ID);
    }


    public int equals(ObstetricsPatientBean o) {
        return (int) (o.ID - this.ID);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do

    }
}
