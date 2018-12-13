<%--
  Created by IntelliJ IDEA.
  User: ray
  Date: 11/27/18
  Time: 9:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
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
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.TransactionDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.RecordBean"%>

<%@include file="/global.jsp" %>


<%
    pageTitle = "iTrust - View Labor And Delivery Report";
    /* Require a Patient ID first */
    String pidString = (String) request.getParameter("pid");
    if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
        request.setAttribute("errorMessage", "pidstring is null");
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewLaborAndDelivery.jsp&isLDR=true");
        return;
    }
    session.setAttribute("pid", pidString);

    PersonnelDAO ppDAO = prodDAO.getPersonnelDAO();
    PersonnelBean ppBean = ppDAO.getPersonnel(loggedInMID);

    if (pidString != null){
        RecordBean bean = null;
        try{
            SearchUsersAction searchAction = new SearchUsersAction(prodDAO, loggedInMID.longValue());
            bean = searchAction.searchRecordWithMID(Long.parseLong(pidString));
            session.setAttribute("rebean",bean);
            
            TransactionDAO transDAO = prodDAO.getTransactionDAO();
            transDAO.logTransaction(TransactionType.LABOR_AND_DELIVERY_REPORT, loggedInMID, Long.parseLong(pidString), "");
            
          %>
<%@include file="/header.jsp" %>

<table class="fTable" align="center" style="width: 900px;">
    <tr>
        <th colspan="10">Past Pregnancy Records</th>
    </tr>
    <tr class="subHeader">
        <td>Pregnancy Term</td>
        <td>Delivery Type</td>
        <td>Conception Year</td>
    </tr>
    <%
        List<String> pregTerm = bean.getPregnancyterm();
        List<String> delivType = bean.getDeliveryType();
        List<Integer> conpYear = bean.getYearOfConception();
        try{
            int size = pregTerm.size();
            for(int i=0;i<size;i++){
                String preg = pregTerm.get(i);
                String deli = delivType.get(i);
                String con = String.valueOf(conpYear.get(i));
    %>
    <tr>
        <td><%= preg %></td>
        <td><%= deli %></td>
        <td><%= con %></td>
    </tr>
    <%

            }

        }catch (Exception e){

        }
    %>


</table>
<br/>

<table class="fTable" align="center" style="width: 900px;">
    <tr>
        <th colspan="10">Delivery Date & Blood Type</th>
    </tr>

    <tr class="subHeader">
        <td>Estimated delivery date</td>
        <td>Blood Type</td>
    </tr>

    <tr>
        <td><%= bean.getDeliveryDate() %></td>
        <td><%= bean.getBloodType() %></td>

    </tr>

</table>
<br/>


<table class="fTable" align="center" style="width: 900px;">
    <tr>
        <th colspan="10">Obstetrics Office Visit Information</th>
    </tr>
    <tr class="subHeader">
        <td>Weeks pregnant</td>
        <td>Weight</td>
        <td>Blood Pressure</td>
        <td>FHR</td>
        <td>Pregnancy Number</td>
        <td>Low Lying Placenta Discovered</td>
        <td>Complications</td>
    </tr>
    <%
        List<List<String>> ofv = null;
        try{
            ofv = bean.getObstetricsOfficeVisit();
            for (List<String> row:ofv){

    %>
    <tr>
        <td><%= row.get(0) %></td>
        <td><%= row.get(1) %></td>
        <td><%= row.get(2) %></td>
        <td><%= row.get(3) %></td>
        <td><%= row.get(4) %></td>
        <td><%= row.get(5) %></td>
        <td><%= row.get(6) %></td>
    </tr>
    <%

            }

        }catch (Exception e){

        }

    %>

</table>
<br/>


<table class="fTable" align="center" style="width: 900px;">
    <tr>
        <th colspan="10">Pregnancy Complication Warning Flags</th>
    </tr>

    <tr class="subHeader">
        <td>RH-flag</td>
        <td>High Blood Pressure</td>
        <td>Advanced Maternal Age</td>
        <td>Low-lying Placenta</td>
        <td>High Genetic Potential-Miscarriage</td>
        <td>Abnormal FHR</td>
        <td>Multiples in Current Pregnancy</td>
        <td>Atypical Weight Change</td>
        <td>Hyperemesis Gravidarum</td>
        <td>Hypothyroidism</td>
    </tr>

    <tr>
        <td><%= bean.getRHflag()%></td>
        <td><%= bean.getHighBloodPressure() %></td>
        <td><%= bean.getAdvancedMaternalAge()%></td>
        <td><%= bean.getLowlyingplacenta() %></td>
        <td><%= bean.isHighgeneticpotentialmiscarriage()%></td>
        <td><%= bean.getAbnormalFHR() %></td>
        <td><%= bean.getnumberofpregnancy()%></td>
        <td><%= bean.getAtypicalweight()%></td>
        <td><%= bean.getHyperemesisgravidarum() %></td>
        <td><%= bean.getHypothyroidism() %></td>
    </tr>

</table>
<br/>


<table class="fTable" align="center" style="width: 900px;">
    <tr>
        <th colspan="10">Relevant Pre-existing Conditions</th>
    </tr>

    <tr class="subHeader">
        <td>Diabetes</td>
        <td>Chronic illness (autoimmune disorders)</td>
        <td>Cancers</td>
        <td>STDs</td>
    </tr>

    <tr>
        <td><%= bean.getDiabetes()%></td>
        <td><%= bean.getisChronic()%></td>
        <td><%= bean.getCancer()%></td>
        <td><%= bean.getSdt()%></td>
    </tr>

</table>
<br/>


<table class="fTable" align="center" style="width: 900px;">
    <tr>
        <th colspan="10">Mother`s Common Drug Allergies</th>
    </tr>

    <tr class="subHeader">
        <td>Penicillin</td>
        <td>Sulfa Drugs</td>
        <td>Tetracycline</td>
        <td>Codeine</td>
        <td>NSAIDs</td>
    </tr>

    <%
        List<String> alle = null;
        try{
            alle = bean.getmaternalallergies();
            boolean pen = false;
            boolean sul = false;
            boolean ter = false;
            boolean code = false;
            boolean NS = false;

            for (String s:alle){
                if (s.equals("Penicillin"))
                    pen = true;
                if (s.equals("Sulfa Drugs"))
                    sul = true;
                if (s.equals("Tetracycline"))
                    ter = true;
                if (s.equals("Codeine"))
                    code = true;
                if (s.equals("NSAIDs"))
                    NS = true;
            }

    %>

    <tr>
        <td><%=pen%></td>
        <td><%=sul%></td>
        <td><%=ter%></td>
        <td><%=code%></td>
        <td><%=NS%></td>
    </tr>

    <%
        }catch (Exception e){

        }

    %>

</table>
<br/>




<%
        }catch(Exception e){

        }

    }
%>











