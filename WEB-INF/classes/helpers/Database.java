package helpers;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
	// DB variables
	private static String driver = "com.ibm.db2.jcc.DB2Driver";
	private static String username = "srthom28";
	private static String password = "Spring2021";
	private static String url = "jdbc:db2://db2.cecsresearch.org:50000/";
	private static String database = "SRTHOM28";
	
	// Connection pool variables
	private static boolean pool = false;
	private static DataSource source = null;
	
	// Connect to pool instance
	public static Connection connectToPool() {
		Connection connection = null;
		
		try {
			// Create new pool if needed
			if (source == null) {
				Context context = (Context) new InitialContext().lookup("java:/comp/env"); 
				source = (DataSource) context.lookup("jdbc/" + database);
			}
			
			// Get connection from pool
			connection = source.getConnection();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	// New connection instance
	public static Connection connect() {
    	// Try to instantiate driver
    	try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	// Try to establish connection
    	try {
			return DriverManager.getConnection(url + database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	// Return null if driver/connection failed
    	return null;
    }

	public static void disconnect(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet query(Connection connection, String query) {
		try {
			return connection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void update(Connection connection, String query) {
		try {
			connection.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(Connection connection, String query) {
		try {
			connection.prepareStatement(query).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
