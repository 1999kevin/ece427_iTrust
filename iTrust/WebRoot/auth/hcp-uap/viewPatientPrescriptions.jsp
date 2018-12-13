<%@page import="org.apache.commons.lang3.CharSequenceUtils"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="edu.ncsu.csc.itrust.Constants"%>;
<%@page import="edu.ncsu.csc.itrust.helper.DateHelper"%>;
<%@page import="edu.ncsu.csc.itrust.helper.ObsHelper"%>;
<%@page import="edu.ncsu.csc.itrust.ObstetricsValidator"%>;
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientPrescriptionDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Patient Prescriptions";
    /* Require a Patient ID first */
    String pidString = (String) session.getAttribute("pid");
    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        request.setAttribute("errorMessage", "pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewPatientPrescriptions.jsp");
        return;
    }
    session.setAttribute("pid", pidString);
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    PatientPrescriptionDAO patientPrescriptionDAO = prodDAO.getPatientPrescriptionDAO();
%>

<%@include file="/header.jsp" %>
<table class="fTable" align="center">
    <tr>
        <th colspan="7">Patient Prescriptions</th>
    </tr>
    <tr class="subHeader">
        <td>Prescription ID</td>
        <td>Date of the Prescription</td>
        <td>Name</td>
        <td>Dosage</td>
        <td>Notes</td>
        <td></td>
    </tr>

<%
    List<PatientPrescriptionBean> prescriptions = patientPrescriptionDAO.getPatientPrescriptionByPatientId(Long.valueOf(pidString));
    if(prescriptions != null && prescriptions.size() > 0){
        for(PatientPrescriptionBean prescription: prescriptions) {
%>
    <tr>
        <td><%=prescription.getID() %></td>
        <td><%=prescription.getCreatedDate().format(df) %></td>
        <td><%=prescription.getName() %></td>
        <td><%=prescription.getDosage()%></td>
        <td><%=prescription.getNotes()%></td>
        <td><a href="editPatientPrescription.jsp?recordID=<%=prescription.getID()%>">Edit</a></td>
    </tr>
<%
        }
        try {
            prescriptionRecordLogger.logViewPrescriptionRecord(loggedInMID, Long.parseLong(pidString));
        } catch (DBException e){
            e.printStackTrace();
        }
    }
%>
</table>
<%

%>
<div align="center">
    <a href="/iTrust/auth/hcp-uap/addPatientPrescription.jsp?pid=<%=pidString%>">Add New Prescription</a>
</div>
<%@include file="/footer.jsp" %>


