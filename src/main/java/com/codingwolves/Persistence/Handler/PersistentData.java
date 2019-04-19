package com.codingwolves.Persistence.Handler;


import com.codingwolves.FileParser.FileTemplate;
import com.codingwolves.InvertedIndex.PostingsList;
import com.codingwolves.Persistence.Connector.DatabaseConnector;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 */

public class PersistentData extends DatabaseConnector implements ModelFileDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(setDataSource());
    private String sqlStatement;
    private static final class ColumnMapper implements RowMapper <String>{
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException{
            return resultSet.getString(1);
        }
    }

    private static final class FileMapper implements RowMapper<ModelFile>{
        @Override
        public ModelFile mapRow(ResultSet resultSet, int i) throws SQLException {
            ModelFile individualFile = new ModelFile(
                    resultSet.getString("filename"),
                    resultSet.getString("checksum"),
                    resultSet.getString("path")
            );
            return individualFile;
        }
    }
    private  static final class WordMapper implements RowMapper<String>{
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException{
            return resultSet.getString("word");
        }
    }
    private boolean wordExistsInDB(String wordSample){
        boolean wordExist = false;
        sqlStatement = "SELECT COUNT(*) FROM " + POSTINGS_LIST_TABLE +
                " WHERE `word` = ?";
        int wordCount = jdbcTemplate.queryForObject(
                sqlStatement,
                new String [] {wordSample},
                Integer.class);
        if(wordCount > 0)
            wordExist = true;
        return wordExist;
    }
    private List<String> wordList(){
        return jdbcTemplate.query("SELECT `word` FROM "+
                POSTINGS_LIST_TABLE, new WordMapper());
    }
    private List<String> columnNamesList (){
        return jdbcTemplate.query("SELECT `COLUMN_NAME` FROM " +
                "`INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_NAME`= '"
                +POSTINGS_LIST_TABLE +"'", new ColumnMapper());
    }

    @Override
    public String deleteFile(String fileName) {
        sqlStatement = "DELETE FROM "+MAIN_TABLE_NAME+
        " WHERE filename = '"+ fileName +"'";
        jdbcTemplate.execute(sqlStatement);
        sqlStatement = "ALTER TABLE " + POSTINGS_LIST_TABLE +
                " DROP COLUMN `"+ fileName+"`";
        jdbcTemplate.execute(sqlStatement);
        cleanDB();
        return fileName + " has been deleted!";
    }

    @Override
    public void updateFile(ModelFile individualFile) {

    }

    @Override
    public void addFile(List<FileTemplate> files) {
        for (FileTemplate individualFile: files){
            sqlStatement = "INSERT INTO "+MAIN_TABLE_NAME+
                    " (filename, checksum, path) VALUES (?, ?, ?)";
            jdbcTemplate.update(
                    sqlStatement,
                    individualFile.getFileName(),
                    individualFile.getCheckSum(),
                    individualFile.getPath()
            );
        }
        addPostingsList(files);
    }

    @Override
    public List<ModelFile> listAllFiles() {
        sqlStatement ="SELECT filename, checksum, path FROM "+MAIN_TABLE_NAME;
        List <ModelFile> fileList = jdbcTemplate.query(sqlStatement,new FileMapper());
        return fileList;
    }

    private void addPostingsList(List<FileTemplate> inputFiles){
        PostingsList postingsList = new PostingsList(inputFiles);
        Map<String, Map<String, List<Integer>>> invertedIndexList
                = postingsList.getInvertedIndex();
        for(String wordKey : invertedIndexList.keySet()){
            for(String fileName : invertedIndexList.get(wordKey).keySet()){
                if(!columnNamesList().contains(fileName)){
                    sqlStatement = "ALTER TABLE "+POSTINGS_LIST_TABLE+
                            " ADD COLUMN `"+fileName+"` varchar(255)";
                    jdbcTemplate.execute(sqlStatement);
                }
                else{
                    sqlStatement = "UPDATE " + POSTINGS_LIST_TABLE +
                            " SET `"+fileName+"` ='' WHERE `word` = ?";
                    jdbcTemplate.update(sqlStatement, wordKey);
                }
                if (wordExistsInDB(wordKey)){
                    sqlStatement = "UPDATE "+POSTINGS_LIST_TABLE+ " SET `"
                    +fileName+"` = ? WHERE `word` = ?";
                    jdbcTemplate.update(sqlStatement,
                            Arrays.toString(
                            invertedIndexList
                            .get(wordKey)
                            .get(fileName)
                            .toArray()
                    ),wordKey);
                }
                else{
                    sqlStatement = "INSERT INTO "+POSTINGS_LIST_TABLE+
                            "(`word` ,`"+fileName+"`) VALUES (?,?)";
                    jdbcTemplate.update(
                            sqlStatement,
                            wordKey,
                            Arrays.toString(
                                    invertedIndexList
                                            .get(wordKey)
                                            .get(fileName)
                                            .toArray()
                            ));
                }
            }
        }
    }

    private void cleanDB(){
        List columns = columnNamesList();
        int columnCount = columns.size();
        sqlStatement = "DELETE FROM "+POSTINGS_LIST_TABLE+" WHERE ";
        for(int i =1; i<columnCount;i++){
            if(i!=columnCount-1){
                sqlStatement += "`"+columns.get(i)+"` IS NULL AND ";
            }
            else{
                sqlStatement+= "`" +columns.get(i)+"` IS NULL";
            }
        }
        jdbcTemplate.execute(sqlStatement);
    }

}