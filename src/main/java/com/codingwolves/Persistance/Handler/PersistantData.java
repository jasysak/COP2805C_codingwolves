package com.codingwolves.Persistance.Handler;
import com.codingwolves.FileParser.FileTemplate;

import java.sql.*;
import java.util.List;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
@Repository
public class PersistantData implements DataAccessObject{
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
        throws DataAccessException{
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private SqlParameterSource getSqlParameterByModel(ModelFile file){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if(file!=null){
            parameterSource.addValue("fileName",file.getFileName());
            parameterSource.addValue("path",file.getFilePath());
            parameterSource.addValue("checksum",file.getChecksum());
        }

        return parameterSource;
    }

    private static final class FileMapper implements RowMapper<ModelFile>{
        public ModelFile mapRow (ResultSet rs, int rowNum) throws SQLException{
            ModelFile individualFile = new ModelFile();
            individualFile.setChecksum(rs.getString("checksum"));
            individualFile.setFileName(rs.getString("filename"));
            individualFile.setFilePath(rs.getString("path"));
            return individualFile;
        }
    }
    @Override
    public void deleteFile(ModelFile individualFile) {
        String sqlStatement = "DELETE FROM fileinformation WHERE filename = :fileName";
        namedParameterJdbcTemplate.update(sqlStatement,getSqlParameterByModel(individualFile));
    }

    @Override
    public void updateFile(ModelFile individualFile) {
        String sqlStatement = "UPDATE fileinformation SET " +
                "checksum = :checksum" +
                "path     = :path WHERE " +
                "filename = :fileName";
        namedParameterJdbcTemplate.update(sqlStatement,getSqlParameterByModel(individualFile));
    }

    @Override
    public void addFile(ModelFile individualFile) {
        String sqlStatement = "INSERT INTO fileinformation(checksum, filename, path)" +
                "VALUES(:checksum, :fileName, :path)";
        namedParameterJdbcTemplate.update(sqlStatement,getSqlParameterByModel(individualFile));
    }

    @Override
    public ModelFile findPostingList(String word) {
        return null;
    }
}

*/

public class PersistantData implements AutoCloseable {

    static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://SG-COP2805C-319-master.servers.mongodirector.com:3306/";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER ="root";
    static final String PASS ="";
    static final String DB_NAME ="INVERTED_INDEX";
    /*static final String ROOTUSER = "sgroot";
    static final String ROOTPASS = "@a7VRm904fd6lWWz";
    static final String USER = "rgeorge12@hawkmail.hccfl.edu";
    static final String PASS = "Password1.";*/

    @Override
    public void close() throws Exception {
        System.out.println("Connection has closed");
    }

    public static void createDB(){
        //Connection connection = null;
        //Statement statement = null;
        try(
                Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement statement = connection.createStatement()
        ) {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to mySQL server...");
            //connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating the database...");
            //statement = connection.createStatement();
            String sqlStatement = "CREATE DATABASE IF NOT EXISTS"+DB_NAME;
            statement.executeUpdate(sqlStatement);
            System.out.println("Database successfully created...");
        }
        catch (SQLException se){
            se.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*finally {
            try{
                if(statement!=null){
                    statement.close();
                    connection.close();
                }
            }
            catch (SQLException se){
                se.printStackTrace();
            }
        }*/
    }

    public static void createTable(){
        try(
                Connection connection = DriverManager.getConnection(DB_URL+DB_NAME,USER,PASS);
                Statement statement = connection.createStatement()
        ){
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to mySQL server");
            String sqlStatement = "CREATE TABLE IF NOT EXISTS fileinformation (" +
                    "    id INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "    filename VARCHAR(300) NOT NULL," +
                    "    checksum VARCHAR(300) NOT NULL," +
                    "    path VARCHAR(500) NOT NULL," +
                    "    PRIMARY KEY(id)" +
                    ");";
            //String sql1 = "INSERT INTO fileinformation (filename,checksum,path) " +
                  //  "VALUES ('QWERTY','UIOPA','SDFGH')";
            statement.executeUpdate(sqlStatement);
            //statement.executeUpdate(sql1);
            System.out.println("Table successfully created...");

        }
        catch (SQLException se){
            se.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertFileInformation(List<FileTemplate> files){
        String sqlStatement = "INSERT INTO fileinformation (`filename`,`checksum`,`path`) SELECT * FROM" +
                " (SELECT ?, ?, ?) AS temporaryInformation WHERE NOT EXISTS " +
                "( SELECT `filename`,`checksum` FROM fileinformation WHERE `filename` = ? AND `checksum`=?)";
        try(
                Connection connection = DriverManager.getConnection(DB_URL+DB_NAME,USER,PASS);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ){
            Class.forName(JDBC_DRIVER);
            for (FileTemplate individualFile: files){
                preparedStatement.setString(1,individualFile.getFileName());
                preparedStatement.setString(2,individualFile.getCheckSum());
                preparedStatement.setString(3,individualFile.getPath());
                preparedStatement.setString(4,individualFile.getFileName());
                preparedStatement.setString(5,individualFile.getCheckSum());
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException se){
            se.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getFileInformation(){
        String sqlStatement="";
    }


}