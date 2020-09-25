package testTemplates;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnection.DBConnectivity;
import application.DashboardController;
import application.MainScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Test_Screens {
	
	public static Stage reportScreen;
	public static ScrollPane sPane;
	public static AnchorPane contentPane;
	public static VBox vbox=new VBox();
	public static String tName;
	private static Connection con;
	private static PreparedStatement ps;
	public static String past_history,menstural_data,clinical_impression,fetal_parameter,fetal_dop_studies,table1,table2,impression,note;
	public static int tID, table1_col,table1_row,table2_col,table2_row;

	public static String gettName() {
		return tName;
	}

	public static void settName(String tName) {
		Test_Screens.tName = tName;
	}
	
	public static AnchorPane getContentPane() {
		return contentPane;
	}
	public static void setContentPane(AnchorPane contentPane) {
		Test_Screens.contentPane = contentPane;
	}
	public static VBox getVbox() {
		return vbox;
	}
	public static void setVbox(VBox vbox) {
		Test_Screens.vbox = vbox;
	}

	public static Stage getReportScreen() {
		return reportScreen;
	}
	
	public static void screenContent(String testName) throws Exception {
		try {
			settName(testName);
			reportScreen = new Stage();
			contentPane=new AnchorPane();
			contentPane.setId("contentPane");
			Parent header = FXMLLoader.load(DashboardController.class.getResource("/testTemplates/Header.fxml"));
			MainScreenController.getHomePage().getChildren().add(header);
			header.setTranslateX(0);
			header.setTranslateY(0);
			
			Label lblTestname=new Label(testName);
			lblTestname.setTranslateX(220);
			lblTestname.setTranslateY(240);
			lblTestname.setPrefWidth(741);
			lblTestname.setPrefHeight(32);
			lblTestname.setTextAlignment(TextAlignment.CENTER);
			
			vbox.setId("test_vbox");
			vbox.setTranslateX(40);
			vbox.setTranslateY(275);
			
			testDetails();
			
			Parent footer = FXMLLoader.load(DashboardController.class.getResource("/testTemplates/Footer.fxml"));
			MainScreenController.getHomePage().getChildren().add(footer);
			footer.setTranslateX(0);
			footer.setTranslateY(830);
			
			contentPane.getChildren().addAll(header,lblTestname,vbox,footer);
			contentPane.setStyle("-fx-background-color: white;");
			contentPane.setPrefHeight(1000);
			sPane=new ScrollPane(contentPane);
			Scene scene = new Scene(sPane);
			reportScreen.setHeight(680);
			reportScreen.setWidth(810);
			reportScreen.setTitle("Patient Report");
			reportScreen.setScene(scene);
			reportScreen.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void testDetails() throws Exception {
		String testname=gettName();
		
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_report WHERE TEST_NAME='"+testname+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			tID=rs.getInt("ID");
			past_history=rs.getString("PAST HISTORY");
			clinical_impression=rs.getString("CLINICAL_IMPRESSION");
			fetal_parameter=rs.getString("FETAL PARAMETER");
			fetal_dop_studies=rs.getString("FETAL DOPPLER STUDIES");
			table1=rs.getString("TABLE1");
			table1_col=rs.getInt("TABLE1_NO_OF_COLUMNS");
			table1_row=rs.getInt("TABLE1_NO_OF_ROWS");
			table2=rs.getString("TABLE2");
			table2_col=rs.getInt("TABLE2_NO_OF_COLUMNS");
			table2_row=rs.getInt("TABLE2_NO_OF_ROWS");  
			impression=rs.getString("IMPRESSION");
			note=rs.getString("PLEASE_NOTE");
		}
		
		if(past_history.equals("TRUE")) {
			System.out.println("past_history");
			TestContent.create_pastHistory(tID);
		}
		/*
		 * if(clinical_impression.equals("TRUE")) { TestContent.create_clinicalImp(tID);
		 * System.out.println("clinical_impression"); }
		 * if(fetal_parameter.equals("TRUE")) { System.out.println("fetal_parameter");
		 * TestContent.create_fetalParameter(tID); }
		 * if(fetal_dop_studies.equals("TRUE")) {
		 * System.out.println("fetal_dop_studies");
		 * TestContent.create_fetaldopStudies(tID); }
		 */
		if(table1.equals("TRUE")) {
			System.out.println("table1");
			TestContent.create_table1(tID);
		}
		if(table2.equals("TRUE")) {
			System.out.println("table2");
			TestContent.create_table2(tID);
		}
		if(impression.equals("TRUE")) {
			System.out.println("impression");
			TestContent.create_impression(tID);
		}
		if(note.equals("TRUE")) {
			System.out.println("note");
			TestContent.create_note(tID);
		}
		ps.close();
		rs.close();
		
	}
	
}
