<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricsPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.InputValidator"%>
<%@page import="edu.ncsu.csc.itrust.ObstetricsValidator"%>
<%@page import="edu.ncsu.csc.itrust.model.old.enums.Ethnicity"%>
<%@page import="edu.ncsu.csc.itrust.model.old.enums.BloodType"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.model.old.enums.Gender"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>


<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add new Obstetrics Record";
%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String pidString = (String) request.getParameter("pid");
	PersonnelDAO ppDAO = prodDAO.getPersonnelDAO();
	PersonnelBean ppBean = ppDAO.getPersonnel(loggedInMID);
	
	if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
		request.setAttribute("errorMessage", "pidstring is null");
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp");
		return;
	}
	
	if (ppBean.getSpecialty().equals("OB/GYN") != true) {
		session.setAttribute("errorMessage", "Current HCP has no privilege to add Obstetrics Records");
		session.setAttribute("pid", pidString);
		response.sendRedirect("/iTrust/auth/hcp-uap/viewObstetricsRecords.jsp?pid=" + pidString);
		return;
	}

%>

<div>Add New Obstetrics Record</div>
<form id="editForm" action="addObstetricsRecord.jsp" method="post"><input type="hidden"
	name="formIsFilled" value="true"> <br />
<%
	int patientID = Integer.parseInt(pidString);
	String yearOfConception = "";
	String weightGained = "";
	String deliveryType = "";
	String numberOfBirth = "";
	String lmp = "";
	String hoursInLabor = "";
	Date today = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy");
	String recordDate = ft.format(today);

	if (request.getParameter("submitButton") != null) {
		patientID = Integer.parseInt(pidString);
		yearOfConception = request.getParameter("conception-year");
		weightGained = request.getParameter("weight-gained");
		deliveryType = request.getParameter("delivery-type");
		numberOfBirth = request.getParameter("number-of-birth");
		lmp = request.getParameter("LMP");
		hoursInLabor = request.getParameter("hours-in-labor");
		StringBuilder sb = new StringBuilder();

		// validate inputs
		if(!ObstetricsValidator.isValidYear(yearOfConception)) {
			sb.append("Conception Year must be a non-empty integer. ");
		}
		if(!ObstetricsValidator.isValidLMP(recordDate, lmp)) {
			sb.append("LMP must be within 280 days before record date. ");
		}
		if(!ObstetricsValidator.isValidDeliveryType(deliveryType)){
			sb.append("Must choose a delivery type. ");
		}
		if(!InputValidator.isInteger(numberOfBirth)){
			sb.append("Number of Birth should be an integer. ");
		}
		if(!InputValidator.isDouble(hoursInLabor)){
			sb.append("Hours in Labor should be a number. ");
		}
		if(!InputValidator.isDouble(weightGained)){
			sb.append("Weight Gained should be a number. ");
		}
		String erroeMsg = sb.toString();
		if(!erroeMsg.isEmpty()){
		    // display error message
			%>
		<div class="iTrustError"><%=erroeMsg%></div>
		<%

		}else{
			ObstetricsPatientBean p = new ObstetricsPatientBean();
			p.setPatientID(patientID);
			p.setCreatedDate(String.valueOf(recordDate));
			p.setYearOfConception(Integer.valueOf(yearOfConception));
			p.setLMP(String.valueOf(lmp));

			long daysOfPreg = ObstetricsValidator.daysOfPreg(recordDate, lmp);
			long weekNum = daysOfPreg/7;
			long dayNum = daysOfPreg%7;
			String setWeek = Long.toString(weekNum) + "-" + Long.toString(dayNum);
			p.setNumberOfWeeksPreg(setWeek);

			p.setWeightGain(Double.valueOf(weightGained));
			p.setDeliveryType(deliveryType);
			p.setNumberofBaby(Integer.valueOf(numberOfBirth));
			p.setNumberOfLaborHour(Double.valueOf(hoursInLabor));

			AddObstetricsPatientAction action = new AddObstetricsPatientAction(prodDAO);
			action.addObstetricsPatient(p);
			
		    String edd = LocalDate.parse(lmp, DateTimeFormatter.ofPattern("MM/dd/yyyy")).plusDays(280).toString();
			try {
				oblogger.logCreateObstetricsRecord(loggedInMID, Long.parseLong(pidString), edd);
			} catch (DBException e){
				e.printStackTrace();
			}

			%>
			<div class="iTrustMessage">The record has been added</div>
			<%
		}
	}
%>

<table cellspacing=0 align=center cellpadding=0>
	<tr>
		<td valign=top>
		<table class="fTable" align=center style="width: 350px;">
			<tr>
				<th colspan=2>Obstetrics Record</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Record Date <span class="required">*</span></td>
				<td><input name="record-date" id="record-date" type="text" disabled="disabled" readonly value="<%=recordDate %>"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Conception Year <span class="required">*</span></td>
				<td><input name="conception-year" type="text"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Weight Gained During Pregnancy</td>
				<td><input name="weight-gained" type="text"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Hours in Labor</td>
				<td><input name="hours-in-labor" type="text"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Delivery Type <span class="required">*</span></td>
				<td><select name="delivery-type">

					<option value="choose one below"># choose one below</option>
					<option value="vaginal delivery">vaginal delivery</option>
					<option value="vaginal delivery vacuum assist">vaginal delivery vacuum assist</option>
					<option value="vaginal delivery forceps assist">vaginal delivery forceps assist</option>
					<option value="caesarean section">caesarean section</option>
					<option value="miscarriage">miscarriage</option>

				</select>
				</td>
			</tr>

			<tr>
				<td class="subHeaderVertical">Number of Birth</td>
				<td><input name="number-of-birth" type="text"></td>
			</tr>

			<tr>
				<td class="subHeaderVertical">LMP <span class="required">*</span></td>
				<td><input type=text name="LMP" maxlength="10"
						   size="10" value=""> <input
						type=button value="Select Date"
						onclick="displayDatePicker('LMP');"></td>
			</tr>
			<tr style="display:none" id="edd">
				<td class="subHeaderVertical">EDD</td>
				<td id="edd-date"></td>
			</tr>
			<tr style="display:none" id="preg-weeks">
				<td class="subHeaderVertical">Weeks & Days Pregnant</td>
				<td id="preg-weeks-data"></td>
			</tr>
		</table>
	</tr>
</table>
<div align="center">
	<input type="submit" value="Submit" name="submitButton"/>
	<input type="hidden" value="<%=pidString%>" name="pid"/>
</div>
</form>
<br />
<br />
<div align="center">
	<a href="/iTrust/auth/hcp-uap/viewObstetricsRecords.jsp?pid=<%=pidString%>">Go back to Obstetrics Records</a>
</div>
<script>
	function datePickerClosed(dateField) {
		var lmpDate = moment(dateField.value);
		var eddDate = moment(dateField.value).add(280, 'days');

		$("#edd").show();
        $("#edd-date").text(eddDate.format("MM/DD/YYYY"));

        var currDate = moment($("#record-date")[0].value);
        var diffDays = currDate.diff(lmpDate, 'days');
        var pregWeeks = parseInt(diffDays / 7);
        var pregDays = diffDays % 7;
        var displayString = "";
        if (pregWeeks < 0 || pregWeeks > 42) {
            displayString = "N/A";
		} else {
            displayString = pregWeeks > 9 ? "" + pregWeeks: "0" + pregWeeks;
            displayString += "-" + pregDays;
		}
        $("#preg-weeks").show();
        $("#preg-weeks-data").text(displayString);
    }
</script>

<%@include file="/footer.jsp"%>
