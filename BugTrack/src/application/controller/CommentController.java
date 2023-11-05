package application.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import DataAccessLayer.CommentDAO;
import application.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CommentController implements Initializable{

	@FXML TextArea commentDesc;
	@FXML Label dateLbl;
	@FXML Button crCommBtn;
	@FXML Button cancelBtn;
	private String ticketName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"
		dateLbl.setText(formattedDateTime);
	}

	/**
	 * Creates a comment utilizing the CommentDAO to insert comments into the table
	 *
	 * @param event User clicks create
	 */
	@FXML public void createComment(ActionEvent event) {
		String date = dateLbl.getText();
		String description = commentDesc.getText();
		Comment insert = new Comment(description, date);
		insert.setTickName(ticketName);

		CommentDAO.insert(insert);					// CommentDAO inserts the object into the Database

		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * Sets the ticket name for use when creating a comment
	 *
	 * @param s The ticket the comment is being added to
	 */
	public void setTickName(String s) {
		ticketName = s;
	}

	/**
	 * Returns the user back to the info screen
	 *
	 * @param event user clicks cancel
	 */
	@FXML public void backToInfo(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}


}
