package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.SearchUsersAction;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class searchForObWithName{
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    @Before
    public void setUp() throws Exception{

    }

    @Test
    public void searchObWithExactName(){
        SearchUsersAction act = new SearchUsersAction(factory, 9000000012L);
        List<ObstetricsPatientBean> ob = act.searchForObRecWithName("Random", "Person");
        for (ObstetricsPatientBean s:ob){
            System.out.print(s.getID()+" ");
            System.out.print(s.getPatientID()+" ");
            System.out.print(s.getCreatedDate());
        }
        assertEquals(1, ob.get(0).getID());
    }

}
