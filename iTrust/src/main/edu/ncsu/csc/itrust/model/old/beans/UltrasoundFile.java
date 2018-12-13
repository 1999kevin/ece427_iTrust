package edu.ncsu.csc.itrust.model.old.beans;

import java.sql.Blob;

public class UltrasoundFile {
    private long ID;
    private Blob contents;
    private String filename;
    private long recordID;
    
    public void setID(long id) {this.ID = id;}
    public void setContents(Blob contents) {this.contents = contents;}
    public void setFilename(String filename) {this.filename = filename;}
    public void setRecordID(long recordID) {this.recordID = recordID;}
    
    public long getID() {return  ID;}
    public Blob getContents() {return contents;}
    public String getFilename() {return filename;}
    public long getRecordID() {return recordID;}
}
