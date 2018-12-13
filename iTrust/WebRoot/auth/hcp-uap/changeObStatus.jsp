<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.model.old.enums.Ethnicity"%>
<%@page import="edu.ncsu.csc.itrust.model.old.enums.BloodType"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.model.old.enums.Gender"%>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Deactivate Patient";
%>

<%@include file="/header.jsp"%>
<itrust:patientNav thisTitle="Change Elegibility for Obstetrics Care" />
<%
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("pid");

    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        pidString = (String)session.getAttribute("pid");
    }

    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        out.println("patientID is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp&isObstetric=true");
        return;
    }
    session.setAttribute("pid", pidString);
    /* If the patient id doesn't check out, then kick 'em out to the exception handler */
    EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);

    /* Now take care of updating information */

    PatientBean p;
    if (request.getParameter("formIsFilled") != null && request.getParameter("formIsFilled").equals("true") &&
            request.getParameter("understand") != null && (request.getParameter("understand").equals("YES") || request.getParameter("understand").equals("NO"))) {
        try {
            boolean enable = request.getParameter("understand").equals("YES");
            long pid = Long.parseLong(pidString);
            action.setObStatus(pid, enable);

%>
<br />
<div align=center>
    <span class="iTrustMessage">Patient's Eligibility Status Changed</span>
</div>
<br />
<%
} catch (Exception e) {
%>
<br />
<div align=center>
    <span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
</div>
<br />
<%
    }
} else {
    p = action.getPatient();

    if (request.getParameter("formIsFilled") != null && request.getParameter("formIsFilled").equals("true") &&
            (request.getParameter("understand") == null || (!request.getParameter("understand").equals("YES") || !request.getParameter("understand").equals("NO")))) {
%>
<br />
<div align=center>
    <span class="iTrustError">You must type "YES" or "NO" in the textbox.</span>
</div>
<br />
<%
    }

%>

<form id="changeForm" action="changeObStatus.jsp" method="post">
    <input type="hidden" name="formIsFilled" value="true"><br />
    <table cellspacing=0 align=center cellpadding=0>
        <tr>
            <td valign=top>
                <table class="fTable" align=center style="width: 350px;">
                    <tr>
                        <th colspan="4">Change Eligibility Status for Obstetrics Care</th>
                    </tr>
                    <tr>

                        <td class="subHeaderVertical">First Name:</td>
                        <td><%= StringEscapeUtils.escapeHtml("" + (p.getFirstName())) %></td>
                        <td class="subHeaderVertical">Last Name:</td>
                        <td><%= StringEscapeUtils.escapeHtml("" + (p.getLastName())) %></td>
                    </tr>
                    <tr>
                        <td colspan="4">Type "YES" in the box if you want to allow this patient for Obstetrics Care, or "NO" to disallow</td>
                    </tr>
                    <tr>
                        <td colspan="4"><div align="center"><input name="understand" type="text"></div></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br />
    <div align=center>
        <input type="submit" name="action"
               style="font-size: 16pt; font-weight: bold;" value="Confirm"><br /><br />
    </div>
</form>
<% } %>
<br />
<br />
<itrust:patientNav thisTitle="Change Eligibility" />

<%@include file="/footer.jsp"%>
