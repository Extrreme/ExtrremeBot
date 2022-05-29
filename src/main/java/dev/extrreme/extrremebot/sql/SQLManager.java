package dev.extrreme.extrremebot.sql;

import dev.extrreme.extrremebot.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Amazing class for dealing with SQL tasks programmatically
 *
 */

@SuppressWarnings("unused")
public class SQLManager {
	private final MySQL sqlConnection;
	
	/**
	 * Create an SQL handler for a database
	 * @param sqlConnection The database
	 */
	public SQLManager(MySQL sqlConnection){
		this.sqlConnection = sqlConnection;
	}
	
	public MySQL getSQlConnectionHandler() {
		return this.sqlConnection;
	}
	
	public synchronized Connection grabConnection() {
		Connection c = sqlConnection.getConnection();
		try {
			c.setAutoCommit(true);
		} catch (SQLException e) {
			Main.log("Error connecting to SQL database!");
			e.printStackTrace();
		}
		try {
			if (c.isClosed()){
				return grabConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return c;
		}
		return c;
	}
	
	/**
	 * Close the connection to the database
	 */
	public void closeConnection(Connection c){
		try {
			c.close();
		} catch (Exception e) {
			Main.log("Error closing connection to SQL database!");
		}
	}

	/**
	 * 
	 * @param tableName The table to search
	 * @param keyName The name of the SQL table's PRIMARY key
	 * @param keyValue The value of the SQL table's PRIMARY key to search for the row of
	 * @param valueName The column name you want the value of
	 * @return The value of the found cell, or null if not found
	 */
	public synchronized byte[] searchBytesInTable(String tableName, String keyName, String keyValue, String valueName) {
		Connection c = grabConnection();
		try {
			PreparedStatement statement = c.prepareStatement("SELECT * FROM "+tableName+" WHERE "+keyName+" = ?;");
			statement.setString(1, keyValue);
			ResultSet res = statement.executeQuery();
			res.next();
			byte[] found;
			try {
				if (res.getString(keyName) == null) {
					found = null;
				} else {
					found = res.getBytes(valueName);
				}
			} catch (Exception e) {
				found = null;
			}
			res.close();
			statement.close();
			return found;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * 
	 * @param tableName The table to search
	 * @param valueName The column name you want the value of
	 * @return The value of the found cell, or null if not found
	 */
	public synchronized byte[] searchBytesInTable(String tableName, Map<String, String> keys, String valueName) {
		Connection c = grabConnection();
		try {
			StringBuilder queryStr = new StringBuilder("SELECT * FROM "+tableName+" WHERE ");
			List<String> ks = new ArrayList<>(keys.keySet());
			Map<Integer, String> keyVals = new HashMap<>();
			for (int i = 0; i < ks.size(); i++) {
				if (i != 0) {
					queryStr.append(" AND ");
				}
				keyVals.put(i+1, keys.get(ks.get(i)));
				queryStr.append(ks.get(i)).append(" = ?");
			}
			queryStr.append(";");
			PreparedStatement statement = c.prepareStatement(queryStr.toString());
			for (Integer i : keyVals.keySet()) {
				statement.setString(i, keyVals.get(i));
			}
			ResultSet res = statement.executeQuery();
			res.next();
			byte[] found;
			try {
				if (res.getString(ks.get(0)) == null) {
					found = null;
				} else {
					found = res.getBytes(valueName);
				}
			} catch (SQLException e) {
				found = null;
			}
			res.close();
			statement.close();
			return found;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * 
	 * @param tableName The table to search
	 * @param valueName The column name you want the value of
	 * @return The value of the found cell, or null if not found
	 */
	public synchronized Blob searchBlobInTable(String tableName, Map<String, String> keys, String valueName) {
		Connection c = grabConnection();
		try {
			StringBuilder queryStr = new StringBuilder("SELECT * FROM "+tableName+" WHERE ");
			List<String> ks = new ArrayList<>(keys.keySet());
			Map<Integer, String> keyVals = new HashMap<>();
			for (int i = 0; i < ks.size(); i++){
				if (i != 0) {
					queryStr.append(" AND ");
				}
				keyVals.put(i+1, keys.get(ks.get(i)));
				queryStr.append(ks.get(i)).append(" = ?");
			}
			queryStr.append(";");
			PreparedStatement statement = c.prepareStatement(queryStr.toString());
			for (Integer i : keyVals.keySet()) {
				statement.setString(i, keyVals.get(i));
			}
			ResultSet res = statement.executeQuery();
			res.next();
			Blob found;
			try {
				if (res.getString(ks.get(0)) == null) {
					found = null;
				} else {
					found = res.getBlob(valueName);
				}
			} catch (SQLException e) {
				found = null;
			}

			res.close();
			statement.close();
			return found;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * 
	 * @param tableName The table to search
	 * @param keyName The name of the SQL table's PRIMARY key
	 * @param keyValue The value of the SQL table's PRIMARY key to search for the row of
	 * @param valueName The column name you want the value of
	 * @return The value of the found cell, or null if not found
	 */
	public synchronized Object searchTable(String tableName, String keyName, String keyValue, String valueName) {
		Connection c = grabConnection();
		try {
			PreparedStatement statement = c.prepareStatement("SELECT * FROM "+tableName+" WHERE "+keyName+" = ?;");
			statement.setString(1, keyValue);
			ResultSet res = statement.executeQuery();
			res.next();
			Object found;
			try {
				if (res.getString(keyName) == null) {
					found = null;
				} else {
					found = res.getObject(valueName);
				}
			} catch (Exception e) {
				found = null;
			}

			res.close();
			statement.close();

			return found;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}

	/**
	 * Execute an SQL statement on the database
	 * 
	 * @param statement The statement to execute
	 */
	public synchronized boolean execute(String statement) {
		Connection c = grabConnection();
		try {
			PreparedStatement placeStatement = c.prepareStatement(statement);
			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}		
	}
	
	/**
	 * Execute an SQL statement on the database
	 * 
	 * @param statement The statement to execute
	 */
	public synchronized boolean execute(String statement, Map<Integer, Object> params) {
		Connection c = grabConnection();
		try {
			PreparedStatement placeStatement = c.prepareStatement(statement);
			for (Integer i : params.keySet()) {
				Object o = params.get(i);
				if (o == null) {
					o = "null";
				}
				if (o instanceof byte[]) {
					placeStatement.setBytes(i, (byte[]) o);
				} else if (o instanceof Blob) {
					placeStatement.setBlob(i, (Blob) o);
				} else if (o instanceof String) {
					placeStatement.setString(i, (String) o);
				} else {
					placeStatement.setString(i, o.toString());
				}
			}

			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}		
	}
	
	/**
	 * Set a cell value in an SQL table
	 * 
	 * @param tableName The table to modify
	 * @param keyName The table's PRIMARY KEY
	 * @param keyValue The table's PRIMARY KEY's value to change the row of
	 * @param valueName The name of the column to edit the value for
	 * @param value The value of the cell to edit
	 * @return True if edited, false if not
	 */
	public synchronized boolean setBytesInTable(String tableName, String keyName, String keyValue, String valueName, byte[] value) {
		Connection c = grabConnection();
		try {
			String replace = "INSERT INTO "+tableName+" (`"+keyName+"`, `"+valueName+"`) VALUES (?, ?)"
					+ " ON DUPLICATE KEY UPDATE "+valueName+" = ?;";
			PreparedStatement placeStatement = c.prepareStatement(replace);
			placeStatement.setString(1, keyValue);
			placeStatement.setBytes(2, value);
			placeStatement.setBytes(3, value);
			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Set a cell value in an SQL table
	 * 
	 * @param tableName The table to modify
	 * @param keyName The table's PRIMARY KEY
	 * @param keyValue The table's PRIMARY KEY's value to change the row of
	 * @return True if edited, false if not
	 */
	public synchronized boolean setInTable(String tableName, String keyName, String keyValue) {
		Connection c = grabConnection();
		try {
			String replace = "INSERT INTO "+tableName+" (`"+keyName+"`) VALUES (?)"
					+ " ON DUPLICATE KEY UPDATE "+keyName+" = ?;";
			PreparedStatement placeStatement = c.prepareStatement(replace);
			placeStatement.setString(1, keyValue);
			placeStatement.setString(2, keyValue);
			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Set a cell value in an SQL table
	 * 
	 * @param tableName The table to modify
	 * @param keyName The table's PRIMARY KEY
	 * @param keyValue The table's PRIMARY KEY's value to change the row of
	 * @param valueName The name of the column to edit the value for
	 * @param value The value of the cell to edit
	 * @return True if edited, false if not
	 */
	public synchronized boolean setInTable(String tableName, String keyName, String keyValue, String valueName, Object value) {
		Connection c = grabConnection();
		try {
			String replace = "INSERT INTO "+tableName+" (`"+keyName+"`, `"+valueName+"`) VALUES (?, ?)"
					+ " ON DUPLICATE KEY UPDATE "+valueName+" = ?;";
			PreparedStatement placeStatement = c.prepareStatement(replace);
			placeStatement.setString(1, keyValue);
			placeStatement.setString(2, value+"");
			placeStatement.setString(3, value+"");
			placeStatement.executeUpdate();
			placeStatement.close();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Set a cell value in an SQL table
	 * 
	 * @param tableName The table to modify
	 * @param keyName The table's PRIMARY KEY
	 * @param keyValue The table's PRIMARY KEY's value to change the row of
	 * @return True if edited, false if not
	 */
	public synchronized boolean setRowInTable(String tableName, String keyName, String keyValue, Map<String, Object> vals) {
		Connection c = grabConnection();
		try {
			List<String> columns = new ArrayList<>();
			columns.add(keyName);
			columns.addAll(vals.keySet());
			
			List<Object> values = new ArrayList<>();
			values.add(keyValue);

			for (String key : columns){
				if (key.equals(keyName)) {
					continue;
				}
				values.add(vals.get(key));
			}
			
			StringBuilder columnBuilder = new StringBuilder("(");
			boolean first = true;
			for (String key : columns){
				if (!first) {
					columnBuilder.append(", ");
				}
				else {
					first = false;
				}
				columnBuilder.append("`").append(key).append("`");
			}
			columnBuilder.append(")");
			
			StringBuilder valsBuilder = new StringBuilder("(");
			first = true;
			for (String ignored : columns){
				if (!first) {
					valsBuilder.append(", ");
				}
				else {
					first = false;
				}
				valsBuilder.append("?");
			}
			valsBuilder.append(")");
			
			StringBuilder updateBuilder = new StringBuilder();
			first = true;
			for (String valName : vals.keySet()){
				if (!first) {
					updateBuilder.append(", ");
				}
				else {
					first = false;
				}
				updateBuilder.append("`").append(valName).append("`=?");
			}
			
			String replace = "INSERT INTO "+tableName+" "+ columnBuilder +" VALUES "+ valsBuilder
					+ " ON DUPLICATE KEY UPDATE "+ updateBuilder +";";
			PreparedStatement placeStatement = c.prepareStatement(replace);
			int n = 1;
			for (Object val : values) {
				placeStatement.setObject(n, val);
				n++;
			}
			//Now to prepare the 'UPDATE' statement
			for (String valName : vals.keySet()){
				Object o = vals.get(valName);
				if (o instanceof Blob) {
					placeStatement.setBlob(n, (Blob) o);
				} else if (o instanceof byte[]) {
					placeStatement.setBytes(n, (byte[]) o);
				} else if (o instanceof String) {
					placeStatement.setString(n, (String) o);
				} else {
					placeStatement.setObject(n, o);
				}
				
				n++;
			}
			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Set a cell value in an SQL table
	 * 
	 * @param tableName The table to modify
	 * @param keyName The table's PRIMARY KEY
	 * @param keyValue The table's PRIMARY KEY's value to change the row of
	 * @return True if edited, false if not
	 */
	public synchronized boolean setRowOfBytesInTable(String tableName, String keyName, byte[] keyValue, Map<String, byte[]> vals) {
		Connection c = grabConnection();
		try {
			List<String> columns = new ArrayList<>();
			columns.add(keyName);
			columns.addAll(vals.keySet());
			
			List<byte[]> values = new ArrayList<>();
			values.add(keyValue);
			for (String key : columns) {
				if (key.equals(keyName)) {
					continue;
				}
				values.add(vals.get(key));
			}
			
			StringBuilder columnBuilder = new StringBuilder("(");
			boolean first = true;
			for (String key : columns) {
				if (!first) {
					columnBuilder.append(", ");
				}
				else {
					first = false;
				}
				columnBuilder.append("`").append(key).append("`");
			}
			columnBuilder.append(")");
			
			StringBuilder valsBuilder = new StringBuilder("(");
			first = true;
			for (String ignored : columns){
				if (!first) {
					valsBuilder.append(", ");
				}
				else {
					first = false;
				}
				valsBuilder.append("?");
			}
			valsBuilder.append(")");
			
			StringBuilder updateBuilder = new StringBuilder();
			first = true;
			for(String valName : vals.keySet()) {
				if (!first) {
					updateBuilder.append(", ");
				}
				else {
					first = false;
				}
				updateBuilder.append("`").append(valName).append("`=?");
			}
			
			String replace = "INSERT INTO "+tableName+" "+ columnBuilder +" VALUES "+ valsBuilder
					+ " ON DUPLICATE KEY UPDATE "+ updateBuilder +";";
			PreparedStatement placeStatement = c.prepareStatement(replace);
			int n = 1;
			for (byte[] val : values) {
				placeStatement.setBytes(n, val);
				n++;
			}
			//Now to prepare the 'UPDATE' statement
			for (String valName : vals.keySet()) {
				placeStatement.setBytes(n, vals.get(valName));
				n++;
			}
			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Creates a table, if not already existing, of the desired specification.
	 * 
	 * @param tableName The table's name
	 * @param columns The columns of the table's names
	 * @param types The types of the columns
	 */
	public synchronized boolean createTable(String tableName, String[] columns, String[] types) {
		Connection c = grabConnection();

		try {
			StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + "(");
			boolean first = true;
			for (int i = 0; i < columns.length && i < types.length; i++){
				if (!first) {
					query.append(", ");
				}
				else {
					first = false;
				}

				String col = columns[i];
				String type = types[i];

				query.append(col).append(" ").append(type);
			}
			query.append(");");

			// Query is assembled
			Statement statement = c.createStatement();
			statement.executeUpdate(query.toString());
			statement.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Creates a table, if not already existing, of the desired specification.
	 * 
	 * @param tableName The table's name
	 * @param columns The columns of the table's names
	 * @param types The types of the columns
	 */
	public synchronized boolean createTable(String tableName, String[] columns, String[] types, String extra) {
		Connection c = grabConnection();
		StringBuilder query;

		try {
			query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + "(");
			boolean first = true;
			for (int i = 0; i < columns.length && i < types.length; i++) {
				if (!first) {
					query.append(", ");
				}
				else {
					first = false;
				}
				String col = columns[i];
				String type = types[i];
				query.append(col).append(" ").append(type);
			}
			query.append(", ").append(extra);
			query.append(");");

			// Query is assembled
			Statement statement = c.createStatement();
			statement.executeUpdate(query.toString());
			statement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Remove a row from the table
	 * 
	 * @param tableName The table name
	 * @param keyName The name of the table's PRIMARY KEY
	 * @param keyValue The value of the table's PRIMARY KEY at desired row
	 */
	public synchronized boolean deleteFromTable(String tableName, String keyName, String keyValue) {
		Connection c = grabConnection();
		try {
			String query = "DELETE FROM "+tableName+" WHERE "+tableName+"."+keyName+"=?;";
			PreparedStatement placeStatement = c.prepareStatement(query);

			placeStatement.setString(1, keyValue);
			placeStatement.executeUpdate();
			placeStatement.close();

			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Gets a row from the table
	 * 
	 * @param tableName The table to get the row from
	 * @param keyName The SQL table's PRIMARY KEY name
	 * @param keyValue The SQL table's PRIMARY KEY value at the desired row
	 * @param columns The columns you want to return the values for at that row
	 * @return A map containing the column names and their values
	 */
	public synchronized List<Map<Object, Object>> getRows(String tableName, String keyName, String keyValue, String... columns) {
		Connection c = grabConnection();
		try {
			String query = "SELECT * FROM "+tableName+" WHERE "+keyName+"=?;";
			PreparedStatement placeStatement = c.prepareStatement(query);
			placeStatement.setString(1, keyValue);
			ResultSet res = placeStatement.executeQuery();
			List<Map<Object, Object>> list = new ArrayList<>();

			while (res.next()) {
				try {
					Map<Object, Object> obs = new HashMap<>();
					for (String col : columns) {
						try {
							Object o = res.getObject(col);
							obs.put(col, o);
						} catch (Exception ignored) {}
					}
					list.add(obs);
				} catch (Exception ignored) {}
			}

			res.close();
			placeStatement.close();

			return list;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Gets a row from the table
	 * 
	 * @param tableName The table to get the row from
	 * @param keyName The SQL table's PRIMARY KEY name
	 * @param keyValue The SQL table's PRIMARY KEY value at the desired row
	 * @param columns The columns you want to return the values for at that row
	 * @return A map containing the column names and their values
	 */
	public synchronized List<Map<Object, byte[]>> getRowsOfBytes(String tableName, String keyName, byte[] keyValue, String... columns) {
		Connection c = grabConnection();
		try {
			String query = "SELECT * FROM "+tableName+" WHERE "+keyName+"=?;";
			PreparedStatement placeStatement = c.prepareStatement(query);
			placeStatement.setBytes(1, keyValue);
			ResultSet res = placeStatement.executeQuery();
			List<Map<Object, byte[]>> list = new ArrayList<>();

			while (res.next()) {
				try {
					Map<Object, byte[]> obs = new HashMap<>();
					for (String col : columns) {
						try {
							byte[] o = res.getBytes(col);
							obs.put(col, o);
						} catch (Exception ignored) {}
					}
					list.add(obs);
				} catch (Exception ignored) {}
			}

			res.close();
			placeStatement.close();

			return list;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}
	
	/**
	 * Get all values of a column in a table
	 * 
	 * @param tableName The table
	 * @param column The column name
	 * @return A list of the values
	 */
	public synchronized List<Object> getColumn(String tableName, String column) {
		Connection c = grabConnection();
		try {
			Statement statement = c.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM "+tableName+";");
			List<Object> list = new ArrayList<>();

			while (res.next()) {
				try {
					Object o = res.getObject(column);
					list.add(o);
				} catch (Exception ignored) {}
			}

			res.close();
			statement.close();

			return list;
		} catch (SQLException e) {
			return null;
		} finally {
			closeConnection(c);
		}
	}
}