package screens;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.ResourceBundle;

import DBConnection.DBConnectivity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class HomePageController implements Initializable {

	@FXML
	private AnchorPane pane_profile;
	
	@FXML
	private Text txt_Wshes;

	@FXML
	private Text txt_totalTest;

	@FXML
	private Text txt_completedTest;

	@FXML
	private Text txt_pendingTest;

	private static Connection connection;

	private static PreparedStatement ps;
	private static ResultSet rs;
	private static AnchorPane paneProfile;
	public static AnchorPane getPaneProfile() {
		return paneProfile;
	}

	public static void setPaneProfile(AnchorPane paneProfile) {
		HomePageController.paneProfile = paneProfile;
	}

	private static Text totalTest, completedTest, pendingTest;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneProfile=pane_profile;
		totalTest = txt_totalTest;
		completedTest = txt_completedTest;
		pendingTest = txt_pendingTest;
		wishes();

		try {
			totalTestCount();
			totalTestPending();
			totalTestCompleted();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public static void totalTestCount() throws Exception {
		String totalCount = "select COUNT(*) AS totalTest from patient_reportmasterdata WHERE active='Y'";
		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement(totalCount);
		rs = ps.executeQuery();
		if (rs.next()) {
			int total = rs.getInt("totalTest");
			totalTest.setText(String.valueOf(total));
		}
	}

	public static void totalTestPending() throws Exception {
		String totalCount = "select COUNT(*) AS testPendingCount from patient_reportmasterdata p WHERE p.reportDate IS NULL AND active='Y'";
		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement(totalCount);
		rs = ps.executeQuery();
		if (rs.next()) {
			int total = rs.getInt("testPendingCount");
			pendingTest.setText(String.valueOf(total));
		}
	}

	public static void totalTestCompleted() throws Exception {
		String totalCount = "select COUNT(*) AS testCompletedCount from patient_reportmasterdata p WHERE p.reportDate IS NOT NULL AND active='Y'";
		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement(totalCount);
		rs = ps.executeQuery();
		if (rs.next()) {
			int total = rs.getInt("testCompletedCount");
			completedTest.setText(String.valueOf(total));
		}
	}
}
