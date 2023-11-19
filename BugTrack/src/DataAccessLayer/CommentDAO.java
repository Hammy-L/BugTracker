package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Comment;

public class CommentDAO extends DBConnection {
	private static Connection connection = conn;

	public CommentDAO () {
		if (connection == null) {
			System.exit(1);
		}
	}

	public boolean isDbConnected() {
		return connection != null;
	}

	public static void insert(Comment c) {				// Connect to Database
		PreparedStatement ps = null;
		String tickName = c.getTickName();
		String date = c.getDate();					// These lines convert the Ticket
		String description = c.getDescription();	// data into string data for the database to handle
		try {
			String sql = "INSERT INTO comments(tickName, date, description) VALUES(?, ?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, tickName);
			ps.setString(2, date);
			ps.setString(3, description);
			ps.executeUpdate();
			System.out.println("Data has been inserted");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void delete(String s) {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE tickName = ?");
			statement.setString(1, s);
			statement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update(String old, String newT) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("UPDATE comments SET tickName ? WHERE tickName = old");
			statement.setString(1, newT);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void close() {
		DBConnection.close();
	}

}
