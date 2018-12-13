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
<%@page import="edu.ncsu.csc.itrust.action.AddUltrasoundRecordAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundRecordDAO"%>


<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Edit Obstetrics Visit";
%>

<%@include file="/header.jsp"%>
<%
    /* Require a Patient ID first */
    String pidString = (String) session.getAttribute("pid");
    long recordID = Long.valueOf(request.getParameter("recordID"));
    PersonnelDAO ppDAO = prodDAO.getPersonnelDAO();
    PersonnelBean ppBean = ppDAO.getPersonnel(loggedInMID);
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
    int numOfWeeksPreg = -1;

    if (ppBean.getSpecialty().equals("OB/GYN") != true) {
        session.setAttribute("errorMessage", "Current HCP has no privilege to edit Obstetrics Visits");
        session.setAttribute("pid", pidString);
        response.sendRedirect("/iTrust/auth/hcp-uap/viewObsVisit.jsp?pid=" + pidString);
        return;
    }

%>

<div>Edit Obstetrics Visit</div>
<form id="editForm" action="editObsVisit.jsp" method="post"><input type="hidden"
                                                                          name="formIsFilled" value="true"> <br />
    <%
    long ID = recordID;
    long patientID = Long.valueOf(pidString);
    String createdDate;
    String locationID;
    String apptTypeID;
    String notes;
    String sendBill;
    String LMP;
    String weightInPounds;
    String bloodPressure;
    String FHR;
    String numberOfBaby;
    String lowLyingPlacenta;

    if (request.getParameter("deleteButton") != null) {
        try {
            new ObstetricsOfficeVisitMySQL().delete(ID);
            %>
            <div class="iTrustMessage">Record deleted</div>
            <%
        } catch (DBException ex) {
        %>
        <div class="iTrustError">Deleting visit failed</div>
        <%
        }
    }
    else if (request.getParameter("submitButton") != null) {
        createdDate = request.getParameter("created-date");
        locationID = request.getParameter("location-id");
        apptTypeID = request.getParameter("appt-type-id");
        notes = request.getParameter("notes");
        sendBill = request.getParameter("send-bill");
        LMP = request.getParameter("lmp");
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
        try {
            ObOfficeVisitlogger.logEditObstetricsOfficeVisit(loggedInMID, Long.parseLong(pidString), Long.toString(ID));
        } catch (DBException e){
            e.printStackTrace();
        }
        ObstetricsOfficeVisit visit = new ObstetricsOfficeVisit();
        visit.setID(ID);
        visit.setPatientID(patientID);
        visit.setCreatedDate(LocalDate.parse(createdDate, df).atStartOfDay());
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
            new ObstetricsOfficeVisitMySQL().update(visit);
            %>
            <div class="iTrustMessage">Edit Finished</div>
            <%
        }catch (DBException ex) {
            %>
            <div class="iTrustError">Editing visit failed</div>
            <%
        }

        }
    } else {
        // edit the visit
        ObstetricsOfficeVisit visit = new ObstetricsOfficeVisitMySQL().getByID(recordID);
        numOfWeeksPreg = ObstetricsValidator.getNumOfWeeksPregnant(visit.getCreatedDate().format(df), visit.getLMP().format(df));
        Date nextStart = new Date();
        if(numOfWeeksPreg <= 13) {
            nextStart = DateHelper.add(nextStart, 4, "weeks");
        }else if (numOfWeeksPreg <= 28) {
            nextStart = DateHelper.add(nextStart, 2, "weeks");
        }else if (numOfWeeksPreg <= 40) {
            nextStart = DateHelper.add(nextStart, 1, "weeks");
        } else if (numOfWeeksPreg <= 42) {
            nextStart = DateHelper.add(nextStart, 1, "days");
            Calendar c = Calendar.getInstance();
            c.setTime(nextStart);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            while(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                nextStart = DateHelper.add(nextStart, 1, "days");
                c.setTime(nextStart);
                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
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
                        <td><input name="created-date" id="created-date" type="text" readonly value="<%=visit.getCreatedDate().format(df) %>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">FHR <span class="required">*</span></td>
                        <td><input name="fhr" type="text" value="<%=visit.getFHR()%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Blood Pressure<span class="required">*</span></td>
                        <td><input name="blood-pressure" type="text" value="<%=visit.getBloodPressure()%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Weight in Pounds<span class="required">*</span></td>
                        <td><input name="weight-in-pounds" type="text" value="<%=visit.getWeightInPounds()%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Notes</td>
                        <td><input name="notes" type="text" value="<%=visit.getNotes()%>"></td>
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
                        <td><input name="number-of-baby" type="text" value="<%=visit.getNumberOfBaby()%>"></td>
                    </tr>

                    <tr>
                        <td class="subHeaderVertical">LMP <span class="required">*</span></td>
                        <td><input name="lmp" type="text" value="<%=visit.getLMP().format(df)%>" readonly></td>
                    </tr>
                </table>
        </tr>
    </table>
    <div align="center">
        <input type="submit" value="Submit" name="submitButton"/>
        <input type="submit" value="Delete" name="deleteButton"/>
        <input type="hidden" value="<%=pidString%>" name="pid"/>
        <input type="hidden" value="<%=recordID%>" name="recordID"/>
        <input type="hidden" value="<%=visit.getLocationID()%>" name="location-id"/>
        <input type="hidden" value="<%=visit.getApptTypeID()%>" name="appt-type-id"/>
    </div>
</form>
<table class="fTable" align="center">
    <tr>
        <th colspan="13">Ultrasound Records</th>
    </tr>
    <tr class="subHeader">
        <td>ID</td>
        <td>Visit ID</td>
        <td>Record Date</td>
        <td>CRL</td>
        <td>BPD</td>
        <td>HC</td>
        <td>FL</td>
        <td>OFD</td>
        <td>AC</td>
        <td>HL</td>
        <td>EFW</td>
        <td>Action</td>
        <td></td>
    </tr>
        <%
    List<UltrasoundRecordBean> ultrasoundRecords = new UltrasoundRecordDAO(prodDAO).fuzzeSearchForUltrasoundRecordWithVisitID(recordID);
    if(ultrasoundRecords != null && ultrasoundRecords.size() > 0){
        for(UltrasoundRecordBean record: ultrasoundRecords) {
%>
    <tr>
        <td><%=record.getID() %></td>
        <td><%=record.getVisitID() %></td>
        <td><%=record.getCreatedDate().format(df) %></td>
        <td><%=record.getCRL() %></td>
        <td><%=record.getBPD() %></td>
        <td><%=record.getHC() %></td>
        <td><%=record.getFL() %></td>
        <td><%=record.getOFD() %></td>
        <td><%=record.getAC() %></td>
        <td><%=record.getHL() %></td>
        <td><%=record.getEFW() %></td>
        <td><a href="uploadUltrasoundFile.jsp?recordID=<%=record.getID()%>&visitID=<%=record.getVisitID()%>">Upload</a></td>
        <%
            if(record.getURL() == null || record.getURL().isEmpty()) {
                %>
        <td></td>
        <%
            } else {
                %>
        <td><a href="javascript:void(0)" onclick="downloadFile(<%=record.getURL()%>)">Download</a></td>
        <%
            }
        %>
    </tr>
        <%      }
    }
%>
</table>
<br />
<br />
<div align="center">
    <a href="/iTrust/auth/hcp-uap/addUltrasound.jsp?pid=<%=pidString%>&recordID=<%=recordID%>">Add Ultrasound Record</a>
</div>
<div align="center">
    <div title="Add to Calendar" class="addeventatc">
        Schedule Next Visit
        <span class="start" id="next-start"><%=df2.format(nextStart)%> 08:00 AM</span>
        <span class="end" id="next-end"><%=df2.format(nextStart)%> 10:00 AM</span>
        <span class="timezone">America/Los_Angeles</span>
        <span class="title" id="next-title">Obstetrics Visit</span>
        <span class="description" id="next-description">Next Obstetrics Visit</span>
        <span class="location" id="next-location">My Office</span>
    </div>
</div>

<%
    }
%>
<div align="center">
    <a href="/iTrust/auth/hcp-uap/viewObsVisit.jsp?pid=<%=pidString%>">Go back to Obstetrics Visits</a>
</div>


<script>
    function downloadFile(fileID) {
        fileID = Number(fileID);
        console.log(fileID);
        window.location = 'DownloadFileServlet?q=query&fileID='+fileID;
/*        $.ajax({
            url : "DownloadFileServlet",
            data : {
                q : "query",
                fileID: fileID
            },
            success : function(e){
                console.log(e);
            }
        });*/
    }

</script>
<%@include file="/footer.jsp"%>
