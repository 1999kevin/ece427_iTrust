package edu.ncsu.csc.itrust.model.old.beans.loaders;

import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;

import java.sql.Date;
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
public class PatientPrescriptionLoader implements BeanLoader<PatientPrescriptionBean>{
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    public List<PatientPrescriptionBean> loadList(ResultSet rs) throws SQLException{
        List<PatientPrescriptionBean> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;

    }

    private void loadCommon(ResultSet rs, PatientPrescriptionBean p) throws SQLException {
        p.setID(rs.getLong("ID"));
        p.setPatientID(rs.getLong("patientID"));
        p.setCreatedDate(rs.getDate("createdDate").toLocalDate());
        p.setName(rs.getString("name"));
        p.setNotes(rs.getString("notes"));
        p.setDosage(rs.getDouble("dosage"));
    }

    @Override
    public PatientPrescriptionBean loadSingle(ResultSet rs) throws SQLException {
        PatientPrescriptionBean p = new PatientPrescriptionBean();
        loadCommon(rs, p);
        return p;
    }

    // insert/update into database
    @Override
    public PreparedStatement loadParameters(PreparedStatement ps, PatientPrescriptionBean p) throws SQLException {
        int i=1;
        ps.setLong(i++,p.getPatientID());
        ps.setDate(i++,Date.valueOf(p.getCreatedDate()));
        ps.setString(i++,p.getName());
        ps.setString(i++,p.getNotes());
        ps.setDouble(i++,p.getDosage());
        return ps;
    }

}
