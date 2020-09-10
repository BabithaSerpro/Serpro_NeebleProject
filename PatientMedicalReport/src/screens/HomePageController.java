package screens;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ResourceBundle;

import DBConnection.DBConnectivity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class HomePageController implements Initializable {

	@FXML
	private Text txt_Wshes;

	@FXML
	private Text txt_totalTest;

	@FXML
	private Text txt_completedTest;

	@FXML
	private Text txt_pendingTest;

	private Connection connection;

	private PreparedStatement ps;

	private ResultSet rs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wishes();

		try {
			totalTestCount();
			totalTestPending();
			totalTestCompleted();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public void totalTestCount() throws Exception {

		String totalCount = "select COUNT(*) AS totalTest from patient_reportmasterdata";
		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement(totalCount);
		rs = ps.executeQuery();

		if (rs.next()) {
			int total = rs.getInt("totalTest");
			txt_totalTest.setText(String.valueOf(total));
		}
	}

	public void totalTestPending() throws Exception {

		String totalCount = "select COUNT(*) AS testPendingCount from patient_reportmasterdata p WHERE p.reportDate IS NULL";
		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement(totalCount);
		rs = ps.executeQuery();
		if (rs.next()) {
			int total = rs.getInt("testPendingCount");
			txt_pendingTest.setText(String.valueOf(total));
		}
	}

	public void totalTestCompleted() throws Exception {

		String totalCount = "select COUNT(*) AS testCompletedCount from patient_reportmasterdata p WHERE p.reportDate IS NOT NULL";
		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement(totalCount);
		rs = ps.executeQuery();

		if (rs.next()) {
			int total = rs.getInt("testCompletedCount");
			txt_completedTest.setText(String.valueOf(total));
		}
	}
}
