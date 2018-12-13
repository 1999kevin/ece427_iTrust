package edu.ncsu.csc.itrust.model.old.dao.mysql;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ChildHospitalVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChildHospitalVisitDAO {
    private transient final DAOFactory factory;
    private ChildHospitalVisitLoader loader;

    public ChildHospitalVisitDAO(final DAOFactory factory){
        this.factory = factory;
        this.loader = new ChildHospitalVisitLoader();//loader initialize
    }

    public long addChildHospitalVisit(ChildHospitalVisitBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = loader.loadParameters(conn.prepareStatement("INSERT INTO "
                     + "childhospitalvisit(patientID, apptID, preferredDeliveryType, actualDeliveryType, pitocin, nitrousOxide, pethidine, "
                     + "epiduralAnaesthesia, magnesiumSulfate, RHImmuneGlobulin) "
                     + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), p)) {

            ps.executeUpdate();
            return DBUtil.getLastInsert(conn);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    
    public boolean checkChildHospitalVisitExists(long apptid) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM childhospitalvisit WHERE apptID=?")) {
            ps.setLong(1, apptid);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            return exists;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<ChildHospitalVisitBean> searchByPatientID(long id, int apptID) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM childhospitalvisit WHERE patientID=? and apptID = ?")) {
            ps.setLong(1, id);
            ps.setInt(2, apptID);
            ResultSet rs;
            rs = ps.executeQuery();
            List<ChildHospitalVisitBean> chvList = loader.loadList(rs);
            rs.close();
            return chvList;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    public boolean editChildHospitalVisit(final ChildHospitalVisitBean childHospitalVisit) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE childhospitalvisit " +
                     "SET preferredDeliveryType = ?, actualDeliveryType = ?, pitocin = ?, nitrousOxide = ?, pethidine = ?," +
                     "epiduralAnaesthesia = ?, magnesiumSulfate = ?, RHImmuneGlobulin = ? WHERE patientID=? and apptID = ?")) {
            stmt.setString(1, childHospitalVisit.getPreferredDeliveryType());
            stmt.setString(2, childHospitalVisit.getActualDeliveryType());
            stmt.setInt(3, childHospitalVisit.getPitocin());
            stmt.setInt(4, childHospitalVisit.getNitrousOxide());
            stmt.setInt(5, childHospitalVisit.getPethidine());
            stmt.setInt(6, childHospitalVisit.getEpiduralAnaesthesia());
            stmt.setInt(7, childHospitalVisit.getMagnesiumSulfate());
            stmt.setInt(8, childHospitalVisit.getRHImmuneGlobulin());
            stmt.setLong(9, childHospitalVisit.getPatientID());
            stmt.setInt(10, childHospitalVisit.getApptID());

            final int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void delete(long id, int apptID) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM childhospitalvisit WHERE patientID=? and apptID = ?")) {
            ps.setLong(1, id);
            ps.setInt(2, apptID);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
