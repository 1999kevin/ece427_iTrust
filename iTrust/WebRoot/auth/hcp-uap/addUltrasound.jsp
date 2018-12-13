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
<%@page import="edu.ncsu.csc.itrust.model.old.beans.UltrasoundRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.action.AddUltrasoundRecordAction"%>


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
        session.setAttribute("errorMessage", "Current HCP has no privilege to edit Obstetrics Visits");
        session.setAttribute("pid", pidString);
        response.sendRedirect("/iTrust/auth/hcp-uap/viewObsVisit.jsp?pid=" + pidString);
        return;
    }

    long recordID = Long.valueOf(request.getParameter("recordID"));

%>

<div>Add Ultrasound Record</div>
<form id="editForm" action="addUltrasound.jsp" method="post"><input type="hidden"
                                                                  name="formIsFilled" value="true"> <br />
    <%
        long patientID = Long.valueOf(pidString);
        String createdDate = LocalDateTime.now().format(df);
        String crl;
        String bpd;
        String hc;
        String fl;
        String ofd;
        String ac;
        String hl;
        String efw;

        if (request.getParameter("submitButton") != null) {
            crl = request.getParameter("crl");
            bpd = request.getParameter("bpd");
            hc = request.getParameter("hc");
            fl = request.getParameter("fl");
            ofd = request.getParameter("ofd");
            ac = request.getParameter("ac");
            hl = request.getParameter("hl");
            efw = request.getParameter("efw");

            // validate inputs
            String erroeMsg = ObstetricsOfficeVisitValidator.validateUltrasoundInputs(crl, bpd, hc, fl, ofd, ac, hl, efw);
            if(!erroeMsg.isEmpty()){
                // display error message
    %>
    <div class="iTrustError"><%=erroeMsg%></div>
    <%

    }else{
        try {
            ObOfficeVisitlogger.logUltrasound(loggedInMID, Long.parseLong(pidString), Long.toString(recordID));
        } catch (DBException e){
            e.printStackTrace();
        }
        // add the data
        try {
            UltrasoundRecordBean bean = new UltrasoundRecordBean();
            bean.setVisitID(recordID);
            bean.setCRL(Double.valueOf(crl));
            bean.setBPD(Double.valueOf(bpd));
            bean.setHC(Double.valueOf(hc));
            bean.setFL(Double.valueOf(fl));
            bean.setOFD(Double.valueOf(ofd));
            bean.setAC(Double.valueOf(ac));
            bean.setHL(Double.valueOf(hl));
            bean.setEFW(Double.valueOf(efw));
            bean.setCreatedDate(LocalDateTime.now());
            bean.setPatientID(patientID);
            long newID = new AddUltrasoundRecordAction(prodDAO).addUltrasoundRecord(bean);
    %>
    <div class="iTrustMessage">Ultrasound record added</div>
    <%


        } catch (Exception ex) {

    %>
    <div class="iTrustError">Adding ultrasound record failed</div>
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
                        <th colspan=2>Ultrasound</th>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Record Date <span class="required">*</span></td>
                        <td><input name="created-date" id="created-date" type="text" disabled="disabled" value="<%=createdDate%>"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Crown rump length (CRL) <span class="required">*</span></td>
                        <td><input name="crl" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Biparietal diameter (BPD)<span class="required">*</span></td>
                        <td><input name="bpd" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Head circumference (HC)<span class="required">*</span></td>
                        <td><input name="hc" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Femur length (FL)<span class="required">*</span></td>
                        <td><input name="fl" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Occipitofrontal diameter (OFD)<span class="required">*</span></td>
                        <td><input name="ofd" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Abdominal circumference (AC)<span class="required">*</span></td>
                        <td><input name="ac" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Humerus length (HL)<span class="required">*</span></td>
                        <td><input name="hl" type="text"></td>
                    </tr>
                    <tr>
                        <td class="subHeaderVertical">Estimated fetal weight (EFW)<span class="required">*</span></td>
                        <td><input name="efw" type="text"></td>
                    </tr>
                </table>
        </tr>
    </table>
    <div align="center">
        <input type="submit" value="Submit" name="submitButton"/>
        <input type="hidden" value="<%=pidString%>" name="pid"/>
        <input type="hidden" value="<%=recordID%>" name="recordID"/>
    </div>
</form>
<br />
<br />
<div align="center">
    <a href="/iTrust/auth/hcp-uap/editObsVisit.jsp?recordID=<%=recordID%>">Go back to Obstetrics Visit <%=recordID%></a>
</div>

<%@include file="/footer.jsp"%>
