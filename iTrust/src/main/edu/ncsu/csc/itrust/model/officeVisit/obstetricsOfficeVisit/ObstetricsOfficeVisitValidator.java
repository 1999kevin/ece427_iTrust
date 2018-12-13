package edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit;

import edu.ncsu.csc.itrust.controller.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;

import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeData;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeMySQLConverter;
import edu.ncsu.csc.itrust.model.hospital.Hospital;
import edu.ncsu.csc.itrust.model.hospital.HospitalData;
import edu.ncsu.csc.itrust.model.hospital.HospitalMySQLConverter;
import org.apache.commons.validator.CreditCardValidator;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ObstetricsOfficeVisitValidator extends POJOValidator<ObstetricsOfficeVisit> {

    private DataSource ds;

    public ObstetricsOfficeVisitValidator(DataSource ds) {
        this.ds = ds;
    }

    public static String validateInputs(String createdDate,
                                        String locationID,
                                        String apptTypeID,
                                        String notes,
                                        String sendBill,
                                        String LMP,
                                        String weightInPounds,
                                        String bloodPressure,
                                        String FHR,
                                        String numberOfBaby,
                                        String lowLyingPlacenta) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        if(createdDate == null) {
            sb.append("Created Date is null. ");
        } else {
            try{
                df.parse(createdDate);
            }catch(DateTimeParseException ex) {
                sb.append("Created Date should be of format MM/dd/yyyy. ");
            }
        }

        if(LMP == null) {
            sb.append("LMP is null. ");
        } else {
            try{
                df.parse(LMP);
            }catch(DateTimeParseException ex) {
                sb.append("LMP should be of format MM/dd/yyyy. ");
            }
        }

        try {
            Long.valueOf(locationID);
        }catch(NumberFormatException ex) {
            sb.append("Location Id invalid. ");
        }

        try {
            Long.valueOf(apptTypeID);
        }catch(NumberFormatException ex) {
            sb.append("Application Type Id invalid. ");
        }

        if(sendBill == null || !(sendBill.equals("Yes") || sendBill.equals("No"))) {
            sb.append("Must choose send bill option. ");
        }

        if(lowLyingPlacenta == null || !(lowLyingPlacenta.equals("Yes") || lowLyingPlacenta.equals("No"))){
            sb.append("Must choose low lying placenta option. ");
        }

        try {
            Double.valueOf(weightInPounds);
        }catch(NumberFormatException ex) {
            sb.append("Weight in Pounds invalid. ");
        }

        try {
            Integer.parseInt(numberOfBaby);
        }catch(NumberFormatException ex) {
            sb.append("Number of Baby invalid. ");
        }

        return sb.toString();
    }

    public static String validateUltrasoundInputs(String crl, String bpd, String hc, String fl, String ofd, String ac, String hl, String efw) {
        String[] inputs = new String[] {crl, bpd, hc, fl, ofd, ac, hl, efw};
        boolean valid = true;
        for (String input: inputs) {
            if(input == null || inputs.equals("")){
                valid = false;
                break;
            }
            try {
                Double.valueOf(input);
            }catch (NumberFormatException ex) {
                valid = false;
                break;
            }
        }

        if(!valid) {
            return "All inputs are mandatory and must be a number";
        }
        return "";
    }

    @Override
    public void validate(ObstetricsOfficeVisit ov) throws FormValidationException {
        ObstetricsOfficeVisitController ovc = new ObstetricsOfficeVisitController(ds);
        ErrorList errorList = new ErrorList();

        LocalDateTime date = ov.getCreatedDate();
        Long patientMID = ov.getPatientID();

        if (patientMID == null) {
            errorList.addIfNotNull("Cannot add office visit information: invalid patient MID");
            throw new FormValidationException(errorList);
        }

        LocalDate patientDOB = ovc.getPatientDOB(patientMID);
        if (patientDOB == null) {
            errorList.addIfNotNull("Cannot add office visit information: patient does not have a birthday");
            throw new FormValidationException(errorList);
        }

        if (date.toLocalDate().isBefore(patientDOB)) {
            errorList.addIfNotNull("Date: date cannot be earlier than patient's birthday at " + patientDOB.format(DateTimeFormatter.ISO_DATE));
        }

        errorList.addIfNotNull(checkFormat("Patient MID", patientMID, ValidationFormat.NPMID, false));

        errorList.addIfNotNull(checkFormat("Location ID", ov.getLocationID(), ValidationFormat.HOSPITAL_ID, false));

        Long apptTypeID = ov.getApptTypeID();
        ApptTypeData atData = new ApptTypeMySQLConverter(ds);
        String apptTypeName = "";
        try {
            apptTypeName = atData.getApptTypeName(apptTypeID);
        } catch (DBException e) {
            // Do nothing
        }
        if (apptTypeName.isEmpty()) {
            errorList.addIfNotNull("Appointment Type: Invalid ApptType ID");
        }

        HospitalData hData = new HospitalMySQLConverter(ds);
        Hospital temp = null;
        try {
            temp = hData.getHospitalByID(ov.getLocationID());
        } catch (DBException e) {
            // Do nothing
        }
        if (temp == null) {
            errorList.addIfNotNull("Location: Invalid Hospital ID");
        }


        errorList.addIfNotNull(checkFormat("Blood Pressure", ov.getBloodPressure(), ValidationFormat.BLOOD_PRESSURE_OV, true));

        if (ov.getWeightInPounds() <= 0) {
            errorList.addIfNotNull("WeightInPounds: Invaild Input");
        }
        if (ov.getFHR() <= 0) {
            errorList.addIfNotNull("FHR: Invalid Input");
        }
        if (ov.getNumberOfBaby() <= 0) {
            errorList.addIfNotNull("Number Of Baby: Invalid Input");
        }

        if (errorList.hasErrors()) {
            throw new FormValidationException(errorList);
        }
    }
}
