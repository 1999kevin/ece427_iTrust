package edu.ncsu.csc.itrust.action;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.ncsu.csc.itrust.controller.diagnosis.DiagnosisController;
import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.diagnosis.Diagnosis;
import edu.ncsu.csc.itrust.model.diagnosis.DiagnosisMySQL;
import edu.ncsu.csc.itrust.model.icdcode.ICDCode;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.old.beans.*;
import edu.ncsu.csc.itrust.model.old.beans.*;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.*;
import edu.ncsu.csc.itrust.model.old.dao.mysql.*;

import javax.sql.DataSource;

/**
 * SearchUsersAction
 */
@SuppressWarnings("unused")
public class SearchUsersAction {
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private ObstetricsPatientDAO obstetricsPatientDAO;
	private UltrasoundRecordDAO ultrasoundRecordDAO;
	private ChildHospitalVisitDAO childHospitalVisitDAO;
	private DeliveryDAO deliveryDAO;
	private RemoteMonitoringDAO rmDAO;
	private DataSource ds;
	private ObstetricsOfficeVisitMySQL ovsql;
	//private OfficeVisitController ocl;
	private OfficeVisitMySQL ocl;
	private DiagnosisMySQL dcl;
	private AllergyDAO allergyDAO;
	private PatientPrescriptionDAO patientPrescriptionDAO;

