package addPatient;

import java.net.URL;
import java.util.ResourceBundle;

import application.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class SuccessController implements Initializable {

	@FXML
	private Button ok;

	@FXML
	private Label pId;

	AddPatientController pc = new AddPatientController();
      
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// pId.setText(pc.getpId());
		System.out.println("Another class-->" );
	}
	
	
	public void okBtn(ActionEvent event) throws Exception {
		ok.getScene().getWindow().hide();
		
		Stage add = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard.fxml"));
		Scene scene = new Scene(root);
		add.setScene(scene);
		add.show();
		DashboardController.refreshTable();
	}

}
