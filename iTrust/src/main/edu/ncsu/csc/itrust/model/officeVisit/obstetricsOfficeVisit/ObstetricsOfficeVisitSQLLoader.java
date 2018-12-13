package edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.SQLLoader;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;

public class ObstetricsOfficeVisitSQLLoader implements SQLLoader<ObstetricsOfficeVisit> {
    @Override
    public List<ObstetricsOfficeVisit> loadList(ResultSet rs) throws SQLException {
        ArrayList<ObstetricsOfficeVisit> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;
    }

    @Override
    public ObstetricsOfficeVisit loadSingle(ResultSet rs) throws SQLException {
        ObstetricsOfficeVisit retVisit= new ObstetricsOfficeVisit();
        retVisit.setID(rs.getLong("ID"));
        retVisit.setPatientID(rs.getLong("patientID"));
        retVisit.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
        retVisit.setLocationID(rs.getString("locationID"));
        retVisit.setApptTypeID(rs.getLong("apptTypeID"));
        retVisit.setNotes(rs.getString("notes"));
        retVisit.setSendBill(rs.getBoolean("sendBill"));
        retVisit.setLMP(rs.getTimestamp("LMP").toLocalDateTime());
        retVisit.setWeightInPounds(rs.getLong("weightInPounds"));
        retVisit.setBloodPressure(rs.getString("bloodPressure"));
        retVisit.setFHR(rs.getInt("FHR"));
        retVisit.setNumberOfBaby(rs.getInt("numberOfBaby"));
        retVisit.setLowLyingPlacenta(rs.getBoolean("lowLyingPlacenta"));

        return retVisit;
    }

    @Override
    public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ObstetricsOfficeVisit ov, boolean newInstance)
            throws SQLException {
        String stmt = "";
        if (newInstance) {
            stmt = "INSERT INTO obstetricsOfficeVisit(patientID, createdDate, "
                    + "locationID, apptTypeID, notes, sendBill, LMP, weightInPounds, "
                    + "bloodPressure, FHR, numberOfBaby, lowLyingPlacenta) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        } else {
            long id = ov.getID();
            stmt = "UPDATE obstetricsOfficeVisit SET "
                    + "patientID=?, "
                    + "createdDate=?, "
                    + "locationID=?, "
                    + "apptTypeID=?, "
                    + "notes=?, "
                    + "sendBill=?, "
                    + "LMP=?, "
                    + "weightInPounds=?, "
                    + "bloodPressure=?, "
                    + "FHR=?, "
                    + "numberOfBaby=?, "
                    + "lowLyingPlacenta=? "
                    + "WHERE ID=" + id + ";";
        }
        ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, ov.getPatientID());
        ps.setTimestamp(2, Timestamp.valueOf(ov.getCreatedDate()));
        ps.setString(3, ov.getLocationID());
        ps.setLong(4, ov.getApptTypeID());
        ps.setString(5, ov.getNotes());
        ps.setBoolean(6, ov.isSendBill());
        ps.setTimestamp(7, Timestamp.valueOf(ov.getLMP()));
        ps.setDouble(8, ov.getWeightInPounds());
        ps.setString(9, ov.getBloodPressure());
        ps.setInt(10, ov.getFHR());
        ps.setInt(11, ov.getNumberOfBaby());
        ps.setBoolean(12, ov.isLowLyingPlacenta());

        return ps;
    }

}
