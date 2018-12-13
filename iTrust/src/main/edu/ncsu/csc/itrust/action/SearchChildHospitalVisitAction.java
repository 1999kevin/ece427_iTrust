package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;

import java.util.List;

public class SearchChildHospitalVisitAction {
    private ChildHospitalVisitDAO childHospitalVisitDAO;

    public SearchChildHospitalVisitAction(DAOFactory factory) {
        this.childHospitalVisitDAO = factory.getChildHospitalVisitDAO();
    }

    public List<ChildHospitalVisitBean> fuzzySearchForChildHospitalVisit(long id, int apptID) {
        try {
            return childHospitalVisitDAO.searchByPatientID(id, apptID);
        } catch (DBException e) {
            return null;
        }
    }

}
