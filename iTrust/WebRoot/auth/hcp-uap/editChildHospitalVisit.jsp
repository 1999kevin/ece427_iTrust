<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyApptsAction"%>
<%@page import="edu.ncsu.csc.itrust.action.CheckChildHospitalVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.SearchChildHospitalVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ChildHospitalVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ChildHospitalVisitDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.DeliveryDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ApptBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ApptTypeDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.AddPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.SearchDeliveryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddDeliveryAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.DeliveryBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.childVisitValidotor"%>

<%@include file="/global.jsp" %>
<%
    pageTitle = "iTrust - Child Hospital Visit Record";
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("pid");
    String apptidString =request.getParameter("apptid");
    
    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        request.setAttribute("errorMessage", "pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewChildHospitalVisit.jsp&isCHV=true");
        return;
    }
    %>
    <%@include file="/header.jsp" %>
    <%
    long patientID = Long.parseLong(pidString);
	int apptID = Integer.parseInt(apptidString);
    ChildHospitalVisitDAO chvDAO = prodDAO.getChildHospitalVisitDAO();
    DeliveryDAO delivDAO = prodDAO.getDeliveryDAO();
    
    SearchDeliveryAction searchDelivery = new SearchDeliveryAction(prodDAO);
	List<ChildHospitalVisitBean> visits = new ArrayList<>();
    
    if (request.getParameter("deleteButton") != null) {
    	chvDAO.delete(patientID, apptID);
    	List<DeliveryBean> deliveries = searchDelivery.searchByMotherID(patientID);
    	for (DeliveryBean d: deliveries) {
    		delivDAO.deleteDelivery(d.getPatientID());
    	}
    }
    
    CheckChildHospitalVisitAction actionCheck = new CheckChildHospitalVisitAction(prodDAO);
    boolean hasVisit = actionCheck.checkForChildHospitalVisit(apptID);
    if (!hasVisit) {
    	String addString = (String) request.getParameter("add");
    	if (addString != null && addString.equals("true")){
    		ChildHospitalVisitBean p = new ChildHospitalVisitBean();
    		p.setPatientID(patientID);
    		p.setApptID(apptID);
    		p.setPreferredDeliveryType("vaginal delivery");
    		p.setPitocin(0);
    		p.setNitrousOxide(0);
    		p.setPethidine(0);
    		p.setEpiduralAnaesthesia(0);
    		p.setMagnesiumSulfate(0);
    		p.setRHImmuneGlobulin(0);
    		p.setActualDeliveryType("vaginal delivery");
    		try {
    			chvDAO.addChildHospitalVisit(p);
    		} catch (Exception e) {
    			e.printStackTrace();
            }
    		chvLogger.logCreateChildbirthVisit(loggedInMID.longValue(), patientID);
    		%>
			<div class="iTrustMessage" align="center">The visit record has been created a new</div>
			<%
    	} else {
    		%>
			<div class="iTrustMessage" align="center">There is no visit record for this Child Hospital Visit appointment</div>
			<%
    	}
    	
	} else {
    
    
    PersonnelDAO ppDAO = prodDAO.getPersonnelDAO();
	PersonnelBean ppBean = ppDAO.getPersonnel(loggedInMID);
	boolean isOB = false;
	if (ppBean.getSpecialty().equals("OB/GYN") == true) {
		isOB = true;
	}
    
    session.setAttribute("pid", pidString);
    session.setAttribute("apptid", apptidString);

    if (pidString != null && apptidString != null) {
%>
<br/><br/>
<input type="hidden" name="add" id="add" />
<table class="fTable" align="center" style="width: 670px;">
    <tr>
        <th colspan="10">Previous Obstetrics Care Records</th>
    </tr>
    <tr class="subHeader">
        <td>Record ID</td>
        <td>Record Date</td>
        <td>LMP</td>
        <td>Year of Conception</td>
        <td>Delivery Type</td>
        <td>Date of Birth</td>
    </tr>
        <%
            List<ObstetricsPatientBean> records = new ArrayList<>();
            try {
                SearchUsersAction searchAction = new SearchUsersAction(prodDAO, loggedInMID.longValue());
                records = searchAction.fuzzySearchForObRecWithMID(Long.parseLong(pidString));
                session.setAttribute("obrecords", records);
                for (ObstetricsPatientBean record:records) {
                    if (record == null) {
                        continue;
                    }
                    %>
    <tr>
        <td><%= record.getID() %></td>
        <td><%= record.getCreatedDate() %></td>
        <td><%= record.getLMP() %> </td>
        <td><%= record.getYearOfConception() %></td>
        <td><%= record.getDeliveryType() %></td>
        <td><%= record.getNumberofBaby() %></td>
    </tr>
        <%
                }
            } catch (Exception e) {

            }
            finally {

        	}
%>
</table>
<br/>
<%
	
	Date today = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy");
	String recordDate = ft.format(today);
	
	String preferredDeliveryType = "";
	String pitocinDosage = "";
	String nitrousOxideDosage = "";
	String pethidineDosage = "";
	String epiduralDosage = "";
	String magnesiumDosage = "";
	String rhimmuneDosage = "";
	String deliveryDate = "";
	String deliveryTime = "";
	String actualDeliveryType = "";
	String isDelivered = "false";
	String babyNumString = "";
	
	
	boolean hasDelivery = false;
	List<DeliveryBean> dBean = searchDelivery.searchByMotherID(patientID);
	System.out.print("Size: " + dBean.size() + "\n");
	if (dBean != null && dBean.size() >= 1) {
		System.out.print("View the delivery\n");
		hasDelivery = true;
		isDelivered = "true";
		deliveryDate = dBean.get(0).getDeliverDate();
		deliveryTime = dBean.get(0).getDeliverTime();
	}
	
	
	try {
		SearchChildHospitalVisitAction searchAction = new SearchChildHospitalVisitAction(prodDAO);
        visits = searchAction.fuzzySearchForChildHospitalVisit(patientID, apptID);
        System.out.print("Enter -1\n");
        if (visits != null) {
            ChildHospitalVisitBean visit = visits.get(0);
            preferredDeliveryType = visit.getPreferredDeliveryType();
            actualDeliveryType = visit.getActualDeliveryType();
            pitocinDosage = "" + visit.getPitocin();
            nitrousOxideDosage = "" + visit.getNitrousOxide();
            pethidineDosage = "" + visit.getPethidine();
            epiduralDosage = "" + visit.getEpiduralAnaesthesia();
            magnesiumDosage = "" + visit.getMagnesiumSulfate();
            rhimmuneDosage = "" + visit.getRHImmuneGlobulin();
         } else {
            	%>
				<div class="iTrustMessage" align="center">The visit cannot be found</div>
				<%
        }
	} catch (Exception e) {
		e.printStackTrace();
    } 
	
	if (request.getParameter("submitButton") != null) {
		preferredDeliveryType = request.getParameter("delivery-type");
		pitocinDosage = request.getParameter("pitocin");
		nitrousOxideDosage = request.getParameter("nitrous-oxide");
		pethidineDosage = request.getParameter("pethidine");
		epiduralDosage = request.getParameter("epidural");
		magnesiumDosage = request.getParameter("magnesium");
		rhimmuneDosage = request.getParameter("rh-immune");
		actualDeliveryType = request.getParameter("actual-delivery-type");
		if (request.getParameter("is-delivered") != null) {
			isDelivered = request.getParameter("is-delivered");
		}
		System.out.print("Enter 1\n");
		System.out.print("Point1: " + patientID + " " + apptID+ " " + preferredDeliveryType+ " " + actualDeliveryType+ " " + Integer.parseInt(pitocinDosage)+ " " + Integer.parseInt(nitrousOxideDosage)+ " " + Integer.parseInt(epiduralDosage) + " " + Integer.parseInt(magnesiumDosage) + " " + Integer.parseInt(rhimmuneDosage) + " " + isDelivered + "\n");
		
		if (isDelivered.equals("true") && hasDelivery == true) {
			deliveryDate = request.getParameter("delivery-date");
			deliveryTime = request.getParameter("delivery-time");
			babyNumString = request.getParameter("babynum");
			String[] babyFirstNames = request.getParameterValues("baby-firstname");
			String[] babyLastNames = request.getParameterValues("baby-lastname");
			String[] babySexs = request.getParameterValues("baby-sex");
			int babyNum = Integer.parseInt(babyNumString);
			int count = 0;
			if (dBean.size() > babyNum) {
				for (int i = 0;i < babyNum; i++) {
					DeliveryBean d = dBean.get(i);
					EditPatientAction editBaby = new EditPatientAction(prodDAO, loggedInMID, "" + d.getPatientID());
					PatientBean editB = editBaby.getPatient();
					d.setDeliverDate(deliveryDate);
					d.setDeliverTime(deliveryTime);
					d.setSex(babySexs[count]);
					editB.setFirstName(babyFirstNames[count]);
					editB.setLastName(babyLastNames[count]);
					editB.setGenderStr(babySexs[count]);
					
					delivDAO.editDelivery(d);
					editBaby.updateInformation(editB);
				}
				count = babyNum;
				for (int i = count;i < dBean.size(); i++) {
					DeliveryBean d = dBean.get(i);
					delivDAO.deleteDelivery(d.getPatientID());
				}
			} else {
				for (DeliveryBean d:dBean) {
					EditPatientAction editBaby = new EditPatientAction(prodDAO, loggedInMID, "" + d.getPatientID());
					PatientBean editB = editBaby.getPatient();
					d.setDeliverDate(deliveryDate);
					d.setDeliverTime(deliveryTime);
					d.setSex(babySexs[count]);
					editB.setFirstName(babyFirstNames[count]);
					editB.setLastName(babyLastNames[count]);
					editB.setGenderStr(babySexs[count]);
					
					delivDAO.editDelivery(d);
					editBaby.updateInformation(editB);
					chvLogger.logBabyBorn(loggedInMID, patientID);
					count++;
				}
				if (count < babyNum) {
					for (int i = count;i < babyNum; i++){
						PatientBean babyBean = new PatientBean();
						DeliveryBean deliveryBean = new DeliveryBean();
						babyBean.setFirstName(babyFirstNames[i]);
						babyBean.setLastName(babyLastNames[i]);
						babyBean.setGenderStr(babySexs[i]);
						babyBean.setMotherMID(pidString);
						babyBean.setEmail(babyLastNames[i] + babyLastNames[i] + "@gmail.com");
							try {
								AddPatientAction addBaby = new AddPatientAction(prodDAO, loggedInMID);
								AddDeliveryAction addDelivery = new AddDeliveryAction(prodDAO, loggedInMID);
								long newID = addBaby.addPatient(babyBean, loggedInMID);
								deliveryBean.setDeliverDate(deliveryDate);
								deliveryBean.setDeliverTime(deliveryTime);
								deliveryBean.setChildHospitalVisitID(visits.get(0).getID());
								deliveryBean.setMotherID(patientID);
								deliveryBean.setPatientID(newID);
								deliveryBean.setSex(babySexs[i]);
								deliveryBean.setMotherID(patientID);
								addDelivery.addDelivery(deliveryBean);
							} catch (Exception e){
								e.printStackTrace();
							}
					}
				}
			}
			dBean = searchDelivery.searchByMotherID(patientID);
		}
		
		if (isDelivered.equals("true") && hasDelivery == false) {
			System.out.print("Enter Delivery\n");
			deliveryDate = request.getParameter("delivery-date");
			deliveryTime = request.getParameter("delivery-time");
			babyNumString = request.getParameter("babynum");
			String[] babyFirstNames = request.getParameterValues("baby-firstname");
			String[] babyLastNames = request.getParameterValues("baby-lastname");
			String[] babySexs = request.getParameterValues("baby-sex");
			int babyNum = Integer.parseInt(babyNumString);
			for (int i=0; i < babyNum; i++) {
				PatientBean babyBean = new PatientBean();
				DeliveryBean deliveryBean = new DeliveryBean();
				babyBean.setFirstName(babyFirstNames[i]);
				babyBean.setLastName(babyLastNames[i]);
				babyBean.setGenderStr(babySexs[i]);
				babyBean.setMotherMID(pidString);
				babyBean.setEmail(babyLastNames[i] + babyLastNames[i] + "@gmail.com");
					try {
						AddPatientAction addBaby = new AddPatientAction(prodDAO, loggedInMID);
						AddDeliveryAction addDelivery = new AddDeliveryAction(prodDAO, loggedInMID);
						long newID = addBaby.addPatient(babyBean, loggedInMID);
						chvLogger.logCreateBabyRecord(loggedInMID, patientID, "" + newID);
						deliveryBean.setDeliverDate(deliveryDate);
						deliveryBean.setDeliverTime(deliveryTime);
						deliveryBean.setChildHospitalVisitID(visits.get(0).getID());
						deliveryBean.setMotherID(patientID);
						deliveryBean.setPatientID(newID);
						deliveryBean.setSex(babySexs[i]);
						deliveryBean.setMotherID(patientID);
						addDelivery.addDelivery(deliveryBean);
					} catch (Exception e){
						e.printStackTrace();
					}
			}
			System.out.print("Enter 2\n");
			dBean = searchDelivery.searchByMotherID(patientID);
			hasDelivery = true;
		}
		
		StringBuilder sb = new StringBuilder();
		if (!childVisitValidotor.isIntValid(pitocinDosage)||!childVisitValidotor.isIntValid(rhimmuneDosage)||!childVisitValidotor.isIntValid(magnesiumDosage)||!childVisitValidotor.isIntValid(nitrousOxideDosage)||!childVisitValidotor.isIntValid(pethidineDosage)){
		    sb.append("Dosage must be an integer");
		}

		String erroeMsg = sb.toString();
		if(!erroeMsg.isEmpty()){
			// display error message
			%>
			<div class="iTrustError" align="center"><%=erroeMsg%></div>
			<%
		}
		
		System.out.print("Enter 3\n");
		try {
			ChildHospitalVisitBean edit = new ChildHospitalVisitBean();
			edit.setPatientID(patientID);
			edit.setApptID(apptID);
			edit.setPreferredDeliveryType(preferredDeliveryType);
			edit.setPitocin(Integer.parseInt(pitocinDosage));
			edit.setNitrousOxide(Integer.parseInt(nitrousOxideDosage));
			edit.setPethidine(Integer.parseInt(pethidineDosage));
			edit.setEpiduralAnaesthesia(Integer.parseInt(epiduralDosage));
			edit.setMagnesiumSulfate(Integer.parseInt(magnesiumDosage));
			edit.setRHImmuneGlobulin(Integer.parseInt(rhimmuneDosage));
			edit.setActualDeliveryType(actualDeliveryType);
			System.out.print("Point2: " + patientID+ " " + apptID+ " " + preferredDeliveryType+ " " + actualDeliveryType+ " " + Integer.parseInt(pitocinDosage)+ " " + Integer.parseInt(nitrousOxideDosage)+ " " + Integer.parseInt(epiduralDosage) + " " + Integer.parseInt(magnesiumDosage) + " " + Integer.parseInt(rhimmuneDosage) + "\n");
			boolean returned = chvDAO.editChildHospitalVisit(edit);
		
			if (returned == true) {
				chvLogger.logAddChildbirthDrugs(loggedInMID, patientID);
				chvLogger.logEditChildbirthVisit(loggedInMID, patientID);
				%>
				<div class="iTrustMessage" align="center">The visit has been updated</div>
				<%
			}
		} catch (Exception e){
			e.printStackTrace();
		} 
		
	}
%>
<form id="editForm" action="editChildHospitalVisit.jsp" method="post">
<table cellspacing=0 align="center" cellpadding=0>
<tr>
<td valign=top>
<table class="fTable" align="center" style="width: 650px;">
			<tr>
				<th colspan="10">Child Hospital Visit Detail</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Record Date <span class="required">*</span></td>
				<td><input name="record-date" id="record-date" type="text" disabled="disabled" readonly value="<%=recordDate %>"></td>
				<td class="subHeaderVertical">Preferred Delivery Method<span class="required">*</span></td>
				<td><select name="delivery-type">
					<option value="choose one below" <% if (preferredDeliveryType.equals("")) {%>selected="selected"<%} %>># choose one below</option>
					<option value="vaginal delivery" <% if (preferredDeliveryType.equals("vaginal delivery")) {%>selected="selected"<%} %>>vaginal delivery</option>
					<option value="vaginal delivery vacuum assist" <% if (preferredDeliveryType.equals("vaginal delivery vacuum assist")) {%>selected="selected"<%} %>>vaginal delivery vacuum assist</option>
					<option value="vaginal delivery forceps assist" <% if (preferredDeliveryType.equals("vaginal delivery forceps assist")) {%>selected="selected"<%} %>>vaginal delivery forceps assist</option>
					<option value="caesarean section" <% if (preferredDeliveryType.equals("caesarean section")) {%>selected="selected"<%} %>>caesarean section</option>
					<option value="miscarriage" <% if (preferredDeliveryType.equals("miscarriage")) {%>selected="selected"<%} %>>miscarriage</option>
				</select></td>
			</tr>
			<tr>
				<th colspan="10">Drugs Administered and Dosages</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Pitocin</td>
				<td><input name="pitocin" type="text" value=<%=pitocinDosage %>></td>
				<td class="subHeaderVertical">Nitrous oxide</td>
				<td><input name="nitrous-oxide" type="text" value=<%=nitrousOxideDosage %>></td>		
			</tr>
			<tr>
				<td class="subHeaderVertical">Pethidine</td>
				<td><input name="pethidine" type="text" value=<%=pethidineDosage %>></td>
				<td class="subHeaderVertical">Epidural anaesthesia</td>
				<td><input name="epidural" type="text" value=<%=epiduralDosage %>></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Magnesium sulfate</td>
				<td><input name="magnesium" type="text" value=<%=magnesiumDosage %>></td>
				<td class="subHeaderVertical">RH immune globulin</td>
				<td><input name="rh-immune" type="text" value=<%=rhimmuneDosage %>></td>
			</tr>
			<tr>
				<th colspan="10">Delivery Details</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Delivery Date</td>
				<td><input type=text name="delivery-date" id="delivery-date" <% if (!hasDelivery) { %> readonly <%} %> maxlength="10"size="10" value=<%=deliveryDate %>> 
				<span><input type=button name="delivery-date-select" id="delivery-date-select" value="Select" <% if (!hasDelivery) { %> readonly <%} %> onclick="displayDatePicker('delivery-date');"></span></td>
				<td class="subHeaderVertical">Delivery Time</td>
				<td><input name="delivery-time" <% if (!hasDelivery) { %> readonly <%} %> id="delivery-time" type="text" value=<%=deliveryTime %>></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Actual Delivery Method <span class="required">*</span></td>
				<td><select name="actual-delivery-type" id="actual-delivery-type" readonly="<%=isDelivered %>">
					<option value="choose one below"  <% if (actualDeliveryType.equals("")) {%>selected="selected"<%} %>># choose one below</option>
					<option value="vaginal delivery"  <% if (actualDeliveryType.equals("vaginal delivery")) {%>selected="selected"<%} %>>vaginal delivery</option>
					<option value="vaginal delivery vacuum assist" <% if (actualDeliveryType.equals("vaginal delivery vacuum assist")) {%>selected="selected"<%} %>>vaginal delivery vacuum assist</option>
					<option value="vaginal delivery forceps assist" <% if (actualDeliveryType.equals("vaginal delivery forceps assist")) {%>selected="selected"<%} %>>vaginal delivery forceps assist</option>
					<option value="caesarean section" <% if (actualDeliveryType.equals("caesarean section")) {%>selected="selected"<%} %>>caesarean section</option>
					<option value="miscarriage" <% if (actualDeliveryType.equals("miscarriage")) {%>selected="selected"<%} %>>miscarriage</option>
				</select></td>
				<td class="subHeaderVertical">Is Delivered</td>
				<td><input type=checkbox id="isdelivered" name="is-delivered" value="<%=hasDelivery %>" <% if (hasDelivery) { %> checked <%} %> onchange="checkedIsDelivered(this)"></td>
			</tr>
</table>
<table class="fTable" align="center" style="width: 670px;">
			<thead>
			<tr>
				<th colspan="4">Babies Information</th>
			</tr>
			</thead>		
			<tbody id="babyTable">
			<tr>
				<td colspan="3">Babies Number</td>
				<%
				if (dBean.size() > 0) {
					%>
						<td colspan="1"><input type="number" name="babynum" id="babynum" min="0" max="5" value="<%=dBean.size()%>"></td>
					<%
					for (DeliveryBean d:dBean) {
						ViewPatientAction viewBaby = new ViewPatientAction(prodDAO, loggedInMID, "" + d.getPatientID());
						PatientBean babyPatient = viewBaby.getPatient("" + d.getPatientID());
						%>
				<tr><td colspan="3">Baby Sex</td><td colspan="1"><input name="baby-sex" type="text" value="<%=d.getSex() %>"></td></tr><tr><td class="subHeaderVertical">Baby First Name</td><td><input name="baby-firstname" type="text" value="<%=babyPatient.getFirstName() %>"></td><td class="subHeaderVertical">Baby Last Name</td><td><input name="baby-lastname" type="text" value="<%=babyPatient.getLastName() %>"></td></tr>
						<% 
					}
				} else {
					%>
					<td colspan="1"><input type="number" name="babynum" id="babynum" min="0" max="5" value="0"></td>
					<%
				}
				%>
			</tr>
			</tbody>	
</table>
</tr>
</table>

<div align="center">
	<input type="hidden" name="formIsFilled" value="true"/>
<input type="hidden" value="<%=pidString%>" name="pid"/>
<input type="hidden" value="<%=apptidString%>" name="apptid"/>
<input type="submit" value="Update" name="submitButton" <% if (isOB == false) { %>disabled<% } %>/>
<input type="submit" value="Delete" name="deleteButton"/>
</div>
</form>
<br/>


<script type="text/javascript">
<% if (dBean == null) {
	%>
	var countBabies = 0;
	<%
} else {
%>
	var countBabies = <%=dBean.size() %>;
<%
}
%>

function checkedIsDelivered(checkboxElem) {
	if (checkboxElem.checked) {
		checkboxElem.value = "true";
        $('#delivery-date').attr("readonly", false);
        $('#delivery-time').attr("readonly", false);
        $('#delivery-date-select').attr("readonly", false);
        $('#actual-delivery-type').attr("disabled", false);
	} else {
		checkboxElem.value = "false";
		$('#delivery-date').attr("readonly", true);
       	$('#delivery-time').attr("readonly", true);
       	$('#delivery-date-select').attr("readonly", true);
       	$('#actual-delivery-type').attr("disabled", true);
	}	
}

$('#babynum').bind('click keydown', function(){
	  if (countBabies < $(this).val()) {
		  table = $('#babyTable');
		  table.append('<tr><td colspan="3">Baby Sex</td><td colspan="1"><input name="baby-sex" type="text"></td></tr><tr><td class="subHeaderVertical">Baby First Name</td><td><input name="baby-firstname" type="text"></td><td class="subHeaderVertical">Baby Last Name</td><td><input name="baby-lastname" type="text"></td></tr>');
	  } else if (countBabies > $(this).val())  {
		  table = $('#babyTable');
		  var last = table.find('tr').last();
		  last.remove();
		  last = table.find('tr').last();
		  last.remove();
	  }
	  countBabies = $(this).val()
});
</script>
<%
    }
%>
<%
	}
%>
<br/>
<div align="center">
    <a href="/iTrust/auth/hcp-uap/viewChildHospitalVisit.jsp?pid=<%=pidString%>">Go back to Overview Child Hospital Visits</a>
</div>
<br/>
<%@include file="/footer.jsp" %>
