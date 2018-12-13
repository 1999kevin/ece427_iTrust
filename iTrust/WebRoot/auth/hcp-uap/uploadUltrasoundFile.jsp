<%@page import="java.io.DataInputStream"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.InputStream"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.UploadUltrasoundFileAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.CSVFormatException"%>
<%@page import="edu.ncsu.csc.itrust.exception.AddPatientFileException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@page import="org.apache.commons.fileupload.DefaultFileItemFactory" %>
<%@page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@page import="org.apache.commons.fileupload.FileItem" %>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - Upload File for Ultrasound";
%>

<%@include file="/header.jsp" %>

<%

    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    long recordID = Long.valueOf(request.getParameter("recordID"));
    long visitID = Long.valueOf(request.getParameter("visitID"));
    if(!isMultipart){

%>

<form name="mainForm" enctype="multipart/form-data" action="uploadUltrasoundFile.jsp?recordID=<%=recordID%>&visitID=<%=visitID%>" method="post">
    <table class="fTable mainTable" align="center">
        <tr><th colspan="3">Choose File</th></tr>
        <tr><td>
            <input name="patientFile" type="file"/>
        </td>
            <td>
                <input type="submit" value="Send File" id="sendFile" name="sendFile">
            </td>
        </tr></table>
</form>
<%
}else{
    String headerMessage = "Upload Successful";

    String error = "";
    Boolean fatal = false;
    List<String> results = new ArrayList<String>();
    InputStream fileStream = null;
    boolean ignore = true;
    FileItemFactory factory = new DefaultFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List<FileItem> items = upload.parseRequest(request);
    Iterator iter = items.iterator();
    String filename = "";

    while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        if(item.getFieldName() != null && item.getFieldName().equals("patientFile")) {
            fileStream = items.get(0).getInputStream();
            filename = item.getName();
        }
    }

    if(fileStream!=null){
        try{
            UploadUltrasoundFileAction action = new UploadUltrasoundFileAction(fileStream, prodDAO,loggedInMID.longValue(), recordID, filename);
            action.save();
        }catch(Exception e){
            fatal = true;
            error = e.getMessage();
        }
    }else{
        fatal = true;
        error = "Please choose a file to upload.";
    }

    String headerColor = "#00CCCC";
    if(fatal){
        headerMessage = "File upload was unsuccessful. " + error;
        headerColor = "#ff3333";
    }else {
        headerMessage = "File upload was successful<br />";
    }

%>

<div align=center>
    <span class="iTrustMessage" style="color:<%=headerColor%>"><%= headerMessage %></span>
</div>
<%
    }
%>
<div align="center">
    <a href="/iTrust/auth/hcp-uap/editObsVisit.jsp?recordID=<%=visitID%>">Go back</a>
</div>

<%@include file="/footer.jsp" %>
