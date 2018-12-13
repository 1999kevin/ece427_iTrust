package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;

public class CheckChildHospitalVisitAction {
    private ChildHospitalVisitDAO childHospitalVisitDAO;

    public CheckChildHospitalVisitAction(DAOFactory factory) {
        this.childHospitalVisitDAO = factory.getChildHospitalVisitDAO();
    }

    public boolean checkForChildHospitalVisit(int apptID) throws DBException {
        try {
            return childHospitalVisitDAO.checkChildHospitalVisitExists(apptID);
        } catch (DBException e) {
        	throw e;
        }
    }


}
