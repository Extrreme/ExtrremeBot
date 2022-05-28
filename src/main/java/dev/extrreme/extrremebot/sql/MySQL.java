package dev.extrreme.extrremebot.sql;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import dev.extrreme.extrremebot.Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * Represents a connection to an SQL database
 *
 */
public class MySQL {
	private String url;
	private String user;

	BoneCP connectionPool = null;
	
	public static String genURL(String host, String database){
		return "jdbc:mysql://"+host+"/"+database;
	}
	
	/**
	 * Use for SQL connections
	 * 
	 * @param url The JDBC url for the database
	 * @param user The username
	 * @param password The password
	 */
	public MySQL(String url, String user, String password){
		this.url = url;
		this.user = user;

		try {
			Main.log("Initializing SQL connection pool...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			// setup the connection pool
			
			Properties properties = new Properties();
			properties.setProperty("characterEncoding", "UTF-8");
			properties.setProperty("useUnicode", "true");
			BoneCPConfig config;
			try {
				config = new BoneCPConfig(properties);
			} catch (Exception e) {
				e.printStackTrace();
				config = new BoneCPConfig();
			}
			config.setJdbcUrl(url); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername(user); 
			config.setPassword(password);
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			config.setDisableConnectionTracking(true);
			connectionPool = new BoneCP(config); // setup the connection pool
			Connection testCon = connectionPool.getConnection();
			if(testCon != null){
				testCon.close();
			}
			else {
				Main.log("Error connecting to SQL database, please check"
						+ " your credentials and try again!");
			}
			Main.log("SQL connection pool initialized!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Main.log("JDBC SQL driver not found! Please find the"
					+ " version required for your OS and put it into the '/libs' folder!");
		} catch (SQLException e) {
			e.printStackTrace();
			Main.log("Error connecting to SQL database, please check"
					+ " your credentials and try again!");
		}
	}
	
	public void shutdownConnections(){
		connectionPool.shutdown();
	}
	
	/**
	 * Get the sql connection
	 * @return The sql connection
	 */
	public Connection getConnection(){
		try {
			return connectionPool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getURL() {
		return this.url;
	}

	public String getUser() {
		return this.user;
	}
}
