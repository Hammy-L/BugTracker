package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import DataAccessLayer.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML VBox MainBox;
	@FXML VBox ProjBox;
	@FXML
	ListView<String> projList;
	@FXML Button addProj;
	@FXML Button addTick;
	@FXML Button refreshBtn;
	@FXML Button getInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refreshList();
		getInfo.setDisable(true);
	}



	/**
	 * Opens a new window for the user to input the required
	 * fields for creating a new project.
	 *
	 */
	@FXML public void showCrprojOp() {

		URL url = getClass().getClassLoader().getResource("view/crproj.fxml");

		try {
			Scene show = new Scene(FXMLLoader.load(url));
			Stage stage = new Stage();
			stage.setScene(show);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the project list
	 *
	 */
	@FXML public void refreshList() {
		getInfo.setDisable(true);
		projList.getItems().clear();
		Connection connect = DBConnection.connect();

		String connectQuery = "SELECT name FROM projects";

		try {
			Statement statement = connect.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				String name = queryOutput.getString("name");
				String listOut = name;

				projList.getItems().add(listOut);
			}

		connect.close();
		} catch (Exception e) {

		}
	}


/**
 * Opens the create ticket screen for the user to input a ticket
 *
 * @param event User clicks create ticket
 */
	@FXML public void showCrTickOp(ActionEvent event) {
		URL url = getClass().getClassLoader().getResource("view/crticket.fxml");

		try {
			Scene show = new Scene(FXMLLoader.load(url));
			Stage stage = new Stage();
			stage.setScene(show);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


/**
 * The program collects the data from the project in the database, and presents it to the
 * user through the "Project Info" screen.
 *
 * @param event User clicks the "get info" button
 * @throws IOException
 */
	@FXML public void getInfo(ActionEvent event) throws IOException {
		int pos = projList.getSelectionModel().getSelectedIndex();
		String name = projList.getItems().get(pos);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/info.fxml"));
        Parent root = loader.load();



		System.out.println("Got Info of " + name + "!");
		InfoController infoControl = loader.getController();
		infoControl.setProjData(name);

		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.show();


	}


/**
 * When an item in the Project List is selected, the "Get Info" button is accessible
 * @param event User clicks an item in the project list
 */
	@FXML public void itemSelected(MouseEvent event) {
		if (!(projList.getSelectionModel().getSelectedItem() == null)) {
			getInfo.setDisable(false);
		}
		else {
			getInfo.setDisable(true);
		}
	}

}
