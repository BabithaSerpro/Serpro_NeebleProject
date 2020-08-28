package screens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TitleBarController {

	@FXML
	private Button btn_minimize;
	
	@FXML
	private Button btn_maximize;
	
	@FXML
	private Button btn_close;
	
	
	@FXML
	private void minimizeScreen() {
		((Stage) btn_minimize.getScene().getWindow()).setIconified(true);
	}
	
	@FXML
	private void maximizeScreen() {
//		((Stage) btn_maximize.getScene().getWindow()).setMaximized(true);
	}
	
	@FXML
	private void closeScreen() {
		((Stage) btn_close.getScene().getWindow()).close();
	}
	
}
