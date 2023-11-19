package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import application.Ticket;

public class TicketDAO extends DBConnection {
	static Connection connection = conn;

	public TicketDAO () {
		if (connection == null) {
			System.exit(1);
		}
	}

	public boolean isDbConnected() {
		return connection != null;
	}
	
	/**
	 * Inserts the desired ticket into the database
	 *
	 * @param t the desired ticket
	 */
	public static void insert(Ticket t) {		// Connect to Database
		PreparedStatement ps = null;
		String name = t.getProjname();				// These lines convert the Ticket
		String title = t.getTitle();				// data into string data for the
		String desc = t.getDescription();			// database to handle
		try {
			String sql = "INSERT INTO tickets(projname,title,description) VALUES(?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, title);
			ps.setString(3, desc);
			ps.executeUpdate();
			System.out.println("Data has been inserted");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Gets the ticket based on the project name and ticket title for specific searching
	 *
	 * @param projN project name
	 * @param name	ticket title
	 * @return		The desired ticket being searched for
	 */
	public static Ticket get(String projN, String name) {	
		String proj = "";
		String title = "";
		String description = "";

		String connectQuery = "SELECT projName, title, description FROM tickets";

		try {
			Statement statement = connection.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("title").equals(name) && queryOutput.getString("projName").equals(projN)) {
					proj = queryOutput.getString("projName");
					title = queryOutput.getString("title");
					description = queryOutput.getString("description");
					break;
				}
			}
		} catch (Exception e) {

		}
		return new Ticket(title, description);
	}
	
	public static ArrayList<Ticket> getAll(String sub) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		String proj = "";
		String title = "";
		String description = "";

		String connectQuery = "SELECT projName, title, description FROM tickets";

		try {
			Statement statement = connection.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("title").toUpperCase().contains(sub.toUpperCase())) {
					proj = queryOutput.getString("projName");
					title = queryOutput.getString("title");
					description = queryOutput.getString("description");
					Ticket tick = new Ticket(title, description);
					tick.setProjname(proj);
					tickets.add(tick);
				}
			}
		} catch (Exception e) {

		}
		return tickets;
	}

	/**
	 * Gets all the comments assigned to the designated ticket
	 *
	 * @param t the requested ticket
	 * @return	the comments of the requested ticket in order of time
	 */
	public static ArrayList<String> getComments(Ticket t) {
		ArrayList<String> commentList = new ArrayList<>();

		String connectQuery = "SELECT tickName, date, description FROM comments";
		try {
			Statement statement = connection.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("tickName").equals(t.getTitle())) {
					String n = queryOutput.getString("date");
					n = n + ": " + queryOutput.getString("description");
					commentList.add(n);
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(commentList);
		return commentList;
	}
	
	public static void delete(String proj, String s) {
		try {
			ArrayList<String> comments = getComments(get(proj, s));
			for (String comment : comments) {
				CommentDAO.delete(comment);
			}
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM tickets WHERE title = ?");
			deleteStatement.setString(1, s);
			deleteStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateProj(String old, String newN, String t) {
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE tickets SET projname = ? WHERE projname = ?");
			statement.setString(1, newN);
			statement.setString(2, old);
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
