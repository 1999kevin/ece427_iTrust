<%--
  Created by IntelliJ IDEA.
  User: Kuriyama
  Date: 2018/11/7
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyApptsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ApptBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ApptTypeDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Child Hospital Visits";
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("pid");
    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        request.setAttribute("errorMessage", "pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewChildHospitalVisit.jsp&isCHV=true");
        return;
    }
    session.setAttribute("pid", pidString);
%>
<%@include file="/header.jsp" %>

<%
    if (pidString != null) {
    	ViewMyApptsAction action = new ViewMyApptsAction(prodDAO, loggedInMID.longValue());
    	ApptTypeDAO apptTypeDAO = prodDAO.getApptTypeDAO();
    	List<ApptBean> appts = action.getAllMyCHVAppointments(Long.parseLong(pidString));
    	session.setAttribute("appts", appts);
    	
    	if (appts.size() > 0) { 
  %>	
    	<table class="fTable" align="center">
    		<tr>
    			<th>Appointment ID</th>
    			<th>Patient</th>
    			<th>Appointment Type</th>
    			<th>Appointment Date/Time</th>
    			<th>Comments</th>
    			<th>View</th>
    		</tr>
    <%		 
    		int index = 0;
    		for(ApptBean a : appts) { 
    			if (!a.getApptType().equals("Childbirth")) {
    				continue;
    			}
    				
    			Date d = new Date(a.getDate().getTime());
    			Date now = new Date();
    			DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    			
    			String row = "<tr";
    %>
    			<%=row+" "+((index%2 == 1)?"class=\"alt\"":"")+">"%>
    				<td><%= StringEscapeUtils.escapeHtml("" + ( a.getApptID() )) %></td></td>
    				<td><%= StringEscapeUtils.escapeHtml("" + ( action.getName(a.getPatient()) )) %></td>
    				<td><%= StringEscapeUtils.escapeHtml("" + ( a.getApptType() )) %></td>
    				<td><%= StringEscapeUtils.escapeHtml("" + ( format.format(d) )) %></td>
    				<td><%= StringEscapeUtils.escapeHtml("" + ( a.getComment() )) %></td>
    				<td>
    					<a href="editChildHospitalVisit.jsp?pid=<%=a.getPatient() %>&apptid=<%=a.getApptID()%>">Enter Visit</a>
    				    <a href="editChildHospitalVisit.jsp?pid=<%=a.getPatient() %>&apptid=<%=a.getApptID()%>&add=true">Initialize Visit</a>
    				</td>
    			</tr>
    	<%
    			index ++;
    		}
    	%>
    	</table>
    <%	} else { %>
    	<div>
    		<i>You have no Child Hospital Visit Appointments</i>
    	</div>
    <%	} %>	
    	<br />
    <%
    }
%>
