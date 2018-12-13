package edu.ncsu.csc.itrust.model.old.beans.loaders;

import edu.ncsu.csc.itrust.model.old.beans.UltrasoundFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UltrasoundFileLoader implements BeanLoader<UltrasoundFile> {
    @Override
    public List<UltrasoundFile> loadList(ResultSet rs) throws SQLException {
        List<UltrasoundFile> list = new ArrayList<>();
        while (rs.next()) {
            list.add(loadSingle(rs));
        }
        return list;
    }

    @Override
    public UltrasoundFile loadSingle(ResultSet rs) throws SQLException {
        UltrasoundFile file = new UltrasoundFile();
        loadCommon(rs, file);
        return file;
    }

    private void loadCommon(ResultSet rs, UltrasoundFile file) throws SQLException {
        file.setID(rs.getLong("ID"));
        file.setRecordID(rs.getLong("recordID"));
        file.setFilename(rs.getString("filename"));
        file.setContents(rs.getBlob("contents"));
    }

    @Override
    public PreparedStatement loadParameters(PreparedStatement ps, UltrasoundFile file) throws SQLException {
        ps.setLong(1, file.getRecordID());
        ps.setString(2, file.getFilename());
        ps.setBlob(3, file.getContents());
        return ps;
    }
}
