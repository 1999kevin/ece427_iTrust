package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.DeliveryDAO;

import java.util.List;

public class SearchDeliveryAction {
    DeliveryDAO deliveryDAO;

    public SearchDeliveryAction(DAOFactory factory) {
        this.deliveryDAO = factory.getDeliveryDAO();
    }

    public List<DeliveryBean> searchByMotherID(long motherID) {
        try {
            return deliveryDAO.searchByMotherID(motherID);
        } catch (DBException e) {
            return null;
        }
    }
}
