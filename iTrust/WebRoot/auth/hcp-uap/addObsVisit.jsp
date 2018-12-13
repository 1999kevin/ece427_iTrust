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
<%@page import="java.time.LocalDate"%>
<%@page import="edu.ncsu.csc.itrust.Constants"%>;
<%@page import="edu.ncsu.csc.itrust.helper.DateHelper"%>;
<%@page import="edu.ncsu.csc.itrust.helper.ObsHelper"%>;
<%@page import="edu.ncsu.csc.itrust.ObstetricsValidator"%>;
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisit"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL"%>
<%@page import="edu.ncsu.csc.itrust.model.officeVisit.obstetricsOfficeVisit.ObstetricsOfficeVisitValidator"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>


<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Add Obstetrics Visit";
%>

<%@include file="/header.jsp"%>
<%
    /* Require a Patient ID first */
    String pidString = (String) session.getAttribute("pid");
    PersonnelDAO ppDAO = prodDAO.getPersonnelDAO();
    PersonnelBean ppBean = ppDAO.getPersonnel(loggedInMID);
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    if (ppBean.getSpecialty().equals("OB/GYN") != true) {
        session.setAttribute("errorMessage", "Current HCP has no privilege to add Obstetrics Visits. You can only add general visits.");
        session.setAttribute("pid", pidString);
        response.sendRedirect("/iTrust/auth/hcp-uap/viewObsVisit.jsp?pid=" + pidString);
        return;
    }

%>

<div>Add Obstetrics Visit</div>
<form id="editForm" action="addObsVisit.jsp" method="post"><input type="hidden"
                                                                   name="formIsFilled" value="true"> <br />
    <%

        long patientID = Long.valueOf(pidString);
        String createdDate = LocalDateTime.now().format(df);
        String locationID;
        String apptTypeID;
        String notes;
        String sendBill;
        String LMP = (String)session.getAttribute("latest-lmp");
        String weightInPounds;
        String bloodPressure;
        String FHR;
        String numberOfBaby;
        String lowLyingPlacenta;

        if (request.getParameter("submitButton") != null) {
            //createdDate = request.getParameter("created-date");
            locationID = request.getParameter("location-id");
            apptTypeID = request.getParameter("appt-type-id");
            notes = request.getParameter("notes");
            sendBill = request.getParameter("send-bill");
            //LMP = request.getParameter("latest-lmp");
            weightInPounds = request.getParameter("weight-in-pounds");
            bloodPressure = request.getParameter("blood-pressure");
            FHR = request.getParameter("fhr");
            numberOfBaby = request.getParameter("number-of-baby");
            lowLyingPlacenta = request.getParameter("low-lying-placenta");

            // validate inputs
            String erroeMsg = ObstetricsOfficeVisitValidator.validateInputs( createdDate,
                    locationID,
                    apptTypeID,
                    notes,
                    sendBill,
                    LMP,
                    weightInPounds,
                    bloodPressure,
                    FHR,
                    numberOfBaby,
                    lowLyingPlacenta);
            if(!erroeMsg.isEmpty()){
                // display error message
    %>
    <div class="iTrustError"><%=erroeMsg%></div>
    <%

    }else{
        ObstetricsOfficeVisit visit = new ObstetricsOfficeVisit();
        visit.setPatientID(patientID);
        visit.setCreatedDate(LocalDateTime.now());
        visit.setLMP(LocalDate.parse(LMP, df).atStartOfDay());
        visit.setLocationID(locationID);
        visit.setApptTypeID(Long.valueOf(apptTypeID));
        visit.setNotes(notes);
        visit.setSendBill(sendBill.equals("Yes"));
        visit.setWeightInPounds(Double.valueOf(weightInPounds));
        visit.setFHR(Integer.parseInt(FHR));
        visit.setNumberOfBaby(Integer.parseInt(numberOfBaby));
        visit.setBloodPressure(bloodPressure);
        visit.setLowLyingPlacenta(lowLyingPlacenta.equals("Yes"));

        try {
            long returnid = new ObstetricsOfficeVisitMySQL().addReturnGeneratedId(visit);
            ObOfficeVisitlogger.logCreateObstetricsOfficeVisit(loggedInMID, Long.parseLong(pidString), Long.toString(returnid));
        %>
    <div class="iTrustMessage">New Obstetrcis Visit added</div>
    <%
        } catch (DBException ex) {
    %>
        <div class="iTrustError">Adding visit failed</div>
        <div class="iTrustError"><%=erroeMsg%></div>
    <%
    }



            }
        }

    %>

    <table cellspacing=0 align=center cellpadding=0>
        <tr>
            <td valign=top>
                <table class="fTable" align=center style="width: 350px;">
                    <tr>
                        <th colspan=2>Obstetrics Visit</th>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Record Date <span class="required">*</span></td>
                        <td><input name="created-date" id="created-date" type="text" disabled="disabled" value="<%=createdDate%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">FHR <span class="required">*</span></td>
                        <td><input name="fhr" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Blood Pressure</td>
                        <td><input name="blood-pressure" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Weight in Pounds</td>
                        <td><input name="weight-in-pounds" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Notes</td>
                        <td><input name="notes" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Send Bill<span class="required">*</span></td>
                        <td><select name="send-bill">
                            <option value="choose one below"># choose one below</option>
                            <option value="Yes">Yes</option>
                            <option value="No">No</option>
                        </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Low Lying Placenta Observed<span class="required">*</span></td>
                        <td><select name="low-lying-placenta">
                            <option value="choose one below"># choose one below</option>
                            <option value="Yes">Yes</option>
                            <option value="No">No</option>
                        </select>
                        </td>
                    </tr>

                    <tr>
                        <td class="subHeaderVertical">Number of Baby</td>
                        <td><input name="number-of-baby" type="text"></td>
                    </tr>

                    <tr>
                        <td class="subHeaderVertical">Latest LMP<span class="required">*</span></td>
                        <td><input name="latest-lmp" type="text" disabled="disabled" value="<%=LMP%>"></td>
                    </tr>
                </table>
        </tr>
    </table>
    <div align="center">
        <input type="submit" value="Submit" name="submitButton"/>
        <input type="hidden" value="<%=pidString%>" name="pid"/>
        <input type="hidden" value="<%=1%>" name="location-id"/>
        <input type="hidden" value="<%=7%>" name="appt-type-id"/>
    </div>
</form>
<br />
<br />
<div align="center">
    <a href="/iTrust/auth/hcp-uap/viewObsVisit.jsp?pid=<%=pidString%>">Go back to Obstetrics Visits</a>
</div>

<%@include file="/footer.jsp"%>
