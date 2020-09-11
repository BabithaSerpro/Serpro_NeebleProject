package addPatient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DashboardController;
import application.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import viewPatient.ViewPDController;

public class updateSuccessController implements Initializable {

	@FXML
	private Button ok;
	
	private int pId = EditPatientController.getPid();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void okBtn(ActionEvent event) throws Exception {
		EditPatientController.getPaneEditPatient().getChildren().remove(ok.getParent().getParent());
		DashboardController.refreshTable();
		ViewPDController.refreshViewDetails(pId);
		
		AnchorPane pane=MainScreenController.getHomePage();
		for(int i=0;i<pane.getChildren().size();i++) {
			String paneID=pane.getChildren().get(i).getId();
			switch (paneID) {
				case "pane_Dashboard":
					MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
					break;
				case "pane_editPatient":
					MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
					break;
				case "pane_viewDetails":
					MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
					break;
				case "pane_newPatient":
					MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
					break;
			}
		}
	}

}
