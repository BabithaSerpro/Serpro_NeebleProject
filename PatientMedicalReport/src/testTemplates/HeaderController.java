package testTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DBConnection.DBConnectivity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import viewPatient.ViewPDController;

public class HeaderController {

	@FXML
	private AnchorPane pane_header;

	@FXML
	private AnchorPane pane_template;

	@FXML
    private ScrollPane sPane;
	
	@FXML
	private Label pname;

	@FXML
	private Label pAge;

	@FXML
	private Label pGender;

	@FXML
	private Label pId;

	@FXML
	private Label testName;
	
	@FXML
    private Label refDoc;

    @FXML
    private Label testDate;

	private static AnchorPane paneHeader, paneTemplate;
	private static Label p_name, p_age, p_gender, p_id, test_name,ref_doctor, test_date;
	public static Label getP_name() {
		return p_name;
	}

	public static Label getP_id() {
		return p_id;
	}

	public static Label getTest_name() {
		return test_name;
	}

	public static void setP_name(Label p_name) {
		HeaderController.p_name = p_name;
	}

	public static void setP_id(Label p_id) {
		HeaderController.p_id = p_id;
	}

	public static void setTest_name(Label test_name) {
		HeaderController.test_name = test_name;
	}

	private static Connection con;
	private static PreparedStatement ps;

	public static AnchorPane getPaneTemplate() {
		return paneTemplate;
	}

	public static void setPaneTemplate(AnchorPane paneTemplate) {
		HeaderController.paneTemplate = paneTemplate;
	}

	public static AnchorPane getPaneHeader() {
		return paneHeader;
	}

	public static void setPaneHeader(AnchorPane paneHeader) {
		HeaderController.paneHeader = paneHeader;
	}
	
	public static Label getRef_doctor() {
		return ref_doctor;
	}

	public static Label getTest_date() {
		return test_date;
	}

	public static void setRef_doctor(Label ref_doctor) {
		HeaderController.ref_doctor = ref_doctor;
	}

	public static void setTest_date(Label test_date) {
		HeaderController.test_date = test_date;
	}

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		paneHeader = pane_header;
		paneTemplate=pane_template;
		p_name = pname;
		p_age = pAge;
		p_gender = pGender;
		ref_doctor = refDoc;
		p_id = pId;
		test_date = testDate;
		test_name = testName;

		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		test_date.setText(timeStamp);

		patientData();
	}

	public static void patientData() {
		try {
			con = DBConnectivity.getConnection();
			int patientID = ViewPDController.getPID();
			ps = con.prepareStatement("SELECT * FROM patient_masterdata WHERE patient_id='" + patientID + "'");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p_name.setText(rs.getString("patient_name"));
				p_age.setText(rs.getString("age"));
				p_gender.setText(rs.getString("gender"));
				p_id.setText(String.valueOf(patientID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
