package testTemplates;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.stage.StageStyle;
import viewPatient.ViewPDController;

public class EditTestTemplateController implements Initializable {

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
    public static AnchorPane paneEdit, testContentPane;
    private static Label p_name, p_age, p_gender, p_id, test_name;
	private static Connection con;
	private static int flag;
	private static PreparedStatement ps;
	public static String test_details, past_history,
			 table1, table2, reportDate,impression, note,testName;
	public static TextField ref_doctor, test_date;
	public static Button btnUpdate = new Button("Update");
	public static VBox tvbox = new VBox(10);
	public static ScrollPane s_editPane;

	
	public static String getTestName() {
		return testName;
	}

	public static void setTestName(String testName) {
		EditTestTemplateController.testName = testName;
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
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneEdit = pane_EditTD;
		s_editPane = sEditPane;
		testContentPane = pane_EditTDContent;
		ref_doctor=refDoc;
		p_id = pId;
		p_age=pAge;
		test_date=testDate;
		p_gender=pGender;
		epatientData();

	}

	public static void epatientData() {
		try {
			con = DBConnectivity.getConnection();
			int patientID = ViewPDController.getPID();
			ps = con.prepareStatement("SELECT * FROM patient_masterdata WHERE patient_id='" + patientID + "'");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p_age.setText(rs.getString("age"));
				p_gender.setText(rs.getString("gender"));
				p_id.setText(String.valueOf(patientID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void editScreenContent(String testName,int tId) throws Exception {
		Label lblTestname = new Label(testName);
		lblTestname.setLayoutX(210);
		lblTestname.setLayoutY(75);
		lblTestname.setPrefWidth(741);
		lblTestname.setPrefHeight(32);
		lblTestname.setTextAlignment(TextAlignment.CENTER);

		setTestName(testName);
		tvbox.getChildren().clear();
		
		edittestDetails(tId);

		tvbox.setLayoutX(35);
		tvbox.setLayoutY(110);
		tvbox.setId("test_vbox");
		tvbox.getChildren().add(btnUpdate);
		btnUpdate.setPrefWidth(110);
		btnUpdate.setPrefHeight(30);
		btnUpdate.setStyle("-fx-font-size: 15; -fx-text-fill: white; -fx-background-color:  #2eacd2; -fx-padding: 2 2 2 2;");
		testContentPane.getChildren().addAll(lblTestname, tvbox);
	}
	public static void edittestDetails(int tId) throws Exception {
     
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tId + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ref_doctor.setText(rs.getString("ref_doctor"));
			test_date.setText(rs.getString("testDate"));
            testName= rs.getString("testName");
			reportDate = rs.getString("reportDate");
			test_details = rs.getString("testDescription");
			past_history = rs.getString("patientHistory");
			table1 = rs.getString("table1");
			table2 = rs.getString("table2");
			impression = rs.getString("impression");
			note = rs.getString("note");
		}
		if (!past_history.isEmpty() ) {
			
			TestContent.edit_pastHistory(tId);
		}

		if (!test_details.isEmpty() ) {
			TestContent.edit_testDetails(tId);
			
		}

		if (!table1.isEmpty() ) {
			TestContent.edit_table1(tId);
		}
		if (!table2.isEmpty() ) {
			TestContent.create_table2(tId);
		}
		if (!impression.isEmpty() ) {
			TestContent.edit_impression(tId);
		}
		if (!note.isEmpty()) {
			TestContent.edit_note(tId);
		}
		btnUpdate.setOnAction(e -> {
			try {
				updateTest(tId);
				
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

	public static void updateTest(int tId) throws Exception {

		String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
		String update = "update patient_reportmasterdata set ref_doctor =?,reportDate=?,patientHistory=?,testDescription=?,"
				+ "table1=?,table2=?,impression=?,note=?,modified_timestamp= '" + timeStamp + "' where id='"
				+ tId + "'";

		con = DBConnectivity.getConnection();
		try {
			ps = con.prepareStatement(update);
			ps.setString(1, ref_doctor.getText());
			//ps.setString(2, (test_date.getText()));
			
			 String reportDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			 ps.setString(2, reportDate);
			 
			if (!past_history.isEmpty() ) {
				ps.setString(3, TestContent.ehistory.getHtmlText());
			} else {
				ps.setString(3, "");
			}
			if (!test_details.isEmpty()) {
				ps.setString(4, TestContent.etestDetstails.getHtmlText());
			} else {
				ps.setString(4, "");
			}

			if (!table1.isEmpty()) {
				ps.setString(5, TestContent.etable1.getHtmlText());
			} else {
				ps.setString(5, "");
			}
			if (!table2.isEmpty()) {
				ps.setString(6, TestContent.etable2.getHtmlText());
			} else {
				ps.setString(6, "");
			}
			if (!impression.isEmpty()) {
				ps.setString(7, TestContent.eimpression.getHtmlText());
			} else {
				ps.setString(7, "");
			}
			if (!note.isEmpty()) {
				ps.setString(8, TestContent.enote.getHtmlText());
			} else {
				ps.setString(8, "");
			}
			flag = ps.executeUpdate();

			if (flag > 0) { // redirecting to dashboard
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Test Updated Successfully");
				alert.showAndWait().ifPresent(bt -> {
					if (bt == ButtonType.OK) {
						close();

					}
				});
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Error while Updating Data!! Please Try Again!");
				alert.showAndWait();
			}

		} catch (SQLException e) { // catching exception if any backend error occurs
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Error while Updating Data!! Please Try Again!");
			alert.showAndWait();
			e.printStackTrace();
		}

	}

	@FXML
	void cancelBtn(ActionEvent event) {
		close();
	}
	public static void close() {
		AnchorPane pane = MainScreenController.getHomePage();
		for (int i = 0; i < pane.getChildren().size(); i++) {
			String paneID = pane.getChildren().get(i).getId();
			switch (paneID) {
			case "pane_Dashboard":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_viewDetails":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
				break;
			case "pane_EditTD":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			}
		}
	}

}
