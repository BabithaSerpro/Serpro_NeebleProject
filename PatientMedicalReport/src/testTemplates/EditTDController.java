package testTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import screens.HomePageController;
import viewPatient.ViewPDController;

public class EditTDController {
	@FXML
	private AnchorPane pane_EditTD;

	@FXML
	private ScrollPane sEditPane;

	@FXML
	private AnchorPane pane_EditTDContent;

	@FXML
	private Label pGender;

	@FXML
	private Label pAge;

	@FXML
	private TextField refDoc;

	@FXML
	private TextField testDate;

	@FXML
	private Label pId;

	@FXML
	private Label lblName;

	@FXML
	private Button btn_close;

	public static ScrollPane s_editPane;
	public static Stage reportScreen;

	public static AnchorPane paneEdit, testContentPane;
	private static Label p_name, p_age, p_gender, p_id, test_name;
	public static TextField ref_doctor, test_date;
	public static String tName;
	private static Connection con;
	private static PreparedStatement ps;
	public static String test_details, past_history, menstural_data, clinical_impression, fetal_parameter, fetal_dop_studies, table1,
			table2, impression, note;
	public static int tID, ID;
	public static VBox tvbox = new VBox(10);
	public static Button btnSave = new Button("Save");
	private static ResultSet rs;
	private static int flag;

	public static Stage getReportScreen() {
		return reportScreen;
	}

	public static void setReportScreen(Stage reportScreen) {
		EditTDController.reportScreen = reportScreen;
	}

	public static String gettName() {
		return tName;
	}

	public static void settName(String tName) {
		EditTDController.tName = tName;
	}

	public static AnchorPane getTestContentPane() {
		return testContentPane;
	}

	public static void setTestContentPane(AnchorPane testContentPane) {
		EditTDController.testContentPane = testContentPane;
	}

	public static VBox getVbox() {
		return tvbox;
	}

	public static void setVbox(VBox tvbox) {
		EditTDController.tvbox = tvbox;
	}

	public static TextField getRef_doctor() {
		return ref_doctor;
	}

	public static void setRef_doctor(TextField ref_doctor) {
		EditTDController.ref_doctor = ref_doctor;
	}
	
	public static int getID() {
		return ID;
	}

	public static void setID(int iD) {
		ID = iD;
	}
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		paneEdit = pane_EditTD;
		s_editPane = sEditPane;
		testContentPane = pane_EditTDContent;
		p_age = pAge;
		p_gender = pGender;
		ref_doctor = refDoc;
		p_id = pId;
		p_name = lblName;
		test_date = testDate;

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

	public static void screenContent(String testName) throws Exception {
		Label lblTestname = new Label(testName);
		lblTestname.setLayoutX(210);
		lblTestname.setLayoutY(75);
		lblTestname.setPrefWidth(741);
		lblTestname.setPrefHeight(32);
		lblTestname.setTextAlignment(TextAlignment.CENTER);

		settName(testName);
		tvbox.getChildren().clear();
		testDetails();

		tvbox.setLayoutX(35);
		tvbox.setLayoutY(110);
		tvbox.setId("test_vbox");
		tvbox.getChildren().add(btnSave);
		btnSave.setPrefWidth(110);
		btnSave.setPrefHeight(30);
		btnSave.setStyle("-fx-font-size: 15; -fx-text-fill: white; -fx-background-color:  #2eacd2; -fx-padding: 2 2 2 2;");
		testContentPane.getChildren().addAll(lblTestname, tvbox);
	}

