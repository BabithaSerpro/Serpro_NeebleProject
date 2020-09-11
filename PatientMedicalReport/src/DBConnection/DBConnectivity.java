package DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectivity {

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/drsubodhapp","root","root");
//			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/drsubodhapp","root","root@123");
//			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/drsubodhapp","root","shruthi14");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

