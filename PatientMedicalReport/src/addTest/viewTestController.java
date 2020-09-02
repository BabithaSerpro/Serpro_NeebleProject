package addTest;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DBConnection.DBConnectivity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class viewTestController implements Initializable {

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

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		int testId = addTestController.getpTId();
		System.out.println("---------->" + testId);
		try {
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement(
					"SELECT pm.patient_name,pm.age,pm.gender,pr.ref_doctor,pr.regNumber,pr.testDate,pr.reportDate,pr.patientHistory,pr.testName,\r\n"
							+ "pr.testDescription,pr.impression,pr.note\r\n"
							+ "FROM patient_masterdata pm, patient_reportmasterdata pr WHERE\r\n"
							+ "pm.patient_id= pr.regNumber AND pr.id=? AND pm.active='Y'");
			ps.setInt(1, testId);
			rs = ps.executeQuery();
			if (rs.next()) {
				pname.setText(rs.getString(1));
				pAge.setText(rs.getString(2));
				pGender.setText(rs.getString(3));
				refDoc.setText(rs.getString(4));
				pId.setText(String.valueOf(rs.getInt(5)));
				testDate.setText(rs.getString(6));
				reportDate.setText(rs.getString(7));
				pHistory.setText(rs.getString(8));
				testName.setText(rs.getString(9));
				testObservation.setText(rs.getString(10));
				impression.setText(rs.getString(11));
				note.setText(rs.getString(12));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