	public static void testDetails() throws Exception {
		String testname = gettName();
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_report WHERE TEST_NAME='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			tID = rs.getInt("ID");
			test_details=rs.getString("TEST_DETAILS");
			past_history = rs.getString("PAST HISTORY");
			table1 = rs.getString("TABLE1");
			table2 = rs.getString("TABLE2");
			impression = rs.getString("IMPRESSION");
			note = rs.getString("PLEASE_NOTE");
		}
		if (past_history.equals("TRUE")) {
			TestContent.create_pastHistory(tID);
		}
		if (test_details.equals("TRUE")) {
			TestContent.create_testDetails(tID);
		}
		if (table1.equals("TRUE")) {
			TestContent.create_table1(tID);
		}
		if (table2.equals("TRUE")) {
			TestContent.create_table2(tID);
		}
		if (impression.equals("TRUE")) {
			TestContent.create_impression(tID);
		}
		if (note.equals("TRUE")) {
			TestContent.create_note(tID);
		}
		btnSave.setOnAction(e -> {
			try {
				addTest();
				int pid=Integer.valueOf(p_id.getText());
				VBox vbox=new VBox(10);
				CreateTestTemplate.screenContent(testname,pid,getID(),vbox);
				ViewPDController.refreshTestDetails(pid);
				HomePageController.totalTestCount();
				HomePageController.totalTestCompleted();
				HomePageController.totalTestCount();
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Error while Inserting Data!! Please Try Again!");
				alert.showAndWait();
				e1.printStackTrace();
			}
		});
		ps.close();
		rs.close();
	}

	public static void addTest() throws Exception {

		String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
		String insert = "INSERT into patient_reportmasterdata (regNumber,ref_doctor,testName,testDate,reportDate,patientHistory,testDescription,"
				+ "table1,table2,impression,note,active,created_timestamp,modified_timestamp)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,'Y','" + timeStamp + "','" + timeStamp + "')";
		con = DBConnectivity.getConnection();
		try {
			ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p_id.getText());
			ps.setString(2, ref_doctor.getText());
			ps.setString(3, gettName());
			ps.setString(4, (test_date.getText()));
			String reportDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			ps.setString(5, reportDate);
			if (past_history.equals("TRUE")) {
				ps.setString(6, TestContent.history.getHtmlText());
			}else {
				ps.setString(6, "");
			}
			if(test_details.equals("TRUE")) {
				String st = TestContent.testDetstails.getHtmlText();
				if (st.contains("<br>")) {
					st = st.replace("<br>", "<br/>");
				}
				ps.setString(7, st);
			}else {
				ps.setString(7, "");
			}
			if (table1.equals("TRUE")) {
				ps.setString(8, TestContent.table1.getHtmlText());
			}else {
				ps.setString(8, "");
			}
			if (table2.equals("TRUE")) {
				ps.setString(9, TestContent.table2.getHtmlText());
			}else {
				ps.setString(9, "");
			}
			if (impression.equals("TRUE")) {
				ps.setString(10, TestContent.impression.getHtmlText());
			}else {
				ps.setString(10, "");
			}
			if (note.equals("TRUE")) {
				ps.setString(11, TestContent.note.getHtmlText());
			}else {
				ps.setString(11, "");
			}
			flag = ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				ID = rs.getInt(1);
				setID(ID);
			}
			if (flag > 0) { // redirecting to dashboard
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Test Added Successfully");
				alert.showAndWait().ifPresent(bt -> {
					if (bt == ButtonType.OK) {
						try {
							close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Error while Inserting Data!! Please Try Again!");
				alert.showAndWait();
			}

		} catch (SQLException e) { // catching exception if any backend error occurs
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Error while Inserting Data!! Please Try Again!");
			alert.showAndWait();
			e.printStackTrace();
		}

	}
	
	@FXML
	void cancelBtn(ActionEvent event) throws Exception {
		close();
	}
	public static void close() throws Exception {
		AnchorPane pane = MainScreenController.getHomePage();
		for (int i = 0; i < pane.getChildren().size(); i++) {
			String paneID = pane.getChildren().get(i).getId();
			switch (paneID) {
			case "pane_Dashboard":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_EditTD":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_viewDetails":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
				break;
			
			}
		}
	}
}
