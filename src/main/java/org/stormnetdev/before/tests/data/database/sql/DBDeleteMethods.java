package org.stormnetdev.before.tests.data.database.sql;

import java.sql.SQLException;
import java.util.UUID;

import org.postgresql.util.PSQLException;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.DBConnect;

public class DBDeleteMethods {

	/**
	 * Table: customer_migration
	 * Delete all customers
	 */

	public void clearMigrationTable() throws SQLException{
		DBConnect connect = new DBConnect();
		try{
			String query = "DELETE FROM customer_migration";
			connect.deleteData(query);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not deleted. " + e.getMessage());
			throw new SQLException();
		}
	}

	/**
	 * Table: projects
	 * Delete project
	 * @param projectId
	 */

	public void deleteProject(UUID projectId) throws SQLException{
		DBConnect connect = new DBConnect();
		try{
			String donateQuery = "DELETE FROM donate_logs WHERE project_id = '" + projectId + "'";
			connect.deleteData(donateQuery);
			deleteUserAccess(projectId.toString());
			String projectQuery = "DELETE FROM projects WHERE id = '" + projectId + "'";
			connect.deleteData(projectQuery);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not deleted. " + e.getMessage());
			throw new SQLException();
		}
	}
	
	/**
	 * Table: user_region
	 * Delete line with userId and regionId
	 */
	
	public void deleteUserRegionData(int userId, int regionId) throws SQLException{
		DBConnect connect = new DBConnect();
		try {
			String query = "DELETE FROM user_region WHERE user_id = " + userId + " and region_id = " + regionId;			
			connect.deleteData(query);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not deleted. " + e.getMessage());
			throw new SQLException();
		}
	}

	/**
	 * Table: projects_whitelist
	 * Delete user access
	 * @param projectId
	 */

	public void deleteUserAccess(String projectId) throws SQLException{
		DBConnect connect = new DBConnect();
		try{
			String query = "DELETE FROM projects_whitelist WHERE project_id = '" + projectId + "'";
			connect.deleteData(query);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not deleted. " + e.getMessage());
			throw new SQLException();
		}
	}
}
