package edu.ncsu.csc.itrust.model.old.beans.loaders;

import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChildHospitalVisitLoader implements BeanLoader<ChildHospitalVisitBean> {
    @Override
    public List<ChildHospitalVisitBean> loadList(ResultSet rs) throws SQLException {
        List<ChildHospitalVisitBean> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;

    }


    private void loadCommon(ResultSet rs, ChildHospitalVisitBean p) throws SQLException {
        p.setID(rs.getLong("id"));
        p.setPatientID(rs.getLong("patientID"));
        p.setApptID(rs.getInt("apptID"));
        p.setPreferredDeliveryType(rs.getString("preferredDeliveryType"));
        p.setActualDeliveryType(rs.getString("actualDeliveryType"));
        p.setPitocin(rs.getInt("pitocin"));
        p.setNitrousOxide(rs.getInt("nitrousOxide"));
        p.setPethidine(rs.getInt("pethidine"));
        p.setEpiduralAnaesthesia(rs.getInt("epiduralAnaesthesia"));
        p.setMagnesiumSulfate(rs.getInt("magnesiumSulfate"));
        p.setRHImmuneGlobulin(rs.getInt("RHImmuneGlobulin"));

    }

    @Override
    public ChildHospitalVisitBean loadSingle(ResultSet rs) throws SQLException {
        ChildHospitalVisitBean p = new ChildHospitalVisitBean();
        loadCommon(rs, p);
        return p;
    }

    @Override
    public PreparedStatement loadParameters(PreparedStatement ps, ChildHospitalVisitBean p) throws SQLException {
        int i=1;
        ps.setLong(i++, p.getPatientID());
        ps.setInt(i++, p.getApptID());
        ps.setString(i++,p.getPreferredDeliveryType());
        ps.setString(i++,p.getActualDeliveryType());
        ps.setInt(i++,p.getPitocin());
        ps.setInt(i++,p.getNitrousOxide());
        ps.setInt(i++,p.getPethidine());
        ps.setInt(i++,p.getEpiduralAnaesthesia());
        ps.setInt(i++,p.getMagnesiumSulfate());
        ps.setInt(i++,p.getRHImmuneGlobulin());
        return ps;
    }
}
