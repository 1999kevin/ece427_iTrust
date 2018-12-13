package edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit;

import com.mysql.jdbc.Statement;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitValidator;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;

@ManagedBean
public class ObstetricsOfficeVisitMySQL implements Serializable, ObstetricsOfficeVisitData {

    @Resource(name = "jdbc/itrust2")
    private ObstetricsOfficeVisitSQLLoader ovLoader;
    private ObstetricsOfficeVisitValidator validator;
    private static final long serialVersionUID = -8631210448583854595L;
    private DataSource ds;

    public ObstetricsOfficeVisitMySQL() throws DBException {
        ovLoader = new ObstetricsOfficeVisitSQLLoader();
        try {
            Context ctx = new InitialContext();
            this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
        } catch (NamingException e) {
            throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
        }
        validator = new ObstetricsOfficeVisitValidator(this.ds);
    }

    public ObstetricsOfficeVisitMySQL(DataSource ds) {
        ovLoader = new ObstetricsOfficeVisitSQLLoader();
        this.ds = ds;
        validator = new ObstetricsOfficeVisitValidator(this.ds);
    }

    @Override
    public List<ObstetricsOfficeVisit> getVisitsForPatient(Long patientID) throws DBException {
        Connection conn = null;
        PreparedStatement pstring = null;
        ResultSet results = null;
        if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
            try {
                conn = ds.getConnection();
                pstring = conn.prepareStatement("SELECT * FROM obstetricsOfficeVisit WHERE patientID=?");
                pstring.setLong(1, patientID);
                results = pstring.executeQuery();

                final List<ObstetricsOfficeVisit> visitList = ovLoader.loadList(results);
                return visitList;
            } catch (SQLException e) {
                throw new DBException(e);
            } finally {
                try {
                    if (results != null) {
                        results.close();
                    }
                } catch (SQLException e) {
                    throw new DBException(e);
                } finally {
                    DBUtil.closeConnection(conn, pstring);
                }
            }

        } else {
            return null;
        }

    }

    @Override
    public boolean add(ObstetricsOfficeVisit ov) throws  DBException {
        return addReturnGeneratedId(ov) >= 0;
    }

    @Override
    public long addReturnGeneratedId(ObstetricsOfficeVisit ov) throws DBException{
        Connection conn = null;
        PreparedStatement pstring = null;
        try {
            validator.validate(ov);
        } catch (FormValidationException e1) {
            throw new DBException(new SQLException(e1.getMessage()));
        }
        long generatedId = -1;
        try {
            conn = ds.getConnection();
            pstring = ovLoader.loadParameters(conn, pstring, ov, true);
            int result = pstring.executeUpdate();
            if (result != 0) {
                ResultSet generatedKeys = pstring.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, pstring);
        }
        return  generatedId;
    }

    @Override
    public  List<ObstetricsOfficeVisit> getAll() throws DBException {
        Connection conn = null;
        PreparedStatement pstring = null;
        ResultSet results = null;
        try {
            conn = ds.getConnection();
            pstring = conn.prepareStatement("SELECT * FROM officeVisit");
            results = pstring.executeQuery();
            final List<ObstetricsOfficeVisit> visitList = ovLoader.loadList(results);
            return visitList;
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
            } catch (SQLException e) {
                throw new DBException(e);
            } finally {
                DBUtil.closeConnection(conn, pstring);
            }
        }
    }

    @Override
    public ObstetricsOfficeVisit getByID(long id) throws DBException {
        ObstetricsOfficeVisit ret = null;
        Connection conn = null;
        PreparedStatement pstring = null;
        ResultSet results = null;
        List<ObstetricsOfficeVisit> visitList = null;
        try {
            conn = ds.getConnection();
            pstring = conn.prepareStatement("SELECT * FROM obstetricsofficevisit WHERE ID=?");

            pstring.setLong(1, id);

            results = pstring.executeQuery();

            /* May update with loader instead */
            visitList = ovLoader.loadList(results);
            if (visitList.size() > 0) {
                ret = visitList.get(0);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
            } catch (SQLException e) {
                throw new DBException(e);
            } finally {

                DBUtil.closeConnection(conn, pstring);
            }
        }
        return ret;
    }

    @Override
    public boolean update(ObstetricsOfficeVisit ov) throws DBException {
        boolean retval = false;
        Connection conn = null;
        PreparedStatement pstring = null;
        try {
            validator.validate(ov);
        } catch (FormValidationException e1) {
            throw new DBException(new SQLException(e1.getMessage()));
        }
        int results;

        try {
            conn = ds.getConnection();
            pstring = ovLoader.loadParameters(conn, pstring, ov, false);
            results = pstring.executeUpdate();
            retval = (results > 0);
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, pstring);
        }
        return retval;
    }

    public boolean updateURL(long id, String url) throws DBException {
        boolean retval = false;
        Connection conn = null;
        PreparedStatement pstring = null;
        int results;

        try {
            conn = ds.getConnection();
            String stmt = "UPDATE ultrasoundrecord SET URL = ? WHERE ID = ?;";
            pstring = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
            pstring.setString(1, url);
            pstring.setLong(2, id);
            results = pstring.executeUpdate();
            retval = (results > 0);
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, pstring);
        }
        return retval;
    }


    public boolean delete(long id) throws DBException {
        boolean retval = false;
        Connection conn = null;
        PreparedStatement pstring = null;

        int results;

        try {
            conn = ds.getConnection();
            pstring = conn.prepareStatement("DELETE FROM obstetricsOfficeVisit WHERE ID = ?");
            pstring.setLong(1, id);
//            pstring = ovLoader.loadParameters(conn, pstring, ov, false);
            results = pstring.executeUpdate();
            retval = (results > 0);
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, pstring);
        }
        return retval;
    }


    public LocalDate getPatientDOB(final Long patientMID) {
        Connection conn = null;
        PreparedStatement pstring = null;
        ResultSet results = null;
        java.sql.Date patientDOB = null;
        try {
            conn = ds.getConnection();
            pstring = conn.prepareStatement("SELECT DateOfBirth FROM patients WHERE MID=?");
            pstring.setLong(1, patientMID);
            results = pstring.executeQuery();
            if (!results.next()) {
                return null;
            }
            patientDOB = results.getDate("DateOfBirth");
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
            } catch (SQLException e) {
                return null;
            } finally {
                DBUtil.closeConnection(conn, pstring);
            }
        }

        if (patientDOB == null) {
            return null;
        }

        return patientDOB.toLocalDate();
    }
}