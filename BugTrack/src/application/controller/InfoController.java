package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectDAO;
import DataAccessLayer.TicketDAO;
import application.Project;
import application.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class InfoController implements Initializable {

	@FXML
	Label namHolder;
	@FXML
	Label dateHolder;
	@FXML
	TextArea descHolder;
	@FXML ListView<String> tickList;
	@FXML Label tickNam;
	@FXML Button doneBtn;
	@FXML TextArea tickDesc;
	@FXML ListView<String> commList;
	@FXML Button addComm;
	private String projectName;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addComm.setDisable(true);
		descHolder.setWrapText(true);
		tickDesc.setWrapText(true);

		commList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(final ListView<String> list) {
                return new ListCell<String>() {
                    {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(commList.widthProperty().subtract(20));
                        text.textProperty().bind(itemProperty());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                };
            }
        });
	}

	/**
	 * Initializes the labels of the page
	 *
	 * @param name	The name of the project being requested
	 */
	public void setProjData(String name) {
		projectName = name;
		namHolder.setText(name);
		Project found = ProjectDAO.get(name);
		dateHolder.setText(found.getDate());
		descHolder.setText(found.getDesc());
		tickList.getItems().addAll(ProjectDAO.getTickets(name));					// Adds all the tickets associated with the project to the Ticklist
	}

	/**
	 * Returns the user back to the home page
	 *
	 * @param event User clicks done
	 */
	@FXML public void goHome(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML public void selectComm() {
		//TODO make a functionality for selecting comments when the project gets
		// to that point
	}

	/**
	 * User selects a ticket, allowing them to access the information of the ticket
	 * and the comments within the ticket
	 *
	 */
	@FXML public void selectTick() {
		if (tickList.getSelectionModel().getSelectedItem() != null) {
			String name = tickList.getSelectionModel().getSelectedItem();
			Ticket selected = TicketDAO.get(projectName, name);
			commList.getItems().setAll(TicketDAO.getComments(selected));
			tickNam.setText(selected.getTitle());
			tickDesc.setText(selected.getDescription());
			addComm.setDisable(false);
		}
		else {
			addComm.setDisable(true);
		}
	}

	/**
	 * Opens the add comment screen for the designated ticket being selected.
	 *
	 * @throws IOException
	 */
	@FXML public void addComment() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/comment.fxml"));
        Parent root = loader.load();

		CommentController commentControl = loader.getController();
		commentControl.setTickName(tickList.getSelectionModel().getSelectedItem());

		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.show();
	}




}
