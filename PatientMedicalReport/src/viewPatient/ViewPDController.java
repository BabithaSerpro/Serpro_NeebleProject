package viewPatient;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnection.DBConnectivity;
import application.DashboardController;
import application.MainScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewPDController implements Initializable {
	@FXML
	private Label lbl_ID;
	
	@FXML
	private Label lbl_patientName_top;
	
	@FXML
	private Label lbl_patientName;

	@FXML
	private Label lbl_gender;

	@FXML
	private Label lbl_mobileNumber;

	@FXML
	private Label lbl_email;

	@FXML
	private Label lbl_dob;

	@FXML
	private Label lbl_age;

	@FXML
	private Button btn_edit;
	
	@FXML
	private Button btn_Close;

	@FXML
	private TableView<TestData> tbl_testDetails;

	@FXML
	private Button btn_addTest;

	private static Label lblID, lblPName_top,lblPName, lblGender, lblmobileNumber, lblEmail, lblDoB, lblAge;

	private static Connection con;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblID=lbl_ID;
		lblPName_top=lbl_patientName_top;
		lblPName = lbl_patientName;
		lblGender = lbl_gender;
		lblmobileNumber=lbl_mobileNumber;
		lblEmail=lbl_email;
		lblDoB=lbl_dob;
		lblAge=lbl_age;
		
		con = DBConnectivity.getConnection();
	}

	public static Label getLblPName() {
		return lblPName;
	}

	public static void setLblPName(Label lblPName) {
		ViewPDController.lblPName = lblPName;
	}

	public static void ViewDetails(int patientID) throws SQLException {
		String SQL_view = "SELECT * FROM patient_masterdata WHERE patient_id='"+patientID+"'";
		try {
			ResultSet rs = con.createStatement().executeQuery(SQL_view);
			lblID.setText(String.valueOf(patientID));
			
			while (rs.next()) {
				lblPName_top.setText(rs.getString("patient_name"));
				lblPName.setText(rs.getString("patient_name"));
				lblGender.setText(rs.getString("gender"));
				lblmobileNumber.setText(rs.getString("mobileNumber"));
				lblEmail.setText(rs.getString("emailId"));
				lblDoB.setText(rs.getString("dob"));
				lblAge.setText(rs.getString("age"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	public void addTest() {
		try {
			FXMLLoader fxmlloader = new FXMLLoader(DashboardController.class.getResource("/addTest/addTest.fxml"));
			Parent workspace = (Parent) fxmlloader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(workspace));
			stage.setTitle("Test Details");
			stage.getIcons().add(new Image("file:imgs/sdIcon.png"));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cant load window");
		}
	}
	@FXML
	public void editDetails() {
		try {
			
		} catch (Exception e) {
			
		}
	}
	@FXML
	public void closeTab() {
		try {
			AnchorPane pane=MainScreenController.getHomePage();
			for(int i=0;i<pane.getChildren().size();i++) {
				String paneID=pane.getChildren().get(i).getId();
				switch (paneID) {
					case "pane_Dashboard":
						MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
						break;
					case "pane_viewDetails":
						MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
						break;
				}
			}
		} catch (Exception e) {
			
		}
	}
	
}
