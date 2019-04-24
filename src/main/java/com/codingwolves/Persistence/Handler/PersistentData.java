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
 * The PersistentData class is the necessary interface that stores the outputs of inverted index
 * into a persistent storage (mySQL DB). This class essentially contains all the method calls
 * required for the mySQL engine. This class uses the JDBC classes designed for the Spring frameworks
 * that works especially well in a Java EE application. JDBC was used instead of JPA to allow more direct
 * control over the mySQL engine.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */

public class PersistentData extends DatabaseConnector implements ModelFileDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    private String sqlStatement;

    /**
     * The ColumnMapper class implements the Spring framework's RowMapper interface that outputs
     * the a list of strings. This class essentially returns a list of strings containing all
     * the file names in the posting list table.
     */
    private static final class ColumnMapper implements RowMapper <String>{
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException{
            //get all column names
            return resultSet.getString(1);
        }
    }

    /**
     * The FileMapper class implements the Spring framework's RowMapper interface which outputs
     * a list of {@link com.codingwolves.Persistence.Handler.ModelFile}. The class gives a
     * list of file information required for creating the inverted indices.
     */
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

    /**
     * The WordMapper class implements the Spring framework's RowMapper Interface. This class
     * outputs all the words stored in the mySQL database, specifically the posting's list table.
     * <b>Note:</b> This class should not be used until absolutely necessary.  All the word in the
     * database will be stored in memory even though only a few are needed.
     */
    private  static final class WordMapper implements RowMapper<String>{
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException{
            return resultSet.getString("word");
        }
    }

    /**
     * This method checks if a word exists in the persistent storage.
     * @param wordSample The word which the user wishes to check
     * @return A boolean indicating whether the word exists in the table or not.
     */
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

    @Deprecated
    /**
     * This method calls the WordMapper class and returns a list of all words stored in
     * persistent storage. This deprecated method should not be used until needed. This method
     * can easily cause an overflow or a memory leak unless properly used.
     * @return A list of string containing all the words in persistent storage.
     */
    private List<String> wordList(){
        return jdbcTemplate.query("SELECT `word` FROM "+
                POSTINGS_LIST_TABLE, new WordMapper());
    }

    /**
     * This method calls the ColumnMapper class and return a list of string that contains the
     * names of all the fields in the postings list table.
     * @return a list of strings that contains all the names  of the field.
     */
    private List<String> columnNamesList (){
        return jdbcTemplate.query("SELECT `COLUMN_NAME` FROM " +
                "`INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_NAME`= '"
                +POSTINGS_LIST_TABLE +"'", new ColumnMapper());
    }

    /**
     * This method will delete the file and all the information associated with said file
     * from the entire database. It will also delete the entire row from the posting list
     * table that had NULL value in all its associated field.
     * @param fileName String that tells the method which file to delete
     * @return A string message which indicates the successful deletion of a file
     * from the database.
     */
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

    /**
     * This method saves all the data created on the client side into persistent storage.
     * It stores data like file name, checksum and path name into a table and the stores the
     * postings list into another table.
     * @param files The methods accepts the list of {@link com.codingwolves.FileParser.FileTemplate}
     */
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

    /**
     * The method calls the {@link com.codingwolves.Persistence.Handler.PersistentData.FileMapper} class.
     * The method queries mySQL DB via JDBC to store in the client's system.
     * @return A list of {@link com.codingwolves.Persistence.Handler.ModelFile} which contains only the metadata
     * like file name, checksum and path name.
     */
    @Override
    public List<ModelFile> listAllFiles() {
        sqlStatement ="SELECT filename, checksum, path FROM "+MAIN_TABLE_NAME;
        List <ModelFile> fileList = jdbcTemplate.query(sqlStatement,new FileMapper());
        return fileList;
    }

    /**
     * The method saves the postings list into the table in the persistent database.
     * The method verifies if the table contains the file name and/or the word and executes
     * appropriate actions via Spring's JDBC.
     * @param inputFiles A list of {@link com.codingwolves.FileParser.FileTemplate} containing metadata and
     *                   the information inside each file.
     */
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

    /**
     * This method checks to see if there are any rows with NULL in all of its fields
     * and deletes that row. This method is called after the a file has been deleted
     * from the database. This is just a clean up operation.
     */
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