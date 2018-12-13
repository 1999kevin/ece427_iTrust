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
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Obstetrics Visits";
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("pid");
    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        request.setAttribute("errorMessage", "pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObsVisit.jsp&isObstetric=true");
        return;
    }
    session.setAttribute("pid", pidString);
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
%>

<%@include file="/header.jsp" %>
<table class="fTable" align="center">
    <tr>
        <th colspan="13">Obstetrics Visits</th>
    </tr>
    <tr class="subHeader">
        <td>Visit ID</td>
        <td>Date of the Office Visit</td>
        <td>LMP</td>
        <td>Number of Weeks Pregnant</td>
        <td>Weight in Pounds</td>
        <td>Blood Pressure</td>
        <td>Fetal Heart Rate</td>
        <td># of Pregnancy</td>
        <td>Send Bill</td>
        <td>Low Lying Placenta Observed?</td>
        <td>Notes</td>
        <td></td>
    </tr>

<%
//    long s = 1;
    List<ObstetricsOfficeVisit> visits = new ObstetricsOfficeVisitMySQL().getVisitsForPatient(Long.parseLong(pidString));
    if(visits != null && visits.size() > 0){
        String visitID = "";
        for(ObstetricsOfficeVisit visit: visits) {
            if(visitID == ""){visitID = Long.toString(visit.getID());}
            else {visitID = visitID + ", " + Long.toString(visit.getID());}
%>
    <tr>
        <td><%=visit.getID() %></td>
        <td><%=visit.getCreatedDate().format(df) %></td>
        <td><%=visit.getLMP().format(df) %></td>
        <td><%=ObstetricsValidator.getNumOfWeeksPreg(visit.getCreatedDate().format(df), visit.getLMP().format(df)) %></td>
        <td><%=visit.getWeightInPounds() %></td>
        <td><%=visit.getBloodPressure() %></td>
        <td><%=visit.getFHR() %></td>
        <td><%=visit.getNumberOfBaby() %></td>
        <td><%=visit.isSendBill() ? "Yes" : "No" %></td>
        <td><%=visit.isLowLyingPlacenta() ? "Yes" : "No" %></td>
        <td><%=visit.getNotes()%></td>
        <td><a href="editObsVisit.jsp?recordID=<%=visit.getID()%>">Edit</a></td>
    </tr>
<%
        }
        try {
            ObOfficeVisitlogger.logViewObstetricsOfficeVisit(loggedInMID, Long.parseLong(pidString), visitID);
        } catch (DBException e){
            e.printStackTrace();
        }
    }
%>
</table>
<%
    String today = DateHelper.format(new Date(), Constants.DEFAULT_DATE_PATTERN);
    boolean eligibleForObsVisit = false;
    List<ObstetricsPatientBean> records = new ArrayList<>();
    try {
        SearchUsersAction searchAction = new SearchUsersAction(prodDAO, loggedInMID.longValue());
        records = searchAction.fuzzySearchForObRecWithMID(Long.parseLong(pidString));
        String latestLMP = ObsHelper.getLatestLMP(records);
        eligibleForObsVisit = ObstetricsValidator.eligibleForObsVisit(latestLMP, today);

        if(!eligibleForObsVisit) {
            // no LMP record found or not eligible
        %>
            <div>This patient has no obstetrics record that is within 49 weeks so can't add new visit</div>
<%
        } else {
            session.setAttribute("latest-lmp", latestLMP);
%>
<div align="center">
    <a href="/iTrust/auth/hcp-uap/addObsVisit.jsp?pid=<%=pidString%>">Add Obstetrics Visit</a>
</div>
<%
        }
    } catch (Exception ex) {

    }
%>
<%@include file="/footer.jsp" %>


