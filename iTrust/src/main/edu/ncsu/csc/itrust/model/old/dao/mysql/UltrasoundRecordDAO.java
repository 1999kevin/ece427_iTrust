package edu.ncsu.csc.itrust.model.old.dao.mysql;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.UltrasoundFileLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.UltrasoundRecordBeanLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundFileValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UltrasoundRecordDAO {
    private transient final DAOFactory factory;
    private UltrasoundRecordBeanLoader ultrasoundRecordBeanLoader;// Ob loader
    private UltrasoundFileLoader ultrasoundFileLoader;


    public UltrasoundRecordDAO(final DAOFactory factory){
        this.factory = factory;
        this.ultrasoundRecordBeanLoader = new UltrasoundRecordBeanLoader();//loader initialize
        this.ultrasoundFileLoader = new UltrasoundFileLoader();
    }

    public long addUltrasoundRecord(UltrasoundRecordBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = ultrasoundRecordBeanLoader.loadParameters(conn.prepareStatement("INSERT INTO "
                     + "ultrasoundrecord(patientID, visitID, createdDate, URL, CRL, BPD, "
                     + "HC, FL, OFD, AC, HL, EFW) "
                     + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), p)) {
            ps.executeUpdate();
            return DBUtil.getLastInsert(conn);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long getPatientID(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT patientID FROM ultrasoundrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                long result = rs.getLong("patientID");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Patient ID has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String getCreatedDate(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT createdDate FROM ultrasoundrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                String result = rs.getString("createdDate");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Created Date has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    public UltrasoundRecordBean getUltrasoundRecord(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundrecord WHERE ID = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            UltrasoundRecordBean obpatient = rs.next() ? ultrasoundRecordBeanLoader.loadSingle(rs) : null;
            rs.close();
            return obpatient;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }




    public void editUltrasoundRecord(UltrasoundRecordBean p, long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = ultrasoundRecordBeanLoader.loadParameters(conn.prepareStatement("UPDATE ultrasoundrecord SET "
                             + "patientID=?, visitID=?, createdDate=?, URL=?, CRL=?, BPD=?, HC=?, FL=?, OFD=?, AC=?, HL=?, EFW=? "
                             + "WHERE ID=?"), p)) {
            ps.setLong(13, p.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean deleteUltrasoundRecord(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn
                     .prepareStatement("DELETE FROM ultrasoundrecord WHERE ID = ?")) {
            stmt.setLong(1, id);
            boolean removed = stmt.executeUpdate() != 0;
            return removed;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean checkUltrasoundRecordExists(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            return exists;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    public List<UltrasoundRecordBean> searchForUltrasoundRecordWithName(String firstName, String lastName) throws DBException{
        if (firstName.equals("%") && lastName.equals("%")) {
            return new ArrayList<UltrasoundRecordBean>();
        }
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT u.ID, u.patientID, u.visitID, u.createdDate, u.URL, u.CRL, u.BPD, u.HC, u.FL, u.OFD, u.AC, u.HL, u.EFW" +
                     " FROM (patients AS p INNER JOIN ultrasoundrecord AS u ON p.MID = u.patientID)" +
                     " WHERE p.firstName LIKE ? AND p.lastName LIKE ?")) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            // Here we need an interface for receiving List<ResultSet>
            List<UltrasoundRecordBean> patientsList = ultrasoundRecordBeanLoader.loadList(rs);
            rs.close();
            return patientsList;
        } catch (SQLException e) {
            throw new DBException(e);
        }

    }


    /**
     * Returns all patients with names "LIKE" (as in SQL) the passed in
     * parameters.
     *
     * @param firstName
     *            The patient's first name.
     * @param lastName
     *            The patient's last name.
     * @return A java.util.List of ObstetricsPatientBean.
     * @throws DBException
     */
    public List<UltrasoundRecordBean> fuzzySearchForUltrasoundRecordWithName(String firstName, String lastName) throws DBException{
        if (firstName.equals("%") && lastName.equals("%")) {
            return new ArrayList<UltrasoundRecordBean>();
        }
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT u.ID, u.patientID, u.visitID, u.createdDate, u.URL, u.CRL, u.BPD, u.HC, u.FL, u.OFD, u.AC, u.HL, u.EFW" +
                     " FROM (patients AS p INNER JOIN ultrasoundrecord AS u ON p.MID = u.patientID)" +
                     " WHERE p.firstName LIKE ? AND p.lastName LIKE ?")) {
            ps.setString(1, "%" + firstName + "%");
            ps.setString(2, "%" + lastName + "%");
            ResultSet rs = ps.executeQuery();

            // Here we need an interface for receiving List<ResultSet>
            List<UltrasoundRecordBean> patientsList = ultrasoundRecordBeanLoader.loadList(rs);
            rs.close();
            return patientsList;
        } catch (SQLException e) {
            throw new DBException(e);
        }

    }

    public UltrasoundFile getFile(long id) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundfile WHERE ID = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            UltrasoundFile file = rs.next() ? ultrasoundFileLoader.loadSingle(rs) : null;
            rs.close();
            return file;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long saveFile(UltrasoundFile file) throws DBException {
        try (
            Connection conn = factory.getConnection();
            PreparedStatement ps = ultrasoundFileLoader.loadParameters(conn.prepareStatement("INSERT INTO "
                 + "ultrasoundfile(recordID, filename, contents)"
                 + "VALUES(?, ?, ?)"), file)) {
            ps.executeUpdate();
            return DBUtil.getLastInsert(conn);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    /**
     * Returns all patients with names "LIKE" (as in SQL) the passed in
     * parameters.
     *
     * @param MID
     *            The patient's MID.
     * @return A java.util.List of ObstetricsPatientBean.
     * @throws DBException
     */

    public List<UltrasoundRecordBean> fuzzeSearchForUltrasoundRecordWithMID(long MID) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT u.ID, u.patientID, u.visitID, u.createdDate, u.URL, u.CRL, u.BPD, u.HC, u.FL, u.OFD, u.AC, u.HL, u.EFW" +
                     " FROM (patients AS p INNER JOIN ultrasoundrecord AS u ON p.MID = u.patientID)" +
                     " WHERE u.patientID LIKE ?")) {
            ps.setString(1, "%" + MID + "%");

            ResultSet rs = ps.executeQuery();
            List<UltrasoundRecordBean> loadlist = ultrasoundRecordBeanLoader.loadList(rs);
            rs.close();
//            Collections.sort(loadlist, new Comparator<UltrasoundRecordBean>(){
//                public int compare(UltrasoundRecordBean p1, UltrasoundRecordBean p2) {
//                    //按照Person的年龄进行升序排列
//                    if (ObstetricsValidator.daysOfPreg(p1.getCreatedDate(), p2.getCreatedDate()) < 0) {
//                        return 1;
//                    }
//                    if (ObstetricsValidator.daysOfPreg(p1.getCreatedDate(), p2.getCreatedDate()) == 0) {
//                        return 0;
//                    }
//                    return -1;
//                }
//            });
            return loadlist;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<UltrasoundRecordBean> fuzzeSearchForUltrasoundRecordWithVisitID(long VisitID) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT u.ID, u.patientID, u.visitID, u.createdDate, u.URL, u.CRL, u.BPD, u.HC, u.FL, u.OFD, u.AC, u.HL, u.EFW" +
                     " FROM (obstetricsofficevisit AS o INNER JOIN ultrasoundrecord AS u ON o.ID = u.visitID)" +
                     " WHERE u.visitID LIKE ?")) {
            ps.setString(1, "%" + VisitID + "%");

            ResultSet rs = ps.executeQuery();
            List<UltrasoundRecordBean> loadlist = ultrasoundRecordBeanLoader.loadList(rs);
            rs.close();
//            Collections.sort(loadlist, new Comparator<UltrasoundRecordBean>(){
//                public int compare(UltrasoundRecordBean p1, UltrasoundRecordBean p2) {
//                    //按照Person的年龄进行升序排列
//                    if (ObstetricsValidator.daysOfPreg(p1.getCreatedDate(), p2.getCreatedDate()) < 0) {
//                        return 1;
//                    }
//                    if (ObstetricsValidator.daysOfPreg(p1.getCreatedDate(), p2.getCreatedDate()) == 0) {
//                        return 0;
//                    }
//                    return -1;
//                }
//            });
            return loadlist;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
