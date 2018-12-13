package edu.ncsu.csc.itrust.controller.officeVisit.obstetricsOfficeVisit;

import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.controller.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@ManagedBean(name = "office_visit_form")
@ViewScoped
public class ObstetricsOfficeVisitForm {
	private ObstetricsOfficeVisitController controller;
	private ObstetricsOfficeVisit ov;
	private long ID;
	private long patientID;
	private LocalDateTime createdDate;
	private String locationID;
	private long apptTypeID;
	private String notes;
	private boolean sendBill;
	private LocalDateTime LMP;
	private double weightInPounds;
	private String bloodPressure;
	private int FHR;
	private int numberOfBaby;
	private boolean lowLyingPlacenta;

	public ObstetricsOfficeVisitController getController() {
		return controller;
	}

	public void setController(ObstetricsOfficeVisitController controller) {
		this.controller = controller;
	}

	public ObstetricsOfficeVisit getOv() {
		return ov;
	}

	public void setOv(ObstetricsOfficeVisit ov) {
		this.ov = ov;
	}

	public long getID() {
		return ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public long getApptTypeID() {
		return apptTypeID;
	}

	public void setApptTypeID(long apptTypeID) {
		this.apptTypeID = apptTypeID;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isSendBill() {
		return sendBill;
	}

	public void setSendBill(boolean sendBill) {
		this.sendBill = sendBill;
	}

	public LocalDateTime getLMP() {
		return LMP;
	}

	public void setLMP(LocalDateTime LMP) {
		this.LMP = LMP;
	}

	public double getWeightInPounds() {
		return weightInPounds;
	}

	public void setWeightInPounds(double weightInPounds) {
		this.weightInPounds = weightInPounds;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public int getFHR() {
		return FHR;
	}

	public void setFHR(int FHR) {
		this.FHR = FHR;
	}

	public int getNumberOfBaby() {
		return numberOfBaby;
	}

	public void setNumberOfBaby(int numberOfBaby) {
		this.numberOfBaby = numberOfBaby;
	}

	public boolean isLowLyingPlacenta() {
		return lowLyingPlacenta;
	}

	public void setLowLyingPlacenta(boolean lowLyingPlacenta) {
		this.lowLyingPlacenta = lowLyingPlacenta;
	}

	/**
	 * Default constructor for OfficeVisitForm.
	 */
	public ObstetricsOfficeVisitForm() {
		this(null);
	}

	/**
	 * Constructor for OfficeVisitForm for testing purpses.
	 */
	public ObstetricsOfficeVisitForm(ObstetricsOfficeVisitController ovc) {
		try {
			controller = (ovc == null) ? new ObstetricsOfficeVisitController() : ovc;
			ov = controller.getSelectedVisit();
			if (ov == null) {
				ov = new ObstetricsOfficeVisit();
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("officeVisitId", ov.getID());
			} catch (NullPointerException e) {
				// Do nothing
			}
			ID = ov.getID();
			patientID = ov.getPatientID();
			if (patientID == 0) {
				patientID = Long.parseLong(
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pid"));
			}
			createdDate = ov.getCreatedDate();
			locationID = ov.getLocationID();
			apptTypeID = ov.getApptTypeID();
			sendBill = ov.isSendBill();
			notes = ov.getNotes();
			
			LMP = ov.getLMP();
			weightInPounds = ov.getWeightInPounds();
			bloodPressure = ov.getBloodPressure();
			FHR = ov.getFHR();
			numberOfBaby = ov.getNumberOfBaby();
			lowLyingPlacenta = ov.isLowLyingPlacenta();

		} catch (Exception e) {
			FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Office Visit Controller Error",
					"Office Visit Controller Error");
			FacesContext.getCurrentInstance().addMessage(null, throwMsg);
		}
	}
	
	/**
	 * Called when user clicks on the submit button in officeVisitInfo.xhtml. Takes data from form
	 * and sends to OfficeVisitMySQLLoader.java for storage and validation
	 */
	public void submit() {
		ov.setApptTypeID(apptTypeID);
		ov.setCreatedDate(createdDate);
		ov.setLocationID(locationID);
		ov.setNotes(notes);
		ov.setSendBill(sendBill);
		ov.setPatientID(patientID);
		
		if (isOfficeVisitCreated()) {
			controller.edit(ov);
			controller.logTransaction(TransactionType.OFFICE_VISIT_EDIT, String.valueOf(ov.getID()));
		} else {
			long pid = -1;
			
			FacesContext ctx = FacesContext.getCurrentInstance();

			String patientID = "";
			
			if (ctx.getExternalContext().getRequest() instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
				HttpSession httpSession = req.getSession(false);
				patientID = (String) httpSession.getAttribute("pid");
			}
			if (ValidationFormat.NPMID.getRegex().matcher(patientID).matches()) {
				pid = Long.parseLong(patientID);
			}
			
			ov.setPatientID(pid);
			ov.setID(0);
			long generatedVisitId = controller.addReturnGeneratedId(ov);
			setID(generatedVisitId);
			ov.setID(generatedVisitId);
			controller.logTransaction(TransactionType.OFFICE_VISIT_CREATE, String.valueOf(ov.getID()));
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("officeVisitId", generatedVisitId);
		}
	}
	
	/**
	 * Called when user updates health metrics on officeVisitInfo.xhtml.
	 */
	public void submitHealthMetrics() {
//        boolean isNew = ov.getHouseholdSmokingStatus() == null || ov.getHouseholdSmokingStatus() == 0;
		// Some error checking here?
		ov.setLMP(LMP);
		ov.setWeightInPounds(weightInPounds);
		ov.setBloodPressure(bloodPressure);
		ov.setFHR(FHR);
		ov.setNumberOfBaby(numberOfBaby);
		ov.setLowLyingPlacenta(lowLyingPlacenta);
		controller.edit(ov);
//		if (isNew){
//		    controller.logTransaction(TransactionType.CREATE_BASIC_HEALTH_METRICS, "Age: " + controller.calculatePatientAge(patientID, createdDate).toString());
//		} else {
//		    controller.logTransaction(TransactionType.EDIT_BASIC_HEALTH_METRICS, "Age: " + controller.calculatePatientAge(patientID, createdDate));
//		}
	}
	
	/**
	 * @return true if the Patient is a baby 
	 */
	public boolean isPatientABaby() {
		return controller.isPatientABaby(getPatientID(), getCreatedDate());
	}
	
	/**
	 * @return true if the Patient is a child 
	 */
	public boolean isPatientAChild() {
		return controller.isPatientAChild(getPatientID(), getCreatedDate());
	}
	
	/**
	 * @return true if the Patient is an adult 
	 */
	public boolean isPatientAnAdult() {
		return controller.isPatientAnAdult(getPatientID(), getCreatedDate());
	}
	
	public boolean isOfficeVisitCreated() {
		return (ID != 0) && (ID > 0);
	}
}
