package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import DataAccessLayer.DBConnection;
import DataAccessLayer.TicketDAO;
import application.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TicketController implements Initializable {

	@FXML TextField tickName;
	@FXML ComboBox projCombo;
	@FXML TextArea tickDesc;
	@FXML Button addTick;
	@FXML Button tickCanc;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Connection conn = DBConnection.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> nameList = new ArrayList<>();

		try {
			String sql = "SELECT * FROM projects";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();


			while(rs.next()) {
				nameList.add(rs.getString("name"));
			}
			Collections.sort(nameList);				// Sort the combo box in alphabetical order
			int i = 0;
			while(i < nameList.size()) {
				projCombo.getItems().add(nameList.get(i));
				i++;
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			try {
				rs.close();
				ps.close();
				conn.close();

			} catch (SQLException e2) {
				System.out.println(e2.toString());
			}
		}
	}

	/**
	 * Creates a new ticket object and stores it in the database
	 *
	 * @param event Clicks "Create Ticket"
	 * @throws Exception
	 */
	public void createTick (ActionEvent event) throws Exception {
		String tickNm = tickName.getText();
		String tickDsc = tickDesc.getText();
		String projNm = (String)projCombo.getValue();

		Ticket added = new Ticket(tickNm, tickDsc);
		added.setProjname(projNm);

		TicketDAO.insert(added);

		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * Closes the create project screen
	 *
	 * @param event Clicks "cancel"
	 */
	@FXML public void cancTick(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

}
