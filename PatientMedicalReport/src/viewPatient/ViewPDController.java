package viewPatient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.DBConnectivity;
import application.DashboardController;
import application.MainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewPDController {
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
	private TableColumn<TestData, String> clm_tDate;

	@FXML
	private TableColumn<TestData, String> clm_testName;

	@FXML
	private TableColumn<TestData, String> clm_status;

	@FXML
	private TableView<TestData> tbl_testDetails;

	@FXML
	private Button btn_addTest;
	private static TableView<TestData> tblTestTable;
	private static ObservableList<TestData> testData = FXCollections.observableArrayList();;

	private static Label lblID, lblPName_top, lblPName, lblGender, lblmobileNumber, lblEmail, lblDoB, lblAge;

	private static Connection con;

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		tblTestTable = tbl_testDetails;
		lblID = lbl_ID;
		lblPName_top = lbl_patientName_top;
		lblPName = lbl_patientName;
		lblGender = lbl_gender;
		lblmobileNumber = lbl_mobileNumber;
		lblEmail = lbl_email;
		lblDoB = lbl_dob;
		lblAge = lbl_age;

		clm_testName.setCellValueFactory(new PropertyValueFactory<TestData, String>("testName"));
		clm_tDate.setCellValueFactory(new PropertyValueFactory<TestData, String>("reportDate"));
		clm_status.setCellValueFactory(new PropertyValueFactory<TestData, String>("status"));
		con = DBConnectivity.getConnection();
	}

	public static Label getLblPName() {
		return lblPName;
	}

	public static void setLblPName(Label lblPName) {
		ViewPDController.lblPName = lblPName;
	}

	public static Label getLblGender() {
		return lblGender;
	}

	public static void setLblGender(Label lblGender) {
		ViewPDController.lblGender = lblGender;
	}

	public static Label getLblAge() {
		return lblAge;
	}

	public static void setLblAge(Label lblAge) {
		ViewPDController.lblAge = lblAge;
	}

	public static void ViewDetails(int patientID) throws SQLException {
		String SQL_view = "SELECT * FROM patient_masterdata WHERE patient_id='" + patientID + "'";
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

	public static void ViewTestDetails(int pid) throws SQLException {
		testData.clear();
		String SQL_view = "SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pid + "'";
		try {
			ResultSet rs = con.createStatement().executeQuery(SQL_view);
			while (rs.next()) {
				TestData td = new TestData();
				td.tId.set(rs.getInt("id"));
				td.testName.set(rs.getString("testName"));
				td.reportDate.set(rs.getString("reportDate"));
				if (rs.getString("reportDate") != null) {
					td.status.set("Completed");
				} else {
					td.status.set("Pending");
				}
				testData.add(td);
			}
			TestData.addViewButton(pid);
			TestData.addEditButton(pid);
			TestData.addDeleteButton(pid, con);
			tblTestTable.setItems(testData);
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dr Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Cannot Load Screen");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	public static void refreshTestDetails(int pid) throws SQLException {
		testData.clear();
		String SQL_view = "SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pid + "'";
		try {
			ResultSet rs = con.createStatement().executeQuery(SQL_view);
			while (rs.next()) {
				TestData td = new TestData();
				td.tId.set(rs.getInt("id"));
				td.testName.set(rs.getString("testName"));
				td.reportDate.set(rs.getString("reportDate"));
				if (rs.getString("reportDate") != null) {
					td.status.set("Completed");
				} else {
					td.status.set("Pending");
				}
				testData.add(td);
			}
			tblTestTable.setItems(testData);
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dr Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Cannot Load Screen");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	@FXML
	public void addTest(ActionEvent event) {
		try {
			AnchorPane pane = MainScreenController.getHomePage();
			for (int i = 0; i < pane.getChildren().size(); i++) {
				String paneID = pane.getChildren().get(i).getId();
				switch (paneID) {
				case "pane_Dashboard":
					MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
					break;
				case "pane_viewDetails":
					MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
					break;
				}
			}
			Parent root = FXMLLoader.load(DashboardController.class.getResource("/addTest/addTest.fxml"));
			MainScreenController.getHomePage().getChildren().add(root);
			root.setTranslateX(370);
			root.setTranslateY(30);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cant load window");
		}
	}

	@FXML
	public void editDetails(ActionEvent event) throws Exception {
		btn_edit.getScene().getWindow();
		Stage add = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/addPatient/editPatient.fxml"));
		Scene scene = new Scene(root);
		add.setScene(scene);
		add.show();
	}

	@FXML
	public void closeTab(ActionEvent event) {
		try {
			AnchorPane pane = MainScreenController.getHomePage();
			for (int i = 0; i < pane.getChildren().size(); i++) {
				String paneID = pane.getChildren().get(i).getId();
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

	public static Label getLblID() {
		return lblID;
	}

	public static void setLblID(Label lblID) {
		ViewPDController.lblID = lblID;
	}

	public static TableView<TestData> getTblTestTable() {
		return tblTestTable;
	}

	public static void setTblTestTable(TableView<TestData> tblTestTable) {
		ViewPDController.tblTestTable = tblTestTable;
	}
}
