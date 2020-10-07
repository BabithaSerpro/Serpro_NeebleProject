package testTemplates;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CreateTestTemplate {
	public static Stage reportScreen;
	public static ScrollPane sPane;
	public static AnchorPane contentPane;
	public static VBox vbox = new VBox(10);
	public static String tName;
	private static Connection con;
	private static PreparedStatement ps;
	public static String test_details,past_history, menstural_data, clinical_impression, fetal_parameter, fetal_dop_studies, table1,
			table2, impression, note;
	public static int tID, table1_col, table1_row, table2_col, table2_row;
	public static Button btnPrint = new Button("Print");
	
	
	public static String gettName() {
		return tName;
	}

	public static void settName(String tName) {
		CreateTestTemplate.tName = tName;
	}

	public static VBox getVbox() {
		return vbox;
	}

	public static void setVbox(VBox vbox) {
		CreateTestTemplate.vbox = vbox;
	}

	public static Stage getReportScreen() {
		return reportScreen;
	}
	public static Button getBtnPrint() {
		return btnPrint;
	}

	public static void setBtnPrint(Button btnPrint) {
		CreateTestTemplate.btnPrint = btnPrint;
	}

	public static void screenContent(String testname, int pid, int id) throws Exception {
		try {
			reportScreen = new Stage();
			Parent header = FXMLLoader.load(CreateTestTemplate.class.getResource("/testTemplates/Header.fxml"));
			contentPane = HeaderController.getPaneTemplate();
			Scene scene = new Scene(header);
			reportScreen.setTitle("Patient Report");
			reportScreen.setResizable(false);
			reportScreen.setScene(scene);
			reportScreen.show();

			vbox.getChildren().clear();
			Label lblTestname = new Label(testname);
			lblTestname.setId("testname");
			lblTestname.setLayoutX(220);
			lblTestname.setLayoutY(230);
			lblTestname.setPrefWidth(741);
			lblTestname.setPrefHeight(32);
			lblTestname.setTextAlignment(TextAlignment.CENTER);
			
			createTemplate(testname,pid,id);
			
			vbox.setId("template_vbox");
			vbox.setLayoutX(40);
			vbox.setLayoutY(260);
			
			Label lblsign = new Label("DR SUBODH C KHARE");
			Label lbldesignatn = new Label("CONSULTANT RADIOLOGIST");
			lblsign.setStyle("-fx-font-size: 15; -fx-text-fill: #2eacd2; -fx-font-weight: bold; -fx-padding: 80 80 2 2;");
			lbldesignatn.setStyle("-fx-font-size: 15; -fx-text-fill: #2eacd2; -fx-padding: 2 2 2 2;");
			btnPrint.setPrefWidth(110);
			btnPrint.setPrefHeight(30);
			btnPrint.setStyle("-fx-font-size: 15; -fx-text-fill: white; -fx-background-color:  #2eacd2; -fx-padding: 2 2 2 2;");
			vbox.getChildren().addAll(lblsign,lbldesignatn,btnPrint);
			HeaderController.getPaneTemplate().getChildren().addAll(lblTestname,vbox);
			btnPrint.setOnAction(e->{
				try {
					PrintableData.generatePdfReport(testname,pid,id,vbox);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createTemplate(String testname, int pID, int id) throws SQLException {
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_report WHERE TEST_NAME='"+testname+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			tID=rs.getInt("ID");
			test_details=rs.getString("TEST_DETAILS");
			past_history=rs.getString("PAST HISTORY");
			table1=rs.getString("TABLE1");
			table2=rs.getString("TABLE2");
			impression=rs.getString("IMPRESSION");
			note=rs.getString("PLEASE_NOTE");
		}
		Test_Template.patientreportData(pID, testname,id);
		
		if(past_history.equals("TRUE")) {
			Test_Template.create_pastHistory(pID,testname,id);
		}
		if(test_details.equals("TRUE")) {
			Test_Template.create_testDetails(pID,testname,id);
		}
		if(table1.equals("TRUE")) {
			Test_Template.create_table1(pID,testname,id);
		}
		if(table2.equals("TRUE")) {
			Test_Template.create_table2(pID,testname,id);
		}
		if(impression.equals("TRUE")) {
			Test_Template.create_impression(pID,testname,id);
		}
		if(note.equals("TRUE")) {
			Test_Template.create_note(pID,testname,id);
		}
		ps.close();
		rs.close();
	}
}
