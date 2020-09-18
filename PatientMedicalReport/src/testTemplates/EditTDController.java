package testTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
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
    private VBox test_vbox;

    @FXML
    private Button btn_close;

    public static ScrollPane s_editPane;
	public static AnchorPane paneEdit, testContentPane;
	private static Label p_name,p_age,p_gender,p_id,test_name;
	private static TextField ref_doctor,test_date;
	public static VBox tvbox;
	public static String tName;
	private static Connection con;
	private static PreparedStatement ps;
	public static String past_history,menstural_data,clinical_impression,fetal_parameter,fetal_dop_studies,table1,table2,impression,note;
	public static int tID, table1_col,table1_row,table2_col,table2_row;
    
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
	
    @FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		paneEdit=pane_EditTD;
		s_editPane=sEditPane;
		testContentPane=pane_EditTDContent;
		p_age=pAge;
		p_gender=pGender;
		ref_doctor=refDoc;
		p_id=pId;
		p_name=lblName;
		test_date=testDate;
		tvbox=test_vbox;
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
    
    public static void screenContent(String testName) throws Exception {
		Label lblTestname=new Label(testName);
		lblTestname.setTranslateX(230);
		lblTestname.setTranslateY(75);
		lblTestname.setPrefWidth(741);
		lblTestname.setPrefHeight(32);
		lblTestname.setTextAlignment(TextAlignment.CENTER);
		
		settName(testName);
		testDetails();
		testContentPane.getChildren().addAll(lblTestname);
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
		if(clinical_impression.equals("TRUE")) {
			TestContent.create_clinicalImp(tID);
			System.out.println("clinical_impression");
		}
		if(fetal_parameter.equals("TRUE")) {
			System.out.println("fetal_parameter");
			TestContent.create_fetalParameter(tID);
		}
		if(fetal_dop_studies.equals("TRUE")) {
			System.out.println("fetal_dop_studies");
			TestContent.create_fetaldopStudies(tID);
		}
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
    
	@FXML
    void cancelBtn(ActionEvent event) {
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
