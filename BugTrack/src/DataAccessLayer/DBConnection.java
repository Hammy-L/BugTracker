package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static Connection conn;

	public static void connect() {
        try {
        	Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:projects.db");
    		System.out.println( "Connected database successfully" );
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
	}

	public static void close() {
		try {
			conn.close();
			System.out.println("Closed successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection conn() {
		return conn;
	}
}
