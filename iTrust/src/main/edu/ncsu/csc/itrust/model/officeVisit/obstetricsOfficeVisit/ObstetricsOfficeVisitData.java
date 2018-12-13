package edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.DataBean;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;

import java.time.LocalDate;
import java.util.List;

public interface ObstetricsOfficeVisitData extends DataBean<ObstetricsOfficeVisit>{
	public List<ObstetricsOfficeVisit> getVisitsForPatient(Long patientID) throws DBException;

	public LocalDate getPatientDOB(Long patientID);

	/**
	 * Add office visit to the database and return the generated VisitID.
	 * 
	 * @param ov
	 * 			Office visit to add to the database
	 * @return VisitID generated from the database insertion, -1 if nothing was generated
	 * @throws DBException if error occurred in inserting office visit
	 */
	public long addReturnGeneratedId(ObstetricsOfficeVisit ov) throws DBException;
}
