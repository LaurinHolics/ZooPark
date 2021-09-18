package ro.itschool.curs.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class ConnectionFactory {

	private static final String URL = "jdbc:mysql://localhost/zoopark";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection getConnection() {
		try {
//			DriverManager.registerDriver(new Driver());
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException("Error connecting to the database", e);
		}

	}

}
