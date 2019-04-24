package com.codingwolves.Persistence.Service;

/**
 * This interface primarily deals with the table creation within the database.
 */
public interface ServiceDao {
    void createMainTable();
    void createPostingsListTable();
}
