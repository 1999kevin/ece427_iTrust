package edu.ncsu.csc.itrust.model.old.beans;

import edu.ncsu.csc.itrust.model.old.enums.BloodType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordBean implements Serializable {
    //private int Pregnancyterm = 0;
    //private int yearOfConception = 2018;
    //private String deliveryType = "vaginal delivery";
    private String deliverDate = "04/11/2018";
    private BloodType bloodType = BloodType.NS;
    private String numberOfWeeksPreg = "20";
    private boolean HighBloodPressure = false;
    private boolean AdvancedMaternalAge = false;

    private boolean RHflag = false;
    private String preexistingcondition = "null";
    private List<String> maternalallergies = new ArrayList<>();
    private boolean Lowlyingplacenta = false;
    private boolean Highgeneticpotentialmiscarriage = false;
    private boolean AbnormalFHR = false;
    int numberofpregnancy = 1;
    private boolean Atypicalweightchange = false;
    private boolean Hyperemesisgravidarum = false;
    private boolean Hypothyroidism = false;
    private boolean isChronic = false;
    private List<String> Pregnancyterm1;
    private List<Integer> yearOfConception1;
    private List<String> deliveryType1;
    private  boolean isDiabetes = false;
    private boolean iscancer  = false;
    private boolean isSDT = false;
    public List<List<String>> obsv= new ArrayList<>();




    public boolean isHighgeneticpotentialmiscarriage() {
        return Highgeneticpotentialmiscarriage;
    }

    public void setHighgeneticpotentialmiscarriage(boolean highgeneticpotentialmiscarriage) {
        Highgeneticpotentialmiscarriage = highgeneticpotentialmiscarriage;
    }

    public void  setDiabetes( boolean isDiabetes) {
        this.isDiabetes = isDiabetes;
    }
    public boolean  getDiabetes( ) {
        return isDiabetes;
    }

    public boolean  getCancer( ) {
        return iscancer;
    }
    public void  setCancer( boolean iscancer) {
        this.iscancer = iscancer;
    }


    public void  setSDT( boolean isSDT) {
        this.isSDT = isSDT;
    }
    public boolean  getSdt( ) {
        return isSDT;
    }


    public void sePregnancyterm(List<String> Pregnancyterm) {
        this.Pregnancyterm1 = Pregnancyterm;
    }
    public List<String> getPregnancyterm() {
        return this.Pregnancyterm1;
    }

    public  List<String> getDeliveryType() {return deliveryType1;}

    public void setDeliveryType(List<String> deliveryType1) {
        this.deliveryType1 = deliveryType1;
    }

    public List<Integer> getYearOfConception() {
        return yearOfConception1;
    }

    public void setYearOfConception(List<Integer> yearOfConception) {
        this.yearOfConception1 = yearOfConception;
    }



    public  String getDeliveryDate() {return deliverDate;}
    public void setDeliveryDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public  String getBloodType() {return bloodType.toString();}
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public boolean getRHflag() {
        String s = getBloodType();
        if (s.charAt(s.length()-1) == '-') {
            return false;
        }
        return true;
    }

    public  boolean getHighBloodPressure() {return HighBloodPressure;}
    public void setHighBloodPressure(boolean HighBloodPressure) {
        this.HighBloodPressure = HighBloodPressure;
    }
    public  boolean getAdvancedMaternalAge() {return AdvancedMaternalAge;}
    public void setAdvancedMaternalAge(boolean AdvancedMaternalAge) {
        this.AdvancedMaternalAge = AdvancedMaternalAge;
    }

    public String getpreexistingcondition() {
        return preexistingcondition;
    }
    public void setpreexistingcondition(String preexistingcondition) {
        this.preexistingcondition  = preexistingcondition;
    }

    public boolean getisChronic() {return isChronic;}
    public void setChronic(boolean isChronic) {
        this.isChronic = isChronic;
    }

    public boolean getHyperemesisgravidarum() {return Hyperemesisgravidarum;}
    public void setHyperemesisgravidarum(boolean Hyperemesisgravidarum) {
        this.Hyperemesisgravidarum = Hyperemesisgravidarum;
    }

    public boolean getHypothyroidism() {return Hypothyroidism;}
    public void setHypothyroidism(boolean Hypothyroidism) {
        this.Hypothyroidism = Hypothyroidism;
    }
    public List<String> getmaternalallergies() {
        return maternalallergies;
    }
    public void setmaternalallergies(List<String> maternalallergies) {
        this.maternalallergies  = maternalallergies;
    }

    public boolean getAtypicalweight() {
        return Atypicalweightchange;
    }

    public void setAtypicalweight(boolean Atypicalweightchange) {
        this.Atypicalweightchange = Atypicalweightchange;
    }

    public int getnumberofpregnancy() {
        return numberofpregnancy;
    }

    public void setnumberofpregnancy(int numberofpregnancy) {
        this.numberofpregnancy = numberofpregnancy;
    }
    public boolean getAbnormalFHR() {
        return AbnormalFHR;
    }

    public void setAbnormalFHR(boolean AbnormalFHR) {
        this.AbnormalFHR = AbnormalFHR;
    }

    public boolean getLowlyingplacenta() {
        return Lowlyingplacenta;
    }

    public void setLowlyingplacenta(boolean Lowlyingplacenta) {
        this.Lowlyingplacenta = Lowlyingplacenta;
    }




    public List<List<String>> getObstetricsOfficeVisit() { return obsv;}
    public void setObstetricsOfficeVisit(List<List<String>> obsv){
        this.obsv = obsv;
    }
}
