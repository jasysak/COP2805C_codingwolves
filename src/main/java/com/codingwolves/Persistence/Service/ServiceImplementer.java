package com.codingwolves.Persistence.Service;

import com.codingwolves.Persistence.MasterInformation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class ServiceImplementer implements ServiceDao, MasterInformation {
    private DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(DATABASE_ADDRESS);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    private String sqlStatement;
    @Override
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
    public void createPostingsListTable() {
        sqlStatement = "CREATE TABLE IF NOT EXISTS "+POSTINGS_LIST_TABLE+
                " (word VARCHAR(255) NOT NULL, "+
                "PRIMARY KEY (word))";
        jdbcTemplate.execute(sqlStatement);
    }

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
