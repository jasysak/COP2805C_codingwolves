package com.codingwolves.Persistence.Service;

import com.codingwolves.Persistence.Connector.DatabaseConnector;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *The ServiceImplementer class has been created to build methods that will create a mySQL database
 * and the tables associated with it. The tables will store the information of all the files that
 * the user has uploaded along with the posting lists that has been created via server side action.
 *
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
public class ServiceImplementer extends DatabaseConnector implements ServiceDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    private String sqlStatement;
    @Override
    /**
     * This method will create  a table where all the information pertaining to a file will be stored.
     * The fields of the table include: file name, checksum and the fileID. <br>
     * <b>Note: </b> The file ID is an auto incremented number generated via the MariaDB engine. No
     * action is needed from the user or the JRE.
     * <br> <b>Note: </b> This method will create a table only if the table doesn't already exist in the
     * database.
     *
     */
    public void createMainTable() {
        sqlStatement = "CREATE TABLE IF NOT EXISTS "+MAIN_TABLE_NAME+
        " (id int(200) NOT NULL AUTO_INCREMENT," +
                " filename VARCHAR(255) NOT NULL,"+
                "checksum VARCHAR(255) NOT NULL,"+
        " path VARCHAR(255) NOT NULL,"+
        " PRIMARY KEY (id , checksum))";
        jdbcTemplate.execute(sqlStatement);
    }
    @Override
    /**
     * This method will create a table where all the posting list created via the inverted index data
     * structure will be stored. The fields of the table include: word and file names. The word field is
     * the primary key which means that it has to be unique. <br>
     * <b>Note:</b> The data in the field name can be null. This table is not normalized and as such it is
     * possible for the data to be corrupted. This table is meant for small data collection ONLY.
     * <br><b>Note:</b> This method will create a table only if the table doesn't already exist in the database.
     */
    public void createPostingsListTable() {
        sqlStatement = "CREATE TABLE IF NOT EXISTS "+POSTINGS_LIST_TABLE+
                " (word VARCHAR(255) NOT NULL, "+
                "PRIMARY KEY (word))";
        jdbcTemplate.execute(sqlStatement);
    }

    /**
     * This static method will call both the createPostingListTable and createMainTable methods as
     * both these tables are needed.
     * @return A string which returns a success or a failure message.
     */
    public static String databaseInitializer(){
        String returnString;
        try{
            ServiceImplementer serviceImplementer = new ServiceImplementer();
            serviceImplementer.createMainTable();
            serviceImplementer.createPostingsListTable();
            returnString = "Database created!";
        }
        catch (Exception e){
            returnString = "Database creation failed due to SQL error!";
        }
        return returnString;
    }
}
