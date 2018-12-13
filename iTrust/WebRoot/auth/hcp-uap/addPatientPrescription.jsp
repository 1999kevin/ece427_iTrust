<%@page import="org.apache.commons.lang3.CharSequenceUtils"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="edu.ncsu.csc.itrust.Constants"%>;
<%@page import="edu.ncsu.csc.itrust.helper.DateHelper"%>;
<%@page import="edu.ncsu.csc.itrust.helper.ObsHelper"%>;
<%@page import="edu.ncsu.csc.itrust.ObstetricsValidator"%>;
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.PatientPrescription"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitValidator"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.action.AddUltrasoundRecordAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO"%>

<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientPrescriptionDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean"%>
<%@page import="edu.ncsu.csc.itrust.action.AddPatientPrescriptionAction"%>


<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Edit Patient Prescription";
%>

<%@include file="/header.jsp"%>
<%
    /* Require a Patient ID first */
    String pidString = (String) session.getAttribute("pid");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
    AddPatientPrescriptionAction action = new AddPatientPrescriptionAction(prodDAO);
%>

<div>Add Patient Prescription</div>
<form id="editForm" action="addPatientPrescription.jsp" method="post"><input type="hidden"
                                                                              name="formIsFilled" value="true"> <br />
    <%
        long patientID = Long.valueOf(pidString);
        String createdDate;
        String name;
        String dosage;
        String notes;

        if (request.getParameter("submitButton") != null) {
            createdDate = request.getParameter("created-date");
            notes = request.getParameter("notes");
            dosage = request.getParameter("dosage");
            name = request.getParameter("name");

            // validate inputs
            String erroeMsg = "";
            if(!erroeMsg.isEmpty()){
                // display error message
    %>
    <div class="iTrustError"><%=erroeMsg%></div>
    <%

    }else{
        try {
            prescriptionRecordLogger.logCreatePrescriptionRecord(loggedInMID, Long.parseLong(pidString));
        } catch (DBException e){
            e.printStackTrace();
        }
        PatientPrescriptionBean prescription = new PatientPrescriptionBean();
        prescription.setCreatedDate(LocalDate.parse(createdDate, df));
        prescription.setName(name);
        prescription.setPatientID(patientID);
        prescription.setDosage(Double.valueOf(dosage));
        prescription.setNotes(notes);
        try {
            long newId = action.addPatientPrescription(prescription);
    %>
    <div class="iTrustMessage">Added Successfully</div>
    <%
    }catch (DBException ex) {
    %>
    <div class="iTrustError">Adding prescription failed</div>
    <%
            }

        }
    } else {
    %>

    <table cellspacing=0 align=center cellpadding=0>
        <tr>
            <td valign=top>
                <table class="fTable" align=center style="width: 350px;">
                    <tr>
                        <th colspan=2>Prescription</th>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Date Prescribed <span class="required">*</span></td>
                        <td><input name="created-date" id="created-date" type="text" readonly value="<%=LocalDate.now().format(df)%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Name <span class="required">*</span></td>
                        <td><input name="name" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Dosage<span class="required">*</span></td>
                        <td><input name="dosage" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Notes</td>
                        <td><input name="notes" type="text"></td>
                    </tr>
                </table>
        </tr>
    </table>
    <div align="center">
        <input type="submit" value="Submit" name="submitButton"/>
        <input type="hidden" value="<%=pidString%>" name="pid"/>
    </div>
</form>

<%
    }
%>
<div align="center">
    <a href="/iTrust/auth/hcp-uap/viewPatientPrescriptions.jsp?pid=<%=pidString%>">Go back to Prescriptions</a>
</div>


<script>

</script>
<%@include file="/footer.jsp"%>
