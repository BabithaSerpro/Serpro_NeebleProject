package screens;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class HomePageController implements Initializable {

	@FXML
	private Text txt_Wshes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wishes();
	}
	
	public void wishes() {
		Calendar cal = Calendar.getInstance();

		int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

		if (timeOfDay >= 0 && timeOfDay < 12) {
			txt_Wshes.setText("Good Morning!");
		} else if (timeOfDay >= 12 && timeOfDay < 16) {
			txt_Wshes.setText("Good Afternoon!");
		} else if (timeOfDay >= 16 && timeOfDay < 21) {
			txt_Wshes.setText("Good Evening!");
		} else if (timeOfDay >= 21 && timeOfDay < 24) {
			txt_Wshes.setText("Good Night!");
		}
	}
}
