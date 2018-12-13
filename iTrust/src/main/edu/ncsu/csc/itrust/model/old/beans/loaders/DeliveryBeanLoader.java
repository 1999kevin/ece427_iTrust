package edu.ncsu.csc.itrust.model.old.beans.loaders;

import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBeanLoader implements BeanLoader<DeliveryBean>{
    @Override
    public List<DeliveryBean> loadList(ResultSet rs) throws SQLException {
        List<DeliveryBean> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;

    }

    private void loadCommon(ResultSet rs, DeliveryBean p) throws SQLException {
        p.setId(rs.getLong("id"));
        p.setPatientID(rs.getLong("patientID"));
        p.setChildHospitalVisitID(rs.getLong("childhospitalvisitID"));
        p.setSex(rs.getString("sex"));
        p.setMotherID(rs.getLong("motherID"));
        p.setDeliverTime(rs.getString("deliverTime"));
        p.setDeliverDate(rs.getString("deliverDate"));
    }

    @Override
    public DeliveryBean loadSingle(ResultSet rs) throws SQLException {
        DeliveryBean p = new DeliveryBean();
        loadCommon(rs, p);
        return p;
    }

    @Override
    public PreparedStatement loadParameters(PreparedStatement ps, DeliveryBean p) throws SQLException {
        int i=1;
        ps.setLong(i++, p.getChildHospitalVisitID());
        ps.setString(i++,p.getSex());
        ps.setLong(i++, p.getPatientID());
        ps.setLong(i++, p.getMotherID());
        ps.setString(i++, p.getDeliverTime());
        ps.setString(i++, p.getDeliverDate());

        return ps;
    }
}
