package com.codingwolves.Persistence.Connector;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnector {
    //public static Connection connection;
    private static String JDBC_DRIVER;
    private static String DATABASE_ADDRESS;
    private static String USERNAME;
    private static String PASSWORD;
    public static String MAIN_TABLE_NAME;
    public static String POSTINGS_LIST_TABLE;
    private static void setConnection() {
        Properties properties = new Properties();
        try(InputStream inputStream = DatabaseConnector.class.getClassLoader().
                getResourceAsStream("mySQL_dbConfig.properties")) {
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
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static DataSource setDataSource(){
        setConnection();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(DATABASE_ADDRESS);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
