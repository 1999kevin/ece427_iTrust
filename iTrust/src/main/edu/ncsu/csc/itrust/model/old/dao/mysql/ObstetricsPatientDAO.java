package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.ObstetricsValidator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class ObstetricsPatientDAO {
    private transient final DAOFactory factory;
    private ObstetricsLoader obstetricsLoader;// Ob loader


    public ObstetricsPatientDAO(final DAOFactory factory){
        this.factory = factory;
        this.obstetricsLoader = new ObstetricsLoader();//loader initialize
    }

    public long addObstetricsRecord(ObstetricsPatientBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = obstetricsLoader.loadParameters(conn.prepareStatement("INSERT INTO "
                     + "obstetricsrecord(patientID, createdDate, yearOfConception, LMP, numberOfWeeksPreg, "
                     + "numberOfLaborHour, weightGain, deliveryType, numberofBaby) "
                     + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"), p)) {
            ps.executeUpdate();
            return DBUtil.getLastInsert(conn);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long getPatientID(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT patientID FROM obstetricsrecord WHERE ID=?")) {
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
            PreparedStatement ps = conn.prepareStatement("SELECT createdDate FROM obstetricsrecord WHERE ID=?")) {
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

    public int getYearOfConception(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT yearOfConception FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                int result = rs.getInt("yearOfConception");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Year of conception has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String getLMP(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT LMP FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                String result = rs.getString("LMP");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("LMP has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String getNumberOfWeeksPreg(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT numberOfWeeksPreg FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                String result = rs.getString("numberOfWeeksPreg");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Number of weeks preg has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public double getNumberOfLaborHour(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT numberOfLaborHour FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                double result = rs.getDouble("numberOfLaborHour");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Number of labor hour has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public double getWeightGain(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT weightGain FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                double result = rs.getDouble("weightGain");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Weight Gain has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String getDeliveryType(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT deliveryType FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                String result = rs.getString("deliveryType");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Delivery type preg has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public int getnumberofBaby(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT numberofBaby FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                int result = rs.getInt("numberofBaby");
                rs.close();
                return result;
            } else {
                rs.close();
                throw new ITrustException("Number of baby has not assigned");//not sure
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public ObstetricsPatientBean getObstetricsPatient(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsrecord WHERE ID = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObstetricsPatientBean obpatient = rs.next() ? obstetricsLoader.loadSingle(rs) : null;
            rs.close();
            return obpatient;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void editObstetricsPatient(ObstetricsPatientBean p, long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = obstetricsLoader.loadParameters(conn.prepareStatement("UPDATE obstetricsrecord SET patientID=?,"
                             + "createdDate=?, yearOfConception=?, LMP=?, numberOfWeeksPreg=?,"
                             + "numberOfLaborHour=?, weightGain=?, deliveryType=?, numberofBaby=?,"
                             + "WHERE ID=?"), p)) {
            ps.setLong(10, p.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean checkObstetricsPatientExists(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsrecord WHERE MID=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            return exists;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean checkObstetricsRecordExists(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsrecord WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            return exists;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    public List<ObstetricsPatientBean> searchForObstetricsWithName(String firstName, String lastName) throws DBException{
        if (firstName.equals("%") && lastName.equals("%")) {
            return new ArrayList<ObstetricsPatientBean>();
        }
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT o.ID,o.patientID,p.firstName,p.lastName,o.createdDate,o.LMP,o.yearOfConception,o.numberOfWeeksPreg,o.numberOfLaborHour,o.weightGain,o.deliveryType,o.numberofBaby" +
                     " FROM (patients AS p INNER JOIN obstetricsrecord AS o ON p.MID = o.patientID)" +
                     " WHERE p.firstName LIKE ? AND p.lastName LIKE ?")) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            // Here we need an interface for receiving List<ResultSet>
            List<ObstetricsPatientBean> patientsList = obstetricsLoader.loadList(rs);
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
    public List<ObstetricsPatientBean> fuzzySearchForObstetricsWithName(String firstName, String lastName) throws DBException{
        if (firstName.equals("%") && lastName.equals("%")) {
            return new ArrayList<ObstetricsPatientBean>();
        }
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT o.ID,o.patientID,p.firstName,p.lastName,o.createdDate,o.LMP,o.yearOfConception,o.numberOfWeeksPreg,o.numberOfLaborHour,o.weightGain,o.deliveryType,o.numberofBaby" +
                     " FROM (patients AS p INNER JOIN obstetricsrecord AS o ON p.MID = o.patientID)" +
                     " WHERE p.firstName LIKE ? AND p.lastName LIKE ?")) {
            ps.setString(1, "%" + firstName + "%");
            ps.setString(2, "%" + lastName + "%");
            ResultSet rs = ps.executeQuery();

            // Here we need an interface for receiving List<ResultSet>
            List<ObstetricsPatientBean> patientsList = obstetricsLoader.loadList(rs);
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
     * @param MID
     *            The patient's MID.
     * @return A java.util.List of ObstetricsPatientBean.
     * @throws DBException
     */

    public List<ObstetricsPatientBean> fuzzeSearchForObstetricsWithMID(long MID) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT o.ID,o.patientID,p.firstName,p.lastName,o.createdDate,o.LMP,o.yearOfConception,o.numberOfWeeksPreg,o.numberOfLaborHour,o.weightGain,o.deliveryType,o.numberofBaby" +
                     " FROM (patients AS p INNER JOIN obstetricsrecord AS o ON p.MID = o.patientID)" +
                     " WHERE o.patientID LIKE ?")) {
            ps.setString(1, "%" + MID + "%");

            ResultSet rs = ps.executeQuery();
            List<ObstetricsPatientBean> loadlist = obstetricsLoader.loadList(rs);
            rs.close();
            Collections.sort(loadlist, new Comparator<ObstetricsPatientBean>(){
                public int compare(ObstetricsPatientBean p1, ObstetricsPatientBean p2) {
                    //按照Person的年龄进行升序排列
                    if (ObstetricsValidator.daysOfPreg(p1.getCreatedDate(), p2.getCreatedDate()) < 0) {
                        return 1;
                    }
                    if (ObstetricsValidator.daysOfPreg(p1.getCreatedDate(), p2.getCreatedDate()) == 0) {
                        return 0;
                    }
                    return -1;
                }
            });
            return loadlist;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
