<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Obstetrics Records";
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("pid");
    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
    	request.setAttribute("errorMessage", "pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp&isObstetric=true");
        return;
    }
    session.setAttribute("pid", pidString);
%>

<%@include file="/header.jsp" %>

<%
    if (pidString != null) {
%>
<br /><br />
<input type="hidden" name="add" id="add" />
<table class="fTable" align="center">
    <tr>
        <th colspan="10">Obstetrics Care Records</th>
    </tr>
    <tr class="subHeader">
        <td>Record ID</td>
        <td>Record Date</td>
        <td>LMP</td>
        <td>Year of Conception</td>
        <td>Delivery Type</td>
        <td># of Birth</td>
        <td>Action</td>
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
        <td><a href="viewObstetricsRecord.jsp?patient=<%= pidString %>&recordID=<%= record.getID() %>">View Details</a></td>
    </tr>
        <%
                }
            } catch (Exception e) {

            }
            finally {

        	}
    }
%>

</table>
<br />
<div align="center">
    <a href="/iTrust/auth/hcp-uap/addObstetricsRecord.jsp?pid=<%=pidString%>">Add a new Obstetrics Record</a>
</div>


<%@include file="/footer.jsp" %>
