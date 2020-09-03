package addTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DBConnection.DBConnectivity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class viewReportController {

	@FXML
	private Label pname;

	@FXML
	private Label pAge;

	@FXML
	private Label pGender;

	@FXML
	private Label refDoc;

	@FXML
	private Label pId;

	@FXML
	private Label testDate;

	@FXML
	private Label reportDate;

	
	@FXML
	private Label pHistory;

	@FXML
	private Label testObservation;

	@FXML
	private Label impression;

	@FXML
	private Label note;

	@FXML
	private Label testName;

	private static Label p_name,p_age,p_gender,ref_doctor,p_id,test_date,report_date,p_history,test_observation,p_impression,p_note,test_name;
	private static Connection con;
	private static PreparedStatement ps;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		p_name=pname;
		p_age=pAge;
		p_gender=pGender;
		ref_doctor=refDoc;
		p_id=pId;
		test_date=testDate;
		report_date=reportDate;
		p_history=pHistory;
		test_observation=testObservation;
		p_impression=impression;
		p_note=note;
		test_name=testName;
		int testId = addTestController.getpTId();
		try {
			con = DBConnectivity.getConnection();
			viewReport(testId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void viewReport(int testId) {
		try {
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement(
					"SELECT pm.patient_name,pm.age,pm.gender,pr.ref_doctor,pr.regNumber,pr.testDate,pr.reportDate,pr.patientHistory,pr.testName,\r\n"
							+ "pr.testDescription,pr.impression,pr.note\r\n"
							+ "FROM patient_masterdata pm, patient_reportmasterdata pr WHERE\r\n"
							+ "pm.patient_id= pr.regNumber AND pr.id=? AND pm.active='Y'");
			ps.setInt(1, testId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p_name.setText(rs.getString(1));
				p_age.setText(rs.getString(2));
				p_gender.setText(rs.getString(3));
				ref_doctor.setText(rs.getString(4));
				p_id.setText(String.valueOf(rs.getInt(5)));
				test_date.setText(rs.getString(6));
				report_date.setText(rs.getString(7));
				p_history.setText(rs.getString(8));
				test_name.setText(rs.getString(9));
				test_observation.setText(rs.getString(10));
				p_impression.setText(rs.getString(11));
				p_note.setText(rs.getString(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
