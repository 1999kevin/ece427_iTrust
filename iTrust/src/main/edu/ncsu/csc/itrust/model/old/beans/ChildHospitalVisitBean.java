package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChildHospitalVisitBean implements Serializable, Comparable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3269564016051039075L;
	private long ID;
    private String preferredDeliveryType;
    private String actualDeliveryType;
    private int pitocin;
    private int nitrousOxide;
    private int pethidine;
    private int epiduralAnaesthesia;
    private int magnesiumSulfate;
    private int RHImmuneGlobulin;
    private long patientID;
    private int apptID;

    public int getApptID() {
        return apptID;
    }

    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public void setRHImmuneGlobulin(int RHImmuneGlobulin) {
        this.RHImmuneGlobulin = RHImmuneGlobulin;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public int getEpiduralAnaesthesia() {
        return epiduralAnaesthesia;
    }

    public int getNitrousOxide() {
        return nitrousOxide;
    }

    public int getMagnesiumSulfate() {
        return magnesiumSulfate;
    }

    public int getPethidine() {
        return pethidine;
    }

    public int getPitocin() {
        return pitocin;
    }

    public int getRHImmuneGlobulin() {
        return RHImmuneGlobulin;
    }

    public long getID() {
        return ID;
    }

    public String getActualDeliveryType() {
        return actualDeliveryType;
    }

    public String getPreferredDeliveryType() {
        return preferredDeliveryType;
    }

    public void setActualDeliveryType(String actualDeliveryType) {
        this.actualDeliveryType = actualDeliveryType;
    }

    public void setEpiduralAnaesthesia(int epiduralAnaesthesia) {
        this.epiduralAnaesthesia = epiduralAnaesthesia;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setMagnesiumSulfate(int magnesiumSulfate) {
        this.magnesiumSulfate = magnesiumSulfate;
    }

    public void setNitrousOxide(int nitrousOxide) {
        this.nitrousOxide = nitrousOxide;
    }

    public void setPethidine(int pethidine) {
        this.pethidine = pethidine;
    }

    public void setPitocin(int pitocin) {
        this.pitocin = pitocin;
    }

    public void setPreferredDeliveryType(String preferredDeliveryType) {
        this.preferredDeliveryType = preferredDeliveryType;
    }



    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
