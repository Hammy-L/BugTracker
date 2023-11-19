package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectDAO;
import application.Project;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class EditProjController implements Initializable {

	@FXML Button editProj;
	@FXML Button projCanc;
	@FXML TextArea descArea;
	@FXML DatePicker dateField;
	@FXML TextField nameField;
	@FXML VBox ProjBox;
	Project proj;
	
	static LocalDate now = LocalDate.now();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}
	
	public void setProjData(String s) {
		proj = ProjectDAO.get(s);
		dateField.setValue(now);
		dateField.setPromptText(now.toString());
		nameField.setText(proj.getName());
		descArea.setText(proj.getDesc());
	}

	@FXML public void edit(ActionEvent event) throws IOException {
		ProjectDAO.update(proj.getName(), nameField.getText(), dateField.getValue().toString(),
				descArea.getText());
		
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML public void cancProj(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

}
