package edu.ncsu.csc.itrust.model.old.dao.mysql;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PatientPrescriptionLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PatientPrescriptionDAO {
    private transient final DAOFactory factory;
    private PatientPrescriptionLoader patientPrescriptionLoader;// Ob loader


    public PatientPrescriptionDAO(final DAOFactory factory){
        this.factory = factory;
        this.patientPrescriptionLoader = new PatientPrescriptionLoader();//loader initialize
    }

    public long add(PatientPrescriptionBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = patientPrescriptionLoader.loadParameters(conn.prepareStatement("INSERT INTO "
                     + "patientprescription(patientID, createdDate, name, notes, dosage) "
                     + "VALUES(?, ?, ?, ?, ?)"), p)) {
            ps.executeUpdate();
            return DBUtil.getLastInsert(conn);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long getPatientID(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT patientID FROM patientprescription WHERE ID=?")) {
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

    public Date getCreatedDate(long id) throws ITrustException, DBException {
        try (Connection conn = factory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT createdDate FROM patientprescription WHERE ID=?")) {
            ps.setLong(1, id);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                Date result = rs.getDate("createdDate");
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

    public PatientPrescriptionBean getPatientPrescription(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM patientprescription WHERE ID = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            PatientPrescriptionBean patientprescription = rs.next() ? patientPrescriptionLoader.loadSingle(rs) : null;
            rs.close();
            return patientprescription;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<PatientPrescriptionBean> getPatientPrescriptionByPatientId(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM patientprescription WHERE patientID = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ArrayList<PatientPrescriptionBean> list = new ArrayList<>();
            List<PatientPrescriptionBean> patientprescriptions = patientPrescriptionLoader.loadList(rs);
            rs.close();
            return patientprescriptions;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void edit(PatientPrescriptionBean p) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = patientPrescriptionLoader.loadParameters(conn.prepareStatement("UPDATE patientprescription SET patientID=?,"
                             + "createdDate=?, name=?, notes=?, dosage=? "
                             + "WHERE ID=?"), p)) {
            ps.setLong(6, p.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean delete(long id) throws DBException {
        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn
                     .prepareStatement("DELETE FROM patientprescription WHERE ID = ?")) {
            stmt.setLong(1, id);
            boolean removed = stmt.executeUpdate() != 0;
            return removed;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    public List<PatientPrescriptionBean> searchForObstetricsWithName(String firstName, String lastName) throws DBException{
        if (firstName.equals("%") && lastName.equals("%")) {
            return new ArrayList<PatientPrescriptionBean>();
        }
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT o.ID, o.patientID, o.createdDate, o.name, o.notes, o.dosage" +
                     " FROM (patients AS p INNER JOIN patientprescription AS o ON p.MID = o.patientID)" +
                     " WHERE p.firstName LIKE ? AND p.lastName LIKE ?")) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            // Here we need an interface for receiving List<ResultSet>
            List<PatientPrescriptionBean> patientsList = patientPrescriptionLoader.loadList(rs);
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
    public List<PatientPrescriptionBean> fuzzySearchForObstetricsWithName(String firstName, String lastName) throws DBException{
        if (firstName.equals("%") && lastName.equals("%")) {
            return new ArrayList<PatientPrescriptionBean>();
        }
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT o.ID,o.patientID,o.createdDate,o.name,o.notes,o.dosage" +
                     " FROM (patients AS p INNER JOIN patientprescription AS o ON p.MID = o.patientID)" +
                     " WHERE p.firstName LIKE ? AND p.lastName LIKE ?")) {
            ps.setString(1, "%" + firstName + "%");
            ps.setString(2, "%" + lastName + "%");
            ResultSet rs = ps.executeQuery();

            // Here we need an interface for receiving List<ResultSet>
            List<PatientPrescriptionBean> patientsList = patientPrescriptionLoader.loadList(rs);
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

    public List<PatientPrescriptionBean> fuzzeSearchForObstetricsWithMID(long MID) throws DBException{
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT o.ID,o.patientID,o.createdDate,o.name,o.notes,o.dosage" +
                     " FROM (patients AS p INNER JOIN patientprescription AS o ON p.MID = o.patientID)" +
                     " WHERE o.patientID LIKE ?")) {
            ps.setString(1, "%" + MID + "%");

            ResultSet rs = ps.executeQuery();
            List<PatientPrescriptionBean> loadlist = patientPrescriptionLoader.loadList(rs);
            rs.close();
            return loadlist;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
