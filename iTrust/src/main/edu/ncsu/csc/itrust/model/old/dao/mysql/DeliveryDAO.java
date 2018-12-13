package edu.ncsu.csc.itrust.model.old.dao.mysql;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.DeliveryBeanLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeliveryDAO {
    private transient final DAOFactory factory;
    private DeliveryBeanLoader deliveryBeanLoader;

    public DeliveryDAO(final DAOFactory factory) {
        this.factory = factory;
        this.deliveryBeanLoader = new DeliveryBeanLoader();
    }

    public List<DeliveryBean> searchByMotherID(long motherID) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, childhospitalvisitID, sex, patientID, " +
                     "motherID, deliverTime, deliverDate FROM delivery where motherID = ?")) {
            ps.setLong(1, motherID);
        	ResultSet rs = ps.executeQuery();
            List<DeliveryBean> chvList = deliveryBeanLoader.loadList(rs);
            rs.close();
            return chvList;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    
   


    public long addDelivery(DeliveryBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO delivery(childhospitalvisitID, sex, patientID, " +
                     "motherID, deliverTime, deliverDate) VALUES(?, ?, ?, ?, ?, ?)")) {
            ps.setLong(1, p.getChildHospitalVisitID());
            ps.setString(2, p.getSex());
            ps.setLong(3, p.getPatientID());
            ps.setLong(4, p.getMotherID());
            ps.setString(5, p.getDeliverTime());
            ps.setString(6, p.getDeliverDate());
            ps.executeUpdate();
            return DBUtil.getLastInsert(conn);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean editDelivery(DeliveryBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE delivery SET sex = ?, deliverTime = ?, deliverDate = ? WHERE id=?")) {
            stmt.setString(1, p.getSex());
            stmt.setString(2, p.getDeliverTime());
            stmt.setString(3, p.getDeliverDate());
            stmt.setLong(4, p.getId());

            final int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void deleteDelivery(long patientID) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM delivery WHERE patientID = ?")) {
            ps.setLong(1, patientID);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
