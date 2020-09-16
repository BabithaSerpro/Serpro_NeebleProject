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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import viewPatient.ViewPDController;

public class HeaderController {

	@FXML
    private AnchorPane pane_header;

	@FXML
	private Label pname;

	@FXML
	private Label pAge;

	@FXML
	private Label pGender;

	@FXML
	private TextField refDoc;

	@FXML
	private Label pId;

	@FXML
	private TextField testDate;

	@FXML
	private Label testName;
	
	private static AnchorPane paneHeader;
	private static Label p_name,p_age,p_gender,p_id,test_name;
	private static TextField ref_doctor,test_date;
	private static Connection con;
	private static PreparedStatement ps;

	public static AnchorPane getPaneHeader() {
		return paneHeader;
	}

	public static void setPaneHeader(AnchorPane paneHeader) {
		HeaderController.paneHeader = paneHeader;
	}
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		paneHeader=pane_header;
		p_name=pname;
		p_age=pAge;
		p_gender=pGender;
		ref_doctor=refDoc;
		p_id=pId;
		test_date=testDate;
		test_name=testName;

		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		test_date.setText(timeStamp);
		
		patientData();
	}
	
	public static void patientData() {
		try {
			con = DBConnectivity.getConnection();
			int patientID=ViewPDController.getPID();
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
