package edu.ncsu.csc.itrust.model.old.beans.loaders;

import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
        * A loader for ObstetricsPatienBeans.
        *
        * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency.
        * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
        */
public class UltrasoundRecordBeanLoader implements BeanLoader<UltrasoundRecordBean>{
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    public List<UltrasoundRecordBean> loadList(ResultSet rs) throws SQLException{
        List<UltrasoundRecordBean> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;

    }

    private void loadCommon(ResultSet rs, UltrasoundRecordBean p) throws SQLException {
        p.setID(rs.getLong("ID"));
        p.setPatientID(rs.getLong("patientID"));
        p.setVisitID(rs.getLong("visitID"));
        p.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
        p.setURL(rs.getString("URL"));
        p.setCRL(rs.getDouble("CRL"));
        p.setBPD(rs.getDouble("BPD"));
        p.setHC(rs.getDouble("HC"));
        p.setFL(rs.getDouble("FL"));
        p.setOFD(rs.getDouble("OFD"));
        p.setAC(rs.getDouble("AC"));
        p.setHL(rs.getDouble("HL"));
        p.setEFW(rs.getDouble("EFW"));
    }

    @Override
    public UltrasoundRecordBean loadSingle(ResultSet rs) throws SQLException {
        UltrasoundRecordBean p = new UltrasoundRecordBean();
        loadCommon(rs, p);
        return p;
    }

    // insert/update into database
    @Override
    public PreparedStatement loadParameters(PreparedStatement ps, UltrasoundRecordBean p) throws SQLException {
        int i=1;
        ps.setLong(i++,p.getPatientID());
        ps.setLong(i++,p.getVisitID());
        ps.setTimestamp(i++, Timestamp.valueOf(p.getCreatedDate()));
        ps.setString(i++,p.getURL());
        ps.setDouble(i++,p.getCRL());
        ps.setDouble(i++,p.getBPD());
        ps.setDouble(i++,p.getHC());
        ps.setDouble(i++,p.getFL());
        ps.setDouble(i++,p.getOFD());
        ps.setDouble(i++,p.getAC());
        ps.setDouble(i++,p.getHL());
        ps.setDouble(i++,p.getEFW());
        return ps;
    }

}
