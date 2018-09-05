package org.stormnetdev.utils;

import org.postgresql.util.PSQLException;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.tests.api.tests.TestBaseAPI;
import org.stormnetdev.utils.configuration.Configurator;

import java.sql.*;
import java.util.ArrayList;

public class DBConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnect() {
		String databaseName = Configurator.databaseServer;
		String databaseUserName = Configurator.databaseUserName;
		String databasePassword = Configurator.databasePassword;
		String defaultHost = Configurator.serverHost;
		try {
			String host;
			Reporter.logOperation("Connect to database...");
			Class.forName("org.postgresql.Driver");
			Reporter.logInfo("PostgreSQL JDBC Driver Registered!");
	        if(TestBaseAPI.appHost==null){
	            host = defaultHost;
	        }else{
	        	host = TestBaseAPI.appHost;
	        }
	        Reporter.logInfo("Current host is " + host);
			con = DriverManager.getConnection(
					"jdbc:postgresql://" + host + ":5432/" + databaseName + "", "" + databaseUserName + "",
					"" + databasePassword + "");
			if (con != null) {
				Reporter.logPassedOperation();
				Reporter.logInfo("You made it, take control your database now!");
			} else {
				Reporter.logFailed("Failed to make connection!");
			}
		} catch (ClassNotFoundException e) {
			Reporter.logFailed("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			Reporter.logInfo(e.toString());
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			Reporter.logFailed("Connection Failed! Check output console");
			Reporter.logInfo(e.toString());
			e.printStackTrace();
			return;
		}
	}
		
	public String getData(String query, String column) throws SQLException{
		String resultValue = null;
		try {
			Reporter.logOperation("Get value from database using query: '" + query + "'");
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				resultValue = rs.getString(column);
			}
			Reporter.logPassedOperation();
			Reporter.logInfo(column + " = " + resultValue);			
			if(resultValue == null){
				Reporter.logWarning("Value was not found in database.");
			}				
			return resultValue;
		} catch (PSQLException ex) {
			Reporter.logFailed("Query is incorrect. " + ex.getMessage());
			throw new SQLException();
		}	
	}

	public ArrayList getDataList(String query, String column) throws SQLException {
		ArrayList resultValues = new ArrayList();
		try {
			Reporter.logOperation("Get value from database using query: '" + query + "'");
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				Reporter.logInfo(rs.getString(column));
				resultValues.add(rs.getString(column));
			}
			Reporter.logPassedOperation();
			Reporter.logInfo(column + " = " + resultValues);
			if(resultValues == null){
				Reporter.logWarning("Value was not found in database.");
			}
			return resultValues;
		} catch (PSQLException ex) {
			Reporter.logFailed("Query is incorrect. " + ex.getMessage());
			throw new SQLException();
		}
	}
	 
	public void setData(String query) throws SQLException{
		Reporter.logOperation("Insert record into database using sql query: " + query);
		st = con.createStatement();
		st.executeUpdate(query);
		Reporter.logPassedOperation();
	}
	
	public void deleteData(String query) throws SQLException{
		Reporter.logOperation("Delete record from database using sql query: " + query);
		try {
			st = con.createStatement();
			st.executeUpdate(query);
			Reporter.logPassedOperation();
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not deleted." + e.getMessage());
			throw new SQLException();
		}
	}
	
	public void updateData(String query) throws SQLException{
		Reporter.logOperation("Update record in database using sql query: " + query);
		try {
			st = con.createStatement();
			st.executeUpdate(query);
			Reporter.logPassedOperation();
		} catch (PSQLException e) {
			Reporter.logFailed("Record was not updated." + e.getMessage());
			throw new SQLException();
		}
	}
}

