package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;

public class DeliveryBean implements Serializable, Comparable {
    private long id;
    private long childHospitalVisitID;
    private String sex;
    private long patientID;
    private long motherID;
    private String deliverTime;
    private String deliverDate;

    public String getDeliverDate() {
        return deliverDate;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public long getMotherID() {
        return motherID;
    }

    public void setMotherID(long motherID) {
        this.motherID = motherID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public long getPatientID() {
        return patientID;
    }

    public long getChildHospitalVisitID() {
        return childHospitalVisitID;
    }

    public long getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

    public void setChildHospitalVisitID(long childHospitalVisitID) {
        this.childHospitalVisitID = childHospitalVisitID;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
