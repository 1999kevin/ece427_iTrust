package edu.ncsu.csc.itrust.unit;


import edu.ncsu.csc.itrust.InputValidator;
//import edu.ncsu.csc.itrust.logger.ObstetricsRecordsLogger;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class InputValidatorTest {
    @Before
    public void setUp() throws Exception {
    }

//    public void testLog() throws DBException {
//        ObstetricsRecordsLogger logger = new ObstetricsRecordsLogger(TestDAOFactory.getTestInstance());
//        logger.logCreateObstetricsRecord(1, 2, "2018-09-10");
//        logger.logViewObstetricsRecord(1, 2, "2018-09-10");
//    }
@Test
    public void testValid() {
        InputValidator  validator = new InputValidator();
        validator.isInteger("1");
        validator.isDouble("1.2");
    }

}
