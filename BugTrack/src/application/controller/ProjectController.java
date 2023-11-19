package application.controller;

import java.io.IOException;
import java.time.LocalDate;

import DataAccessLayer.ProjectDAO;
import application.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ProjectController {
	@FXML VBox MainBox;
	@FXML VBox ProjBox;
	@FXML Button addProj;
	@FXML Button projCanc;
	@FXML TextField nameField;
	@FXML DatePicker dateField;
	@FXML TextArea descArea;

	public ProjectDAO projDAO = new ProjectDAO();
	static LocalDate now = LocalDate.now();
	@FXML Label connectionLabel;



	@FXML
	public void initialize() {
		dateField.setValue(now);
		dateField.setPromptText(now.toString());
	}
/**
 * Creates a project using the parameters required in the window
 *
 * @param event
 * @throws IOException
 */
	@FXML public void createProj(ActionEvent event) throws IOException {
		String name = nameField.getText();
		String date = dateField.getValue().toString();
		String description = descArea.getText();
		Project proj = new Project(name, date, description);

		ProjectDAO.insert(proj);

		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * Closes the create project screen
	 *
	 * @param event the current action undertaken by the user
	 */
	@FXML public void cancProj(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}
}
