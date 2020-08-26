package addPatient;

import application.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ValidController {

	@FXML
	private Button ok;

	public void okBtn(ActionEvent event) throws Exception {
		ok.getScene().getWindow().hide();
		DashboardController.refreshTable();
		Stage add = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard.fxml"));
		Scene scene = new Scene(root);
		add.setScene(scene);
		add.show();
	}
}
