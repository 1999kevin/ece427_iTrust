package edu.ncsu.csc.itrust.model.old.beans.loaders;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
        * A loader for ObstetricsPatienBeans.
        *
        * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency.
        * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
        */
public class ObstetricsLoader implements BeanLoader<ObstetricsPatientBean>{
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    public List<ObstetricsPatientBean> loadList(ResultSet rs) throws SQLException{
        List<ObstetricsPatientBean> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;

    }

    private void loadCommon(ResultSet rs, ObstetricsPatientBean p) throws SQLException {
        p.setID(rs.getLong("ID"));
        p.setPatientID(rs.getLong("patientID"));
        p.setCreatedDate(rs.getString("createdDate"));
        p.setYearOfConception(rs.getInt("yearOfConception"));
        p.setLMP(rs.getString("LMP"));
        p.setNumberOfWeeksPreg(rs.getString("numberOfWeeksPreg"));
        p.setNumberOfLaborHour(rs.getDouble("numberOfLaborHour"));
        p.setWeightGain(rs.getDouble("weightGain"));
        p.setDeliveryType(rs.getString("deliveryType"));
        p.setNumberofBaby(rs.getInt("NumberofBaby"));

    }

    @Override
    public ObstetricsPatientBean loadSingle(ResultSet rs) throws SQLException {
        ObstetricsPatientBean p = new ObstetricsPatientBean();
        loadCommon(rs, p);
        return p;
    }

    // insert/update into database
    @Override
    public PreparedStatement loadParameters(PreparedStatement ps, ObstetricsPatientBean p) throws SQLException {
        int i=1;
        ps.setLong(i++,p.getPatientID());
        ps.setString(i++,p.getCreatedDate());
        ps.setInt(i++,p.getYearOfConception());
        ps.setString(i++,p.getLMP());
        ps.setString(i++,p.getNumberOfWeeksPreg());
        ps.setDouble(i++,p.getNumberOfLaborHour());
        ps.setDouble(i++,p.getWeightGain());
        ps.setString(i++,p.getDeliveryType());
        ps.setInt(i++,p.getNumberofBaby());
        return ps;
    }



}
