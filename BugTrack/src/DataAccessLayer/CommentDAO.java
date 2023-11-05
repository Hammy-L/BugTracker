package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;

import application.Comment;

public class CommentDAO {
	Connection connection;

	public CommentDAO () {
		connection = DBConnection.connect();
		if (connection == null) {
			System.exit(1);
		}
	}

	public boolean isDbConnected() {
		return connection != null;
	}

	public static void insert(Comment c) {
		Connection con = DBConnection.connect();	// Connect to Database
		PreparedStatement ps = null;
		String tickName = c.getTickName();
		String date = c.getDate();					// These lines convert the Ticket
		String description = c.getDescription();	// data into string data for the database to handle
		try {
			String sql = "INSERT INTO comments(tickName, date, description) VALUES(?, ?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, tickName);
			ps.setString(2, date);
			ps.setString(3, description);
			ps.executeUpdate();
			System.out.println("Data has been inserted");
		} catch (Exception e) {
			System.out.println(e);
		}
		close();

	}

	public static void close() {
		DBConnection.close();
	}

}
