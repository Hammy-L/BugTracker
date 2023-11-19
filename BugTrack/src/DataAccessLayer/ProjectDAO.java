package DataAccessLayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Project;

public class ProjectDAO extends DBConnection{
	private static Connection connection = conn;

	/**
	 *  Connects the DAO to the DB
	 */
	public ProjectDAO () {
		if (connection == null) {
			System.exit(1);
		}
	}

	/**
	 * Checks DB connection
	 * @return Boolean for db connection
	 */
	public boolean isDbConnected() {
		return connection != null;
	}

	/**
	 * Inserts the Project object into the projects table in the database
	 * @param p the Project being inserted
	 */
	public static void insert(Project p) {// Connection to the DB
		PreparedStatement ps = null;
		String name = p.getName();					// 	Splits the object into
		String date = p.getDate();					// 	Strings for the database
		String desc = p.getDesc();					//	To handle
		try {
			String sql = "INSERT INTO projects(name,date,description) VALUES(?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, date);
			ps.setString(3, desc);
 			ps.executeUpdate();
			System.out.println("Data has been inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets all the tickets associated with a project utilizing a linear Search (need to figure out
	 * a way to assign tickets to the projects in the database for O(1) time if we want)
	 *
	 * @param name The name of the project that the tickets are in
	 * @return	The array list of associated tickets
	 */
	public static ArrayList<String> getTickets(String name) {
		ArrayList<String> tickList = new ArrayList<>();

		String connectQuery = "SELECT projname, title FROM tickets";

		try {
			Statement statement = connection.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("projName").equals(name)) {
					String n = queryOutput.getString("title");
					tickList.add(n);
				}
			}
		} catch (Exception e) {

		}
		return tickList;
	}

	public static Project get(String name) {
		String nm = "";
		String date = "";
		String description = "";

		String connectQuery = "SELECT name, date, description FROM projects";

		try {
			Statement statement = connection.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("name").equals(name)) {
					nm = queryOutput.getString("name");
					date = queryOutput.getString("date");
					description = queryOutput.getString("description");
					break;
				}
			}
		} catch (Exception e) {

		}
		return new Project(nm, date, description);
	}
	
	public static ArrayList<Project> getAll(String name) {
		ArrayList<Project> projects = new ArrayList<Project>();
		String nm = "";
		String date = "";
		String description = "";

		String connectQuery = "SELECT name, date, description FROM projects";
		

		try {
			Statement statement = connection.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("name").toUpperCase().contains(name.toUpperCase())) {
					nm = queryOutput.getString("name");
					date = queryOutput.getString("date");
					description = queryOutput.getString("description");
					projects.add(new Project(nm, date, description));
				}
			}
		} catch (Exception e) {

		}
		return projects;
	}
	
	public static void delete(String proj) {
		try {
			ArrayList<String> tickets = getTickets(proj);
			for (String ticket : tickets) {
				CommentDAO.delete(ticket);
				TicketDAO.delete(proj, ticket);
			}
			
			PreparedStatement statement = connection.prepareStatement("DELETE FROM projects WHERE name = ?");
			statement.setString(1, proj);
			statement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update(String old, String newN, String newdate, String newdescription) {
		try {
			for (String tick : getTickets(old)) {
				TicketDAO.updateProj(old, newN, tick);
			}
			PreparedStatement statement = connection.prepareStatement("UPDATE projects SET name = ? , date = ? , description = ? "
					+ "WHERE name = ?");
			statement.setString(1, newN);
			statement.setString(2, newdate);
			statement.setString(3, newdescription);
			statement.setString(4, old);
			statement.execute();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		DBConnection.close();
	}
}
