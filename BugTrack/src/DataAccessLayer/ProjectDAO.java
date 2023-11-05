package DataAccessLayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import application.Project;

public class ProjectDAO {
	Connection connection;

	/**
	 *  Connects the DAO to the DB
	 */
	public ProjectDAO () {
		connection = DBConnection.connect();
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
	public static void insert(Project p) {
		Connection con = DBConnection.connect();	// Connection to the DB
		PreparedStatement ps = null;
		String name = p.getName();					// 	Splits the object into
		String date = p.getDate();					// 	Strings for the database
		String desc = p.getDesc();					//	To handle
		try {
			String sql = "INSERT INTO projects(name,date,description) VALUES(?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, date);
			ps.setString(3, desc);
			ps.executeUpdate();
			System.out.println("Data has been inserted");
		} catch (Exception e) {
			System.out.println(e);
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
		Connection connect = DBConnection.connect();

		String connectQuery = "SELECT projname, title FROM tickets";

		try {
			Statement statement = connect.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("projName").equals(name)) {
					String n = queryOutput.getString("title");
					tickList.add(n);
				}
			}

		connect.close();
		} catch (Exception e) {

		}
		return tickList;
	}

	public static Project get(String name) {
		Connection connect = DBConnection.connect();
		String nm = "";
		String date = "";
		String description = "";

		String connectQuery = "SELECT name, date, description FROM projects";

		try {
			Statement statement = connect.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				if (queryOutput.getString("name").equals(name)) {
					nm = queryOutput.getString("name");
					date = queryOutput.getString("date");
					description = queryOutput.getString("description");
					break;
				}
			}
		connect.close();
		} catch (Exception e) {

		}
		return new Project(nm, date, description);
	}

	public static void close() {
		DBConnection.close();
	}
}
