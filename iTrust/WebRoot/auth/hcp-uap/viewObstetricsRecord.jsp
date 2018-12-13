<%@page import="org.apache.commons.lang3.CharSequenceUtils"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - View Obstetrics Record";
%>

<%@include file="/header.jsp"%>
<%
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("patient");
    String recordIdString = (String) request.getParameter("recordID");

    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        out.println("pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp");
        return;
    }

    if (recordIdString == null || recordIdString.equals("") || 1 > recordIdString.length()) {
        out.println("recordIdString is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp");
        return;
    }

    long recordId = Long.parseLong(recordIdString);

    // load data
    List<ObstetricsPatientBean> records = (List<ObstetricsPatientBean>)session.getAttribute("obrecords");
    ObstetricsPatientBean record = null;
    for(ObstetricsPatientBean r: records) {
        if(r.getID() == recordId) {
            record = r;
            break;
        }
    }
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    // output
    int yearOfConception = record.getYearOfConception();
    double weightGained = record.getWeightGain();
    String deliveryType = record.getDeliveryType();
    int numberOfBirth = record.getNumberofBaby();
    String lmp = record.getLMP();
    String recordDate = record.getCreatedDate();
    double hoursInLabor = record.getNumberOfLaborHour();
    
    String edd = LocalDate.parse(lmp, DateTimeFormatter.ofPattern("MM/dd/yyyy")).plusDays(280).toString();
    try {
		oblogger.logViewObstetricsRecord(loggedInMID, Long.parseLong(pidString), edd);
    } catch (DBException e){
		e.printStackTrace();
	}
    
%>

<div>Viewing Obstetrics Record (ID <%=recordIdString %>)</div>
<form id="editForm" action="editPatient.jsp" method="post"><input type="hidden"
                                                                  name="formIsFilled" value="true"> <br />
    <table cellspacing=0 align=center cellpadding=0>
        <tr>
            <td valign=top>
                <table class="fTable" align=center style="width: 350px;">
                    <tr>
                        <th colspan=2>Obstetrics Record</th>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Record Date</td>
                        <td id="record-date"><%= recordDate %></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Conception Year</td>
                        <td><%= yearOfConception %></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">LMP</td>
                        <td id="lmp"> <%=lmp %></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">EDD</td>
                        <td id="edd"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Number of Weeks-Days Pregnant</td>
                        <td id="preg-weeks"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Weight Gained During Pregnancy</td>
                        <td><%= weightGained %></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Hours in Labor</td>
                        <td><%= hoursInLabor %></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Delivery Type</td>
                        <td><%= deliveryType %></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Number of Birth</td>
                        <td><%= numberOfBirth %></td>
                    </tr>
                </table>
        </tr>
    </table>
</form>
<br />
<br />
<div align="center">
    <a href="/iTrust/auth/hcp-uap/viewObstetricsRecords.jsp?pid=<%=pidString%>">Go back to Obstetrics Records</a>
</div>

<script>

    var recordDate = moment($("#record-date")[0].innerText);
    var lmpDate = moment($("#lmp")[0].innerText);
    var eddDate = moment(lmpDate).add(280, 'days');
    $("#edd").text(eddDate.format("MM/DD/YYYY"));
    $("#record-date").text(recordDate.format("MM/DD/YYYY"));
    $("#lmp").text(lmpDate.format("MM/DD/YYYY"));

    var diffDays = recordDate.diff(lmpDate, 'days');
    var pregWeeks = parseInt(diffDays / 7);
    var pregDays = diffDays % 7;
    var pregWeeksString = "";
    if (pregWeeks < 0 || pregWeeks > 42) {
        pregWeeksString = "N/A";
    } else {
        pregWeeksString = pregWeeks > 9 ? "" + pregWeeks: "0" + pregWeeks;
        pregWeeksString += "-" + pregDays;
    }
    $("#preg-weeks").text(pregWeeksString);


</script>

<%@include file="/footer.jsp"%>
