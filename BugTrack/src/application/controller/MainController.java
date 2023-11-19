package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DataAccessLayer.DBConnection;
import DataAccessLayer.ProjectDAO;
import DataAccessLayer.TicketDAO;
import application.Project;
import application.Ticket;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class MainController implements Initializable {

	@FXML VBox MainBox;
	@FXML VBox ProjBox;
	@FXML
	ListView<String> projList;
	@FXML Button addProj;
	@FXML Button addTick;
	@FXML Button refreshBtn;
	@FXML Button getInfo;
	@FXML ToggleButton tickToggle;
	@FXML ToggleButton projToggle;
	@FXML TextField searchBar;
	@FXML Button searchBtn;
	@FXML ListView<String> searchList;
	@FXML Label searchLabel;
	@FXML Button editBtn;
	@FXML Button deleteBtn;
	@FXML Button searchInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBConnection.connect();
		refreshList();
		getInfo.setDisable(true);
		searchList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(final ListView<String> list) {
                return new ListCell<String>() {
                    {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(searchList.widthProperty().subtract(20));
                        text.textProperty().bind(itemProperty());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                };
            }
        });
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
		Connection connect = DBConnection.conn();

		String connectQuery = "SELECT name FROM projects";

		try {
			Statement statement = connect.createStatement();
			ResultSet queryOutput = statement.executeQuery(connectQuery);

			while (queryOutput.next()) {
				String name = queryOutput.getString("name");
				String listOut = name;

				projList.getItems().add(listOut);
			}
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
		String name;
		int pos = projList.getSelectionModel().getSelectedIndex();
		name = projList.getItems().get(pos);
		

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
		if (projList.getSelectionModel().getSelectedItem() != null) {
			getInfo.setDisable(false);
		}
		else {
			getInfo.setDisable(true);
		}
	}


/**
 * Searches and return items containing a substring into the searchList
 */
	@FXML public void search(ActionEvent event) {
		searchInfo.setDisable(true);
		deleteBtn.setDisable(true);
		editBtn.setDisable(true);
		
		searchList.getItems().clear();
		ArrayList<Project> projects = new ArrayList<Project>();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		if (!(projToggle.isSelected() || tickToggle.isSelected())) {
			searchLabel.setText("Select an item category to search for...");
		}
		else if (searchBar.getText().isEmpty()) {
			searchLabel.setText("Field is empty...");
		}
		else {
			if (projToggle.isSelected()) {
				projects = ProjectDAO.getAll(searchBar.getText());
				for (Project proj : projects)
					searchList.getItems().addAll("(Project: " + proj.getName() + ")");
			}
			if (tickToggle.isSelected()) {
				tickets = TicketDAO.getAll(searchBar.getText());
				for (Ticket tick : tickets)
					searchList.getItems().addAll("(Project: " + tick.getProjname() + ") Ticket: " + tick.getTitle());
			}
			
			if (searchList.getItems().isEmpty()) {
				searchLabel.setText("No items found...");
			}
			else {
				searchLabel.setText("Found!");
			}
		}
		
		
	}



	@FXML public void edit(ActionEvent event) throws IOException {
		int pos = searchList.getSelectionModel().getSelectedIndex();
		String name = searchList.getItems().get(pos);
		
		if (name.contains("Ticket: ")) {
			searchLabel.setText("Sorry! Ticket editing not implemented yet!");
			return;
		}
		
		name = name.substring(name.indexOf(" ") + 1, name.indexOf(")"));
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/editProj.fxml"));
        Parent root = loader.load();



		System.out.println("Got Info of " + name + "!");
		EditProjController editControl = loader.getController();
		editControl.setProjData(name);

		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.show();
	}
	
	
	
	@FXML public void delete(ActionEvent event) {
		if (searchList.getSelectionModel().getSelectedItem() != null) {
			String str = searchList.getSelectionModel().getSelectedItem();
			if (str.contains("Ticket: ")) {
				searchLabel.setText("Sorry! Ticket Deletion not implemented");
			}
			else {
				
				ProjectDAO.delete(str.substring(str.indexOf(" ") + 1, str.indexOf(")")));
				refreshList();
				search(event);
			}
		}
	}
	
	
	
	@FXML public void searchInfo(ActionEvent event) throws IOException {
		if (searchList.getSelectionModel().getSelectedItem() != null) {
			String str = searchList.getSelectionModel().getSelectedItem();
			if (str.contains("Ticket: ")) {
				searchLabel.setText("Sorry! Ticket information is not implemented");
			}
			else {
				int pos = searchList.getSelectionModel().getSelectedIndex();
				str = searchList.getItems().get(pos);
				str = str.substring(str.indexOf(" ") + 1, str.indexOf(")"));
				
				
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/info.fxml"));
		        Parent root = loader.load();

				System.out.println("Got Info of " + str + "!");
				InfoController infoControl = loader.getController();
				infoControl.setProjData(str);

				Scene scene = new Scene(root);
				Stage stage = new Stage();

				stage.setScene(scene);
				stage.show();
			}
		}
		
	}



	@FXML public void searchSelected(MouseEvent event) {
		if (searchList.getSelectionModel().getSelectedItem() != null) {
			searchInfo.setDisable(false);
			deleteBtn.setDisable(false);
			editBtn.setDisable(false);
		}
		else {
			searchInfo.setDisable(true);
			deleteBtn.setDisable(true);
			editBtn.setDisable(true);
		}
	}

}
