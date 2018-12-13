package edu.ncsu.csc.itrust.server;

import edu.ncsu.csc.itrust.action.base.DownloadUltrasoundFileAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class DownloadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DownloadUltrasoundFileAction action;

    public DownloadFileServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        if (query == null) {
            return;
        }

        long fileID = Long.valueOf(request.getParameter("fileID"));
        action = new DownloadUltrasoundFileAction(DAOFactory.getProductionInstance(), fileID);
        try {
            UltrasoundFile file = action.getFile();
            String filename = file.getFilename();
            Blob blob = file.getContents();
            InputStream inputStream = blob.getBinaryStream();
            int fileLength = inputStream.available();

            ServletContext context = getServletContext();
            String mimeType = context.getMimeType(filename);
            if(mimeType == null) {
                mimeType = "application/octet-stream";
            }

            // set content properties and header attributes for the response
            response.setContentType(mimeType);
            response.setContentLength(fileLength);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", filename);
            response.setHeader(headerKey, headerValue);

            // writes the file to the client
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.close();

        } catch (DBException ex) {
            throw new ServletException();
        } catch (SQLException ex2) {
            throw new ServletException();
        }


    }
}
