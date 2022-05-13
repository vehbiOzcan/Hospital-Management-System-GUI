package Helper;

import java.sql.*;

public class DbConnection {
	private String userName = "KullanýcýAdýnýz";
	private String password = "Sifreniz";
	private String dbUrl = "jdbc:mysql://localhost:3306/hospital?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	Connection c = null;

	public DbConnection() {

	}

	public Connection connectDb() {
		try {
			this.c = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

}
