package addNewTest;

import java.io.IOException;

import application.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class AddnewTestController {
	@FXML
	private AnchorPane pane_addTest;

	@FXML
	private AnchorPane sc_pane;

	@FXML
	private AnchorPane pane_note;

	@FXML
	private TextField testName;

	@FXML
	private VBox newTestVbox;

	@FXML
	private HTMLEditor phistory;

	@FXML
	private HTMLEditor tDesc;

	@FXML
	private HTMLEditor impression;

	@FXML
	private HTMLEditor note;

	@FXML
	private Button saveBtn;

	@FXML
	private Button btn_Close;
	
	

	public void cancelBtn(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Dr Subodh App");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		alert.getDialogPane().setHeaderText("Are you Sure!! You want to Close this Page!!");
		alert.showAndWait().ifPresent(bt -> {
			if (bt == ButtonType.YES) {
				close();
			} else if (bt == ButtonType.NO) {
				event.consume();
			}
		});
	}

	public static void close() {
		AnchorPane pane = MainScreenController.getHomePage();
		for (int i = 0; i < pane.getChildren().size(); i++) {
			String paneID = pane.getChildren().get(i).getId();
			switch (paneID) {
			case "pane_Dashboard":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
				break;
			case "pane_viewDetails":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_addTest":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			}
		}
	}

}
