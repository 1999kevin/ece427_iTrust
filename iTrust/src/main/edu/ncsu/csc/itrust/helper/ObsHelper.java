package edu.ncsu.csc.itrust.helper;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import java.util.*;

public class ObsHelper {
    public static String getLatestLMP(List<ObstetricsPatientBean> records) {
        if(records == null || records.size() == 0) {
            return "";
        }
        List<String> dates = new ArrayList<>();
        for(ObstetricsPatientBean record: records) {
            dates.add(record.getLMP());
        }
        return DateHelper.latest(dates);
    }

    public static String validateObsVisit(){
        return "";
    }
}
