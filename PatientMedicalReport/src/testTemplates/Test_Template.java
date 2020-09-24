package testTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DBConnection.DBConnectivity;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class Test_Template {
	private static Connection con = DBConnectivity.getConnection();
	private static PreparedStatement ps;
	private ResultSet rs;
	public static AnchorPane contentPane = HeaderController.getPaneTemplate();
	public static VBox vbox = CreateTestTemplate.getVbox();
	public static HTMLEditor history, clinicalImp, fetalParameter, fetaldopStudies, impression, note, table1, table2,
			testDetstails;

	public static void patientreportData(int pID, String testname) {
		try {
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				HeaderController.getRef_doctor().setText(rs.getString("ref_doctor"));
				HeaderController.getTest_date().setText(rs.getString("reportDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void create_testDetails(int pID, String testname) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDescription"));
		}
		String st = he_Testdetails.getHtmlText();
		if (st.contains("contenteditable=\"true\"")) {
			st = st.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
		}
		
		he_Testdetails=htmlEditorStyle(he_Testdetails);

		String text = stripHTMLTags(he_Testdetails.getHtmlText());
		heHeightTestDeatils(he_Testdetails,testname);
		System.out.println("he_Testdetails "+he_Testdetails.getHtmlText().length());
		if (!(text.equals(""))) {
			vbox.getChildren().add(he_Testdetails);
		}
	}

	public static void create_pastHistory(int pID, String testname) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setPrefHeight(400);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement(
				"SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("patientHistory"));
		}
		// hide controls we don't need.
		hE_pHistory=htmlEditorStyle(hE_pHistory);

		vbox.getChildren().addAll(lblphistory, hE_pHistory);
	}

	public static void create_impression(int pID, String testname) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefWidth(700);
		hE_imp.setPrefHeight(150);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement(
				"SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("impression"));
		}
		htmlEditorHeight(hE_imp,testname);
		System.out.println("hE_imp "+hE_imp.getHtmlText().length());
		// hide controls we don't need.
		hE_imp=htmlEditorStyle(hE_imp);

		vbox.getChildren().addAll(lblimp, hE_imp);
	}

	public static void create_note(int pID, String testname) throws SQLException {
		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefWidth(700);
		hE_note.setPrefHeight(100);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("note"));
		}
		
		hE_note=htmlEditorStyle(hE_note);
		htmlEditorHeight(hE_note,testname);
		System.out.println("hE_note "+hE_note.getHtmlText().length());
		vbox.getChildren().addAll(lblnote, hE_note);
	}

	public static String stripHTMLTags(String htmlText) {
		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		final StringBuffer sb = new StringBuffer(htmlText.length());
		while (matcher.find()) {
			matcher.appendReplacement(sb, " ");
		}
		matcher.appendTail(sb);
		return sb.toString().trim();

	}

	public static HTMLEditor htmlEditorStyle(HTMLEditor htmleditor) {
		// hide controls we don't need.
		Node t_node = htmleditor.lookup(".top-toolbar");
		Node b_node = htmleditor.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		return htmleditor;
	}

	public static HTMLEditor heHeightTestDeatils(HTMLEditor htmleditor, String testname) {
		switch (testname) {
		case "COLOUR DOPPLER OBSTRETIC":
			htmleditor.setPrefHeight(1800);
			break;
		case "FOLLICULAR STUDY":
			htmleditor.setPrefHeight(800);
			break;
		case "USG OF PELVIS (VERY EARLY STAGE PREGNANCY)":
			htmleditor.setPrefHeight(400);
			break;
		case "ULTRASONOGRAPHY OF BOTH BREAST":
			htmleditor.setPrefHeight(450);
			break;
		case "USG  OBSTRETIC ANOMALY SCAN":
			htmleditor.setPrefHeight(400);
			break;
		case "USG ABDOMEN AND PELVIS - RUS":
			htmleditor.setPrefHeight(450);
			break;
		case "USG ABDOMEN AND PELVIS - FEMALE":
			htmleditor.setPrefHeight(500);
			break;
		case "USG ABDOMEN AND PELVIS - MALE BPH":
			htmleditor.setPrefHeight(500);
			break;
		case "USG ABDOMEN AND PELVIS BKS":
			htmleditor.setPrefHeight(500);
			break;
		case "USG ABDOMEN AND PELVIS MALE":
			htmleditor.setPrefHeight(500);
			break;
		case "USG ABDOMEN":
			htmleditor.setPrefHeight(400);
			break;
		case "USG CHEST":
			htmleditor.setPrefHeight(150);
			break;
		case "USG GROIN (THIGH)":
			htmleditor.setPrefHeight(200);
			break;
		case "USG KUB BKS":
			htmleditor.setPrefHeight(420);
			break;
		case "USG KUB":
			htmleditor.setPrefHeight(380);
			break;
		case "USG  OBSTRETIC TWINS":
			htmleditor.setPrefHeight(500);
			break;
		case "USG OF OBSTETRIC NT SCAN (I TRIMESTER ANOMALY SCAN)":
			htmleditor.setPrefHeight(450);
			break;
		case "USG  OBSTRETIC 4-9 MTH":
			htmleditor.setPrefHeight(470);
			break;
		case "USG OBSTRETIC EARLY PREGNANCY":
			htmleditor.setPrefHeight(400);
			break;
		case "USG OF SKULL":
			htmleditor.setPrefHeight(300);
			break;
		case "USG OF THYROID":
			htmleditor.setPrefHeight(300);
			break;
		case "USG OF PELVIS (FEMALE)":
			htmleditor.setPrefHeight(450);
			break;
		case "USG OF SCROTUM ":
			htmleditor.setPrefHeight(400);
			break;
		case "USG OF SMALL PART":
			htmleditor.setPrefHeight(340);
			break;
		case "VENOUS DOPPLER OF BOTH LOWER LIMBS":
			htmleditor.setPrefHeight(370);
			break;
		case "VENOUS DOPPLER OF BOTH UPPER LIMBS":
			htmleditor.setPrefHeight(350);
			break;
		case "ARTERIAL DOPPLER OF BOTH LOWER LIMBS":
			htmleditor.setPrefHeight(800);
			break;
		case "ARTERIAL DOPPLER BOTH UPPER LIMBS":
			htmleditor.setPrefHeight(800);
			break;
		case "COLOUR DOPPLER OF  CAROTID AND VERTEBRAL ARTERIES":
			htmleditor.setPrefHeight(700);
			break;
		case "COLOR DOPPLER EXAMINATION OF SCROTUM":
			htmleditor.setPrefHeight(620);
			break;
		case "RENAL COLOR DOPPLER":
			htmleditor.setPrefHeight(900);
			break;
		case "SCROTUM COLOR DOPPLER":
			htmleditor.setPrefHeight(350);
			break;
		case "3D ULTRASONOGRAPHY OF PELVIS":
			htmleditor.setPrefHeight(580);
			break;
		case "CAROTID AND RADIAL COLOUR  DOPPLER":
			htmleditor.setPrefHeight(630);
			break;
		case "COLOR DOPPLER EXAMINATION  OF ABDOMINAL VESSELS":
			htmleditor.setPrefHeight(900);
			break;
		case "COLOR DOPPLER EXAMINATION  OF SUPERIOR MESENTRIC  ARTERY":
			htmleditor.setPrefHeight(610);
			break;
		case "COLOR DOPPLER EXAMINATION OF LEFT SUBCLAVIAN  ARTERY":
			htmleditor.setPrefHeight(1200);
			break;
		case "COLOR DOPPLER EXAMINATION OF NECK VESSELS":
			htmleditor.setPrefHeight(900);
			break;
		case "COLOR DOPPLER EXAMINATION OF UPPER EXTREMITY VENOUS CIRCULATION":
			htmleditor.setPrefHeight(400);
			break;
		case "COLOR DOPPLER OF FEMALE PELVIS":
			htmleditor.setPrefHeight(1200);
			break;
		case "PENILE COLOR DOPPLER":
			htmleditor.setPrefHeight(600);
			break;
		case "TRANSCRANIAL COLOR  DOPPLER  EXAMINATION":
			htmleditor.setPrefHeight(800);
			break;
		
		}
		
		return htmleditor;
	}
	public static HTMLEditor htmlEditorHeight(HTMLEditor htmleditor, String testname) {
		String text = stripHTMLTags(htmleditor.getHtmlText());
		int lngth=htmleditor.getHtmlText().length();
		
		System.out.println("text "+lngth);
		if(lngth>10 && lngth<=50) {
			htmleditor.setPrefHeight(10);
		}else if(lngth>50 && lngth<=100){
			htmleditor.setPrefHeight(50);
		}else if(lngth>100 && lngth<=200){
			htmleditor.setPrefHeight(70);
		}else if(lngth>200 && lngth<=300){
			htmleditor.setPrefHeight(100);
		}else if(lngth>300 && lngth<=400){
			htmleditor.setPrefHeight(100);
		}else if(lngth>400 && lngth<=600){
			htmleditor.setPrefHeight(430);
		}else if(lngth>600 && lngth<=800){
			htmleditor.setPrefHeight(520);
		}else if(lngth>900 && lngth<=1000){
			htmleditor.setPrefHeight(450);
		}else if(lngth>1000 && lngth<=1200){
			htmleditor.setPrefHeight(550);
		}else if(lngth>1200 && lngth<=1400){
			htmleditor.setPrefHeight(650);
		}else if(lngth>1400 && lngth<=1600){
			htmleditor.setPrefHeight(900);
		}else if(lngth>1600 && lngth<=1800){
			htmleditor.setPrefHeight(5000);
		}else if(lngth>1800 && lngth<=2000){
			htmleditor.setPrefHeight(1100);
		}else if(lngth>2000 && lngth<=2200){
			htmleditor.setPrefHeight(1200);
		}else if(lngth>2200 && lngth<=2400){
			htmleditor.setPrefHeight(1300);
		}else if(lngth>2400 && lngth<=2600){
			htmleditor.setPrefHeight(1400);
		}else if(lngth>2600 && lngth<=2800){
			htmleditor.setPrefHeight(1500);
		}else if(lngth>2800 && lngth<=3000){
			htmleditor.setPrefHeight(1600);
		}else if(lngth>3000 && lngth<=3200){
			htmleditor.setPrefHeight(1700);
		}else if(lngth>3200 && lngth<=3400){
			htmleditor.setPrefHeight(1800);
		}else if(lngth>3400 && lngth<=3600){
			htmleditor.setPrefHeight(2000);
		}else if(lngth>3600 && lngth<=3800){
			htmleditor.setPrefHeight(1800);
		}else if(lngth>7000 && lngth<=7400){
			htmleditor.setPrefHeight(5000);
		}
		
		return htmleditor;
	}
}
