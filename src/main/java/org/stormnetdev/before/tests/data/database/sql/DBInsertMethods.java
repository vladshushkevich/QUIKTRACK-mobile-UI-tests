package org.stormnetdev.before.tests.data.database.sql;

import org.postgresql.util.PSQLException;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.DBConnect;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

public class DBInsertMethods {

	/**
	 * Table: customer_migration
	 * Add migration user
	 */

	public void addUser(String id, String email, int wddBalanceAfter) throws SQLException {
		DBConnect connect = new DBConnect();
		try{
			String insertQuery;
			insertQuery = "insert into customer_migration (id, email, wddbalanceafter, status, transactionid, failurereason, emailsent) " +
					"values ('"
					+ id + "', '"
					+ email + "', "
					+ wddBalanceAfter + ", "
					+ "0, null, null, false)";
			connect.setData(insertQuery);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not added. " + e.getMessage());
			throw new SQLException();
		}
	}

	/**
	 * Table: projects
	 * Add project
	 */

	protected UUID addProject(String title, String description, String starteDate, String expiresAt, int wddTarget,
							  int wddCollected, int wddMin, int wddMax, int wddTolprate, String accountId,
							  String accountRs, String accountPublicKey, String accountPassphrase, String lptCurrentyId,
							  String shrinkingAddress, boolean hidden, BigDecimal destroyedLPTAmount, int status) throws SQLException {
		DBConnect connect = new DBConnect();
		UUID id;
		try {
			String insertQuery;
			Reporter.logInfo("accountrs is " + accountRs);
			id = UUID.randomUUID();
			Reporter.logInfo("project Id is " + id);

			insertQuery = "insert into projects (id, title, description, startedat, expiresat, wddtarget, wddcollected, " +
					"wddmin, wddmax, wddtolprate, account_id, account_rs, account_publickey, account_passphrase, lptcurrencyid, shrinkingaddress, hidden, destroyedlptamount, status) values ('"
					+ id + "', '"
					+ title + "', '"
					+ description + "', "
					+ starteDate + ", "
					+ expiresAt + ", "
					+ wddTarget + ", "
					+ wddCollected + ", "
					+ wddMin + ", "
					+ wddMax + ", "
					+ wddTolprate + ", '"
					+ accountId + "', '"
					+ accountRs + "', '"
					+ accountPublicKey + "', '"
					+ accountPassphrase + "', '"
					+ lptCurrentyId + "', '"
					+ shrinkingAddress + "', "
					+ hidden + ", "
					+ destroyedLPTAmount + ", "
					+ status + ")"
			;

			connect.setData(insertQuery);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not added. " + e.getMessage());
			throw new SQLException();
		}
		return id;
	}

	/**
	 * Table: projects_whitelist
	 * Add user to whitelist
	 */

	public void addAccess(String projectId, String email, int wddMax) throws SQLException {
		DBConnect connect = new DBConnect();
		String currentWDDMax = null;
		if(wddMax != 0){
			currentWDDMax = String.valueOf(wddMax);
		}
		try{
			String insertQuery;
			insertQuery = "insert into projects_whitelist (project_id, email, wddmax) " +
					"values ('"
					+ projectId + "', '"
					+ email + "', "
					+ currentWDDMax + ")";
			connect.setData(insertQuery);
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not added. " + e.getMessage());
			throw new SQLException();
		}
	}
}