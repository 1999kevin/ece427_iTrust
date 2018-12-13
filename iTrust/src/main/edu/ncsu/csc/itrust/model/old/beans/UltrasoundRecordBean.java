package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UltrasoundRecordBean implements Serializable,Comparable<UltrasoundRecordBean> {

    private long ID; // primary key
    private long patientID; //MID foreign key
    private long visitID;
    private LocalDateTime createdDate;
    private String URL;
    private double CRL;
    private double BPD;
    private double HC;
    private double FL;
    private double OFD;
    private double AC;
    private double HL;
    private double EFW;

    public long getVisitID() {
        return visitID;
    }

    public void setVisitID(long visitID) {
        this.visitID = visitID;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public double getCRL() {
        return CRL;
    }

    public void setCRL(double CRL) {
        this.CRL = CRL;
    }

    public double getBPD() {
        return BPD;
    }

    public void setBPD(double BPD) {
        this.BPD = BPD;
    }

    public double getHC() {
        return HC;
    }

    public void setHC(double HC) {
        this.HC = HC;
    }

    public double getFL() {
        return FL;
    }

    public void setFL(double FL) {
        this.FL = FL;
    }

    public double getOFD() {
        return OFD;
    }

    public void setOFD(double OFD) {
        this.OFD = OFD;
    }

    public double getAC() {
        return AC;
    }

    public void setAC(double AC) {
        this.AC = AC;
    }

    public double getHL() {
        return HL;
    }

    public void setHL(double HL) {
        this.HL = HL;
    }

    public double getEFW() {
        return EFW;
    }

    public void setEFW(double EFW) {
        this.EFW = EFW;
    }

    @Override
    public int compareTo(UltrasoundRecordBean o) {
        return (int) (o.ID - this.ID);
    }


    public int equals(UltrasoundRecordBean o) {
        return (int) (o.ID - this.ID);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do

    }
}
