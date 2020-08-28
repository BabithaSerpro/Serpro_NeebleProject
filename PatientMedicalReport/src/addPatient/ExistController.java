package addPatient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ExistController {

	@FXML
	private Button ok;

	public void okBtn(ActionEvent event) throws Exception {
		AddPatientController.getPaneNewPatient().getChildren().remove(ok.getParent().getParent());
	}
}
