package org.stormnetdev.before.tests.data.database.sql;

import org.postgresql.util.PSQLException;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.DBConnect;
import java.sql.SQLException;

public class DBSelectMethods {

	/**
	 * Table: customer_migration
	 * Get user by email
	 */

	public static String getUserByEmail(String email, String column) throws SQLException {
		DBConnect connect = new DBConnect();
		String user;
		user = connect.getData("SELECT * from customer_migration where email='" + email + "'", column);
		Reporter.logInfo(column + " = " + user);
		return user;
	}

	/**
	 * Table: projects
	 * Get random project
	 */

	public static String getRandomProject(String column) throws SQLException {
		DBConnect connect = new DBConnect();
		String project;
		project = connect.getData("select * from projects ORDER BY RANDOM() limit 1", column);
		Reporter.logInfo(column + " = " + project);
		return project;
	}

	/**
	 * Table: projects
	 * Get project by accountRs
	 */

	public static String getProjectByAccountRs(String accountRs, String column) throws SQLException {
		DBConnect connect = new DBConnect();
		String project;
		project = connect.getData("SELECT * from projects where account_rs='" + accountRs + "'", column);
		Reporter.logInfo(column + " = " + project);
		return project;
	}

	/**
	 * Table: country
	 * Get random country id
	 */

	public static String getRandomCountryId() throws SQLException {
		DBConnect connect = new DBConnect();
		String countryId;
		try{
			countryId = connect.getData("select id from country ORDER BY RANDOM() limit 1", "id");
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not added. " + e.getMessage());
			throw new SQLException();
		}
		return countryId;
	}
}