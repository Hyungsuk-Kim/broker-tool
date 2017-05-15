package trader.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private Properties connectionProps;
	private String dbms;
	private String serverName;
	private String portNumber;
	private String dbName;
	private String user;
	private String password;
	
	public ConnectionManager() {
		try {
			connectionProps = new Properties();
			connectionProps.load(new FileInputStream("db.properties"));
			dbms = connectionProps.getProperty("dbms");
			serverName = connectionProps.getProperty("serverName");
			portNumber = connectionProps.getProperty("portNumber");
			dbName = connectionProps.getProperty("dbName");
			user = connectionProps.getProperty("user");
			password = connectionProps.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		String url = null;
		Connection conn = null;
		
		if (dbms.equals("mysql") || dbms.equals("derby")) {
			url = "jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/" + dbName;
			
		} else if (dbms.equals("oracle")) {
			url = "jdbc:" + dbms + ":thin:@" + serverName + ":" + portNumber + ":" + dbName;
		}
		
		conn = DriverManager.getConnection(url, user, password);
		//conn = DriverManager.getConnection(url, connectionProps);
		System.out.println("Connected to " + dbms + " database");
		
		return conn;
	}
}
