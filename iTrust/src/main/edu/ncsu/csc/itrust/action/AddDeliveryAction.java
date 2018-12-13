package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.DeliveryDAO;
import edu.ncsu.csc.itrust.model.old.validate.DeliveryValidator;

public class AddDeliveryAction {
    private DeliveryDAO deliveryDAO;

    public AddDeliveryAction(DAOFactory factory, long loggedInMID) {
        this.deliveryDAO = factory.getDeliveryDAO();
    }

    public long addDelivery(DeliveryBean p) throws FormValidationException {
        try {
            DeliveryValidator validator = new DeliveryValidator();
            validator.validate(p);
            return deliveryDAO.addDelivery(p);
        } catch (Exception e) {
            throw new FormValidationException(e.getMessage());
        }
    }
}
