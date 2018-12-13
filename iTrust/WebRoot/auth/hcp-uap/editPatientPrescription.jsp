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
<%@page import="edu.ncsu.csc.itrust.action.EditPatientPrescriptionAction"%>


<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Edit Patient Prescription";
%>

<%@include file="/header.jsp"%>
<%
    /* Require a Patient ID first */
    String pidString = (String) session.getAttribute("pid");
    long recordID = Long.valueOf(request.getParameter("recordID"));
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
    PatientPrescriptionDAO patientPrescriptionDAO = prodDAO.getPatientPrescriptionDAO();

%>

<div>Edit Patient Prescription</div>
<form id="editForm" action="editPatientPrescription.jsp" method="post"><input type="hidden"
                                                                   name="formIsFilled" value="true"> <br />
    <%
    long ID = recordID;
    long patientID = Long.valueOf(pidString);
    String createdDate;
    String name;
    String dosage;
    String notes;
    if (request.getParameter("submitButton") != null || request.getParameter("deleteButton") != null) {
        boolean toDelete = request.getParameter("deleteButton") != null;
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

        } else{

            PatientPrescriptionBean prescription = new PatientPrescriptionBean();
            prescription.setID(ID);
            prescription.setPatientID(patientID);
            prescription.setCreatedDate(LocalDate.parse(createdDate, df));
            prescription.setName(name);
            prescription.setDosage(Double.valueOf(dosage));
            prescription.setNotes(notes);
            EditPatientPrescriptionAction action = new EditPatientPrescriptionAction(prodDAO, loggedInMID, pidString);
            boolean success = true;
            String operation = "";
            if (toDelete) {
                operation = "deleted";
                try {
                    action.deletePatientPrescription(ID);
                    try {
                        prescriptionRecordLogger.logDeletePrescriptionRecord(loggedInMID, Long.parseLong(pidString));
                    } catch (DBException e){
                        e.printStackTrace();
                    }
                } catch (DBException ex) {
                    success = false;
                }
            } else {
                operation = "modified";
                try {
                    action.editPatientPrescription(prescription);
                    try {
                        prescriptionRecordLogger.logEditPrescriptionRecord(loggedInMID, Long.parseLong(pidString));
                    } catch (DBException e){
                        e.printStackTrace();
                    }
                } catch (DBException ex) {
                    success = false;
                }
            }
            if(success) {
                %>
                    <div class="iTrustMessage">The prescription has been <%=operation%></div>
                <%
            }else {
                %>
                    <div class="iTrustMessage">Error happens. The prescription was not <%=operation%></div>
                <%
            }
        }
    } else {
        PatientPrescriptionBean prescription = patientPrescriptionDAO.getPatientPrescription(ID);
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
                        <td><input name="created-date" id="created-date" type="text" readonly value="<%=prescription.getCreatedDate().format(df) %>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Name <span class="required">*</span></td>
                        <td><input name="name" type="text" value="<%=prescription.getName()%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Dosage<span class="required">*</span></td>
                        <td><input name="dosage" type="text" value="<%=prescription.getDosage()%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Notes</td>
                        <td><input name="notes" type="text" value="<%=prescription.getNotes()%>"></td>
                    </tr>
                </table>
        </tr>
    </table>
    <div align="center">
        <input type="submit" value="Submit" name="submitButton"/>
        <input type="submit" value="Delete" name="deleteButton"/>
        <input type="hidden" value="<%=pidString%>" name="pid"/>
        <input type="hidden" value="<%=recordID%>" name="recordID"/>
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
