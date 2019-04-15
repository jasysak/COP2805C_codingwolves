package com.codingwolves.Persistence;

public interface MasterInformation {
    String MAIN_TABLE_NAME="file_information";
    String POSTINGS_LIST_TABLE = "posting_lists";
    String JDBC_DRIVER ="com.mysql.cj.jdbc.Driver";
    String DATABASE_ADDRESS ="jdbc:mysql://localhost:3306/INVERTED_INDEX?" +
            "createDatabaseIfNotExist=true";
    String USERNAME ="root";
    String PASSWORD = "";
}
