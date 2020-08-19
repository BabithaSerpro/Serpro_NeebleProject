package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBClass {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/drsubodhapp", "root", "Babitha@123");
	}
}
