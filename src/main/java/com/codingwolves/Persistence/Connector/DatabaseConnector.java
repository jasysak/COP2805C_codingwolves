package com.codingwolves.Persistence.Connector;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * The DatabaseConnector class is required to establish a connection between
 * the SQL connector and the Spring JDBC interface. This class extracts information
 * from the pertinent property files and outputs the results needs to establish said
 * connection
 * <b>Note: </b> This class is meant to be used in conjunction with the Spring JdbcTemplate class
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
public class DatabaseConnector {
    private static String JDBC_DRIVER;
    private static String DATABASE_ADDRESS;
    private static String USERNAME;
    private static String PASSWORD;
    public static String MAIN_TABLE_NAME;
    public static String POSTINGS_LIST_TABLE;

    /**\
     * This method extracts the information from a property file and instantiates it to private fields
     * @param propertyFileName The name of the property file
     */
    private static void setConnection(String propertyFileName) {
        Properties properties = new Properties();
        //try with resources used
        try(InputStream inputStream = DatabaseConnector.class.getClassLoader().
                getResourceAsStream(propertyFileName)) {
            properties.load(inputStream);
            JDBC_DRIVER = properties.getProperty("JDBC_DRIVER");
            DATABASE_ADDRESS = properties.getProperty("DATABASE_ADDRESS");
            USERNAME = properties.getProperty("USERNAME");
            PASSWORD = properties.getProperty("PASSWORD");
            MAIN_TABLE_NAME = properties.getProperty("MAIN_TABLE_NAME");
            POSTINGS_LIST_TABLE = properties.getProperty("POSTINGS_LIST_TABLE");
            if (PASSWORD == null || PASSWORD.isEmpty()){
                PASSWORD = "";
            }
        }
        //in case the user typed in the wrong property file
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * This method is needed to create the jdbcTemplate object. It creates a DataSource object by
     * utilizing the Spring DriverManagerDataSource class.
     * @return a DataSource object to be used in the jdbcTemplate constructor.
     */
    public static DataSource getDataSource(){
        setConnection("mySQL_dbConfig.properties");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(DATABASE_ADDRESS);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