	/**
	 * Set up defaults
	 *
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the user who is performing the search.
	 */
	public SearchUsersAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.obstetricsPatientDAO = factory.getObDAO();
		this.ultrasoundRecordDAO = factory.getUltrasoundecordDAO();
		this.childHospitalVisitDAO = factory.getChildHospitalVisitDAO();
		this.allergyDAO = factory.getAllergyDAO();
		this.rmDAO = factory.getRemoteMonitoringDAO();
		this.deliveryDAO = factory.getDeliveryDAO();
		this.patientPrescriptionDAO = factory.getPatientPrescriptionDAO();
	}

	/**
	 * Search the Labor and Delivery Report 
	 *
	 * @param patientMID The MID of the patient who is performing the search.
	 */
	
	public RecordBean searchRecordWithMID(long patientMID) throws DBException {
		RecordBean rb = new RecordBean();
		List<ObstetricsPatientBean> opb = fuzzySearchForObRecWithMID(patientMID);
		if(opb != null && opb.size()>=1){
			List<String> Pregnancyterm = new ArrayList<>();
			List<Integer> YearOfConception = new ArrayList<>();
			List<String> DeliveryType = new ArrayList<>();
			for (ObstetricsPatientBean ob : opb) {
				Pregnancyterm.add(ob.getNumberOfWeeksPreg());
				YearOfConception.add(ob.getYearOfConception());
				DeliveryType.add(ob.getDeliveryType());
			}
			rb.sePregnancyterm(Pregnancyterm);
			rb.setYearOfConception(YearOfConception);
			rb.setDeliveryType(DeliveryType);
			
			List<DeliveryBean> db = searchByMotherID(patientMID);
			if(db != null && db.size()>=1) {
				rb.setDeliveryDate(db.get(0).getDeliverDate());
			}
		}
		
		List<PatientBean> pb = fuzzySearchForPatientsWithMID(patientMID);
		if (pb != null && pb.size()>=1) {
			rb.setBloodType(pb.get(0).getBloodType());

			rb.setAdvancedMaternalAge(pb.get(0).getAgeInWeeks()/48 <35);
			
			List<RemoteMonitoringDataBean> reb = getPatientDataByType(patientMID);
			if(reb != null && reb.size()>=1) {
				rb.setHighBloodPressure(reb.get(0).getDiastolicBloodPressure() < 140 && reb.get(0).getSystolicBloodPressure()<80);
			}
			
			List<edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit> obs;
			ObstetricsOfficeVisitMySQL oov;
			try {
				oov =  new ObstetricsOfficeVisitMySQL(ConverterDAO.getDataSource());
			} catch (Exception e) {
				oov = new ObstetricsOfficeVisitMySQL();
			}
			obs = oov.getVisitsForPatient(patientMID);

			if(obs != null && obs.size()>=1) {
				List<List<String>> reobs = new ArrayList<>();
				for (edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit ob : obs) {
					StringBuilder sb = new StringBuilder();
					List<String> temp = new ArrayList<>();
					long weeks = ChronoUnit.WEEKS.between(ob.getCreatedDate(),ob.getLMP());
					temp.add(String.valueOf(weeks));

					temp.add(String.valueOf(ob.getWeightInPounds()));

					temp.add(ob.getBloodPressure());

					temp.add(String.valueOf(ob.getFHR()));

					temp.add(String.valueOf(ob.getNumberOfBaby()));

					temp.add(String.valueOf(ob.isLowLyingPlacenta()));
					temp.add(ob.getNotes());

					reobs.add(temp);
				}
				rb.setObstetricsOfficeVisit(reobs);
				rb.setAtypicalweight(obs.get(0).getWeightInPounds() < 35);
				rb.setnumberofpregnancy(obs.get(0).getNumberOfBaby());
				rb.setAbnormalFHR(obs.get(0).getFHR()< 120 && obs.get(0).getFHR()> 160);
				rb.setLowlyingplacenta(obs.get(0).isLowLyingPlacenta());
			}
			
			List<OfficeVisit> ov;
			OfficeVisitMySQL ov_mysql;
			try {
				ov_mysql = new OfficeVisitMySQL(ConverterDAO.getDataSource());
			} catch (Exception e) {
				ov_mysql = new OfficeVisitMySQL();
			}
			ov = ov_mysql.getVisitsForPatient(patientMID);
			
			if (ov != null && ov.size()>=1) {
				for (OfficeVisit visit: ov) {
					long vid = visit.getVisitID();
					System.out.println(vid);
					try {
						dcl = new DiagnosisMySQL(ConverterDAO.getDataSource());
					} catch (Exception e) {
						dcl = new DiagnosisMySQL();
					}
					
					List<Diagnosis> dList = getDiagnosesByOfficeVisit(vid);
					
					if (dList == null) {
						continue;
					}
					
					for (Diagnosis d: dList) {
						if (d != null) {
							ICDCode idc = d.getIcdCode();
							String s  = idc.getName();
							boolean isChronic  = idc.isChronic();
							rb.setChronic(isChronic);
							if (s.equals("Hyperemesis gravidarum")) {
								rb.setHyperemesisgravidarum(true);
							}
							if (s.equals("Hypothyroidism")) {
								rb.setHypothyroidism(true);
							}
							if (s.equals("Diabetes")) {
								rb.setDiabetes(true);
							}
							if (s.equals("cancer")) {
								rb.setCancer(true);
							}
							if (s.equals("STDs")) {
								rb.setSDT(true);
							}
						}
					}
					
				}
			}
			List<AllergyBean> ab = getAllergies(patientMID);
			List<String> allegry  = new ArrayList<>();
			if (ab != null && ab.size()>=1) {
				for(AllergyBean a: ab) {
					String s = a.getDescription();
					allegry.add(s);

				}

				rb.setmaternalallergies(allegry);

			}


		}

		return rb;
	}

	public List<PatientBean> fuzzySearchForPatientsWithMID (long patientMID) {
		try{
			return patientDAO.fuzzySearchForPatientsWithMID(patientMID);

		} catch (DBException e) {
			return null;
		}
	}

	public List<AllergyBean> getAllergies(long patientMID) {
		try{
			return allergyDAO.getAllergies(patientMID);
		}catch (DBException e) {
			return null;
		}
	}


	public List<Diagnosis> getDiagnosesByOfficeVisit(long vid) {
		try {
			return dcl.getByVisitID(vid);
		}catch (DBException e) {
			return null;
		}
	}

	public List<RemoteMonitoringDataBean> getPatientDataByType(long patientMID) {
		try{
			return rmDAO.getPatientDataByType(patientMID,"");
		}catch (DBException e) {
			return null;
		}
	}

	public List<DeliveryBean> searchByMotherID(long motherID) {
		try {
			return deliveryDAO.searchByMotherID(motherID);
		} catch (DBException e) {
			return null;
		}
	}


	public List<PatientPrescriptionBean> searchForObstetricsWithName(String firstName, String lastName){
		try {
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			System.out.print(4);
			return patientPrescriptionDAO.searchForObstetricsWithName(firstName, lastName);
		}
		catch (DBException e) {
			System.out.print(5);
			return null;
		}
	}

	public List<PatientPrescriptionBean> fuzzySearchForObstetricsWithName(String firstName, String lastName){
		try {
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return patientPrescriptionDAO.fuzzySearchForObstetricsWithName(firstName, lastName);
		}
		catch (DBException e) {

			return null;
		}
	}

	public List<PatientPrescriptionBean> fuzzeSearchForObstetricsWithMID(long MID){
		try {

			return patientPrescriptionDAO.fuzzeSearchForObstetricsWithMID(MID);
		}
		catch (DBException e) {

			return null;
		}
	}

	public List<UltrasoundRecordBean> searchForUltrasoundRecWithName(String firstName, String lastName){
		try {
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return ultrasoundRecordDAO.searchForUltrasoundRecordWithName(firstName, lastName);
		}
		catch (DBException e) {

			return null;
		}
	}

	public List<UltrasoundRecordBean> fuzzySearchForUltrasoundRecordWithName(String firstName, String lastName){
		try {
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return ultrasoundRecordDAO.fuzzySearchForUltrasoundRecordWithName(firstName, lastName);
		}
		catch (DBException e) {

			return null;
		}
	}


	public List<UltrasoundRecordBean> fuzzeSearchForUltrasoundRecordWithMID(long MID){
		try {

			return ultrasoundRecordDAO.fuzzeSearchForUltrasoundRecordWithMID(MID);
		}
		catch (DBException e) {

			return null;
		}
	}

	public List<UltrasoundRecordBean> fuzzeSearchForUltrasoundRecordWithVisitID(long VisitID){
		try {
			return ultrasoundRecordDAO.fuzzeSearchForUltrasoundRecordWithVisitID(VisitID);
		}
		catch (DBException e) {

			return null;
		}
	}

	public List<ChildHospitalVisitBean> fuzzySearchForChildHospitalVisit(String id, String apptID) {
		try {
			return childHospitalVisitDAO.searchByPatientID(Long.parseLong(id), Integer.parseInt(apptID));
		} catch (DBException e) {
			return null;
		}
	}

	public List<ObstetricsPatientBean> fuzzySearchForObRecWithName(String firstName, String lastName) {

		try {
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return obstetricsPatientDAO.fuzzySearchForObstetricsWithName(firstName, lastName);
		}
		catch (DBException e) {

			return null;
		}
	}

	public List<ObstetricsPatientBean> fuzzySearchForObRecWithMID(long mid) {

		try {

			return obstetricsPatientDAO.fuzzeSearchForObstetricsWithMID(mid);
		}
		catch (DBException e) {

			return null;
		}
	}


	public List<ObstetricsPatientBean> searchForObRecWithName(String firstName, String lastName) {

		try {
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return obstetricsPatientDAO.searchForObstetricsWithName(firstName, lastName);
		}
		catch (DBException e) {

			return null;
		}
	}

	/**
	 * Searches for all personnel with the first name and last name specified in the parameter list.
	 * @param firstName The first name to be searched.
	 * @param lastName The last name to be searched.
	 * @return A java.util.List of PersonnelBeans for the users who matched.
	 */
	public List<PersonnelBean> searchForPersonnelWithName(String firstName, String lastName) {
		
		try {	
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return personnelDAO.searchForPersonnelWithName(firstName, lastName);
		}
		catch (DBException e) {
			
			return null;
		}
	}
	
	/**
	 * Search for all experts with first name and last name given in parameters.
	 * @param query query
	 * @return A java.util.List of PersonnelBeans
	 */
	public List<PersonnelBean> fuzzySearchForExperts(String query) {
		String[] subqueries=null;
		
		List<PersonnelBean> result = new ArrayList<PersonnelBean>();
		if(query!=null && query.length()>0 && !query.startsWith("_")){
			subqueries = query.split(" ");
			int i=0;
			for(String q : subqueries){
				try {
					List<PersonnelBean> first = personnelDAO.fuzzySearchForExpertsWithName(q, "");				
					List<PersonnelBean> last = personnelDAO.fuzzySearchForExpertsWithName("", q);
					
					for(int j=0; j < last.size(); j++){
					  if(!result.contains(last.get(j))){
						  result.add(0, last.get(j));
					  }
					}
					for(int j=0; j < first.size(); j++){
					  if(!result.contains(first.get(j))){
						  result.add(0, first.get(j));
					  }
					}
					i++;
				} catch (DBException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * Search for all patients with first name and last name given in parameters.
	 * @param firstName The first name of the patient being searched.
	 * @param lastName The last name of the patient being searched.
	 * @return A java.util.List of PatientBeans
	 */
	public List<PatientBean> searchForPatientsWithName(String firstName, String lastName) {
	
		try {	
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return patientDAO.searchForPatientsWithName(firstName, lastName);
		}
		catch (DBException e) {
			
			return null;
		}
	}
	
	/**
	 * Search for all patients with first name and last name given in parameters.
	 * @param query query
	 * @return A java.util.List of PatientBeans
	 */
	public List<PatientBean> fuzzySearchForPatients(String query) {
		return fuzzySearchForPatients(query, false);
	}
	
	/**
	 * Search for all patients with first name and last name given in parameters.
	 * @param query query
	 * @param allowDeactivated allowDeactivated
	 * @return A java.util.List of PatientBeans
	 */
	@SuppressWarnings("unchecked")
	public List<PatientBean> fuzzySearchForPatients(String query, boolean allowDeactivated) {
		String[] subqueries=null;
		
		Set<PatientBean> patientsSet = new TreeSet<PatientBean>();
		if(query!=null && query.length()>0 && !query.startsWith("_")){
			subqueries = query.split(" ");
			Set<PatientBean>[] patients = new Set[subqueries.length];
			int i=0;
			for(String q : subqueries){
				try {
					patients[i] = new TreeSet<PatientBean>();
					List<PatientBean> first = patientDAO.fuzzySearchForPatientsWithName(q, "");				
					List<PatientBean> last = patientDAO.fuzzySearchForPatientsWithName("", q);
					patients[i].addAll(first);
					patients[i].addAll(last);
					
					try{
						long mid = Long.valueOf(q);
						//If the patient exists with the mid, then add the patient to the patient list
						List<PatientBean> searchMID = patientDAO.fuzzySearchForPatientsWithMID(mid);
						patients[i].addAll(searchMID);
						
						//old way of doing it when they only were returning one person
						//now that we are returning everybody with that as a substring in their MID, not necessary
						//yet want to keep it in case we revert sometime
						
					}catch(NumberFormatException e) {
						//TODO
					}
					i++;
				} catch (DBException e1) {
					e1.printStackTrace();
				}
			}
			
			if (i > 0) {
				patientsSet.addAll(patients[0]);
			}
			for(Set<PatientBean> results : patients){
				try{
					patientsSet.retainAll(results);
				}catch(NullPointerException e) {
					//TODO
				}
			}
		}
		ArrayList<PatientBean> results=new ArrayList<PatientBean>(patientsSet);
		
		if(allowDeactivated == false) {
			for(int i=results.size()-1; i>=0; i--){
				if(!results.get(i).getDateOfDeactivationStr().equals("")){
					results.remove(i);
				}
			}
		}
		Collections.reverse(results);
		return results;
	}

	public List<PatientBean> fuzzySearchForObstetricsPatients(String query, boolean isObstetric) {
		String[] subqueries=null;

		Set<PatientBean> patientsSet = new TreeSet<PatientBean>();
		if(query!=null && query.length()>0 && !query.startsWith("_")){
			subqueries = query.split(" ");
			Set<PatientBean>[] patients = new Set[subqueries.length];
			int i=0;
			for(String q : subqueries){
				try {
					patients[i] = new TreeSet<PatientBean>();
					List<PatientBean> first = patientDAO.fuzzySearchForPatientsWithName(q, "");
					List<PatientBean> last = patientDAO.fuzzySearchForPatientsWithName("", q);
					patients[i].addAll(first);
					patients[i].addAll(last);

					try{
						long mid = Long.valueOf(q);
						//If the patient exists with the mid, then add the patient to the patient list
						List<PatientBean> searchMID = patientDAO.fuzzySearchForPatientsWithMID(mid);
						patients[i].addAll(searchMID);

						//old way of doing it when they only were returning one person
						//now that we are returning everybody with that as a substring in their MID, not necessary
						//yet want to keep it in case we revert sometime

					}catch(NumberFormatException e) {
						//TODO
					}
					i++;
				} catch (DBException e1) {
					e1.printStackTrace();
				}
			}

			if (i > 0) {
				patientsSet.addAll(patients[0]);
			}
			for(Set<PatientBean> results : patients){
				try{
					patientsSet.retainAll(results);
				}catch(NullPointerException e) {
					//TODO
				}
			}
		}
		ArrayList<PatientBean> results=new ArrayList<PatientBean>(patientsSet);

		if(isObstetric) {
			for(int i=results.size()-1; i>=0; i--){
				if(!results.get(i).isObStatus()){
					results.remove(i);
				}
			}
		}
		Collections.reverse(results);
		return results;
	}

	/**
	 * getDeactivated is a special case used for when we want to see all deactivated patients.
	 * @return The List of deactivated patients.
	 */
	public List<PatientBean> getDeactivated(){
		List<PatientBean> result = new ArrayList<PatientBean>();
		try {
			result = patientDAO.getAllPatients();
			for(int i = result.size() - 1; i >= 0; i--){
				if(result.get(i).getDateOfDeactivationStr().equals("")){
					result.remove(i);
				}
			}
		} catch (DBException e) {
			//TODO
		}
		return result;
	}

    public List<PatientBean> getObstetrics(String query) {
        List<PatientBean> result = new ArrayList<PatientBean>();
        try {
            result = fuzzySearchForPatients(query);
            for(int i = result.size() - 1; i >= 0; i--){
                //TODO:if not obstetric remove the record
                if(result.get(i).getDateOfDeactivationStr().equals("")){
                    result.remove(i);
                }
            }
        } catch (Exception e) {
            //TODO
        }
        return result;
    }

}
