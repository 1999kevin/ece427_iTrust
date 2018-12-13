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
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.PatientPrescriptionMySQL"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientPrescriptionDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientPrescriptionBean"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View My Prescriptions";
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    PatientPrescriptionDAO patientPrescriptionDAO = prodDAO.getPatientPrescriptionDAO();
%>

<%@include file="/header.jsp" %>
<table class="fTable" align="center">
    <tr>
        <th colspan="5">My Prescriptions</th>
    </tr>
    <tr class="subHeader">
        <td>Prescription ID</td>
        <td>Date of the Prescription</td>
        <td>Name</td>
        <td>Dosage</td>
        <td>Notes</td>
    </tr>

    <%
        List<PatientPrescriptionBean> prescriptions = patientPrescriptionDAO.getPatientPrescriptionByPatientId(loggedInMID);
        if(prescriptions != null && prescriptions.size() > 0){
            for(PatientPrescriptionBean prescription: prescriptions) {
    %>
    <tr>
        <td><%=prescription.getID() %></td>
        <td><%=prescription.getCreatedDate().format(df) %></td>
        <td><%=prescription.getName() %></td>
        <td><%=prescription.getDosage()%></td>
        <td><%=prescription.getNotes()%></td>
    </tr>
    <%
            }
            // TODO: Add logger
        }
    %>
</table>
<%@include file="/footer.jsp" %>


