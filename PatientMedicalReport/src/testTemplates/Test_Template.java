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
		htmlEditorHeight(he_Testdetails,testname);
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

	public static void create_clinicalImp(int pID, String testname) throws SQLException {
		Label lblclinicalImp = new Label("CLINICAL IMPRESSION");
		HTMLEditor hE_clinicalImp = new HTMLEditor();
		hE_clinicalImp.setPrefHeight(400);
		hE_clinicalImp.setPrefWidth(700);
		hE_clinicalImp.setId("hePastHistory");
		hE_clinicalImp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement(
				"SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_clinicalImp.setHtmlText(rs.getString("clinicalImpression"));
		}
		// hide controls we don't need.
		hE_clinicalImp=htmlEditorStyle(hE_clinicalImp);
		
		vbox.getChildren().addAll(lblclinicalImp, hE_clinicalImp);
	}

	public static void create_fetalParameter(int pID, String testname) {
		try {
			Label lblfp = new Label("FETAL PARAMETERS: ");

			HTMLEditor hE_fp = new HTMLEditor();
			hE_fp.setPrefWidth(700);
			hE_fp.setPrefHeight(400);
			hE_fp.setId("heFetalParameter");
			hE_fp.setStyle("-fx-border-color:white;");

			ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID
					+ "' AND testName='" + testname + "'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hE_fp.setHtmlText(rs.getString("fetalParameter"));
			}
			// hide controls we don't need.
			hE_fp=htmlEditorStyle(hE_fp);
			
			vbox.getChildren().addAll(lblfp, hE_fp);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void create_fetaldopStudies(int pID, String testname) throws SQLException {
		HTMLEditor hE_fds = new HTMLEditor();
		hE_fds.setPrefWidth(700);
		hE_fds.setPrefHeight(400);
		hE_fds.setId("heFetalDS");
		hE_fds.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement(
				"SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_fds.setHtmlText(rs.getString("fetalDoplerStudies"));
		}
		// hide controls we don't need.
		hE_fds=htmlEditorStyle(hE_fds);
		
		vbox.getChildren().addAll(hE_fds);
	}

	public static void create_table1(int pID, String testname) throws SQLException {
		HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefWidth(700);
		hE_table1.setPrefHeight(400);
		hE_table1.setId("hE_tbl1");

		// hide controls we don't need.
		hE_table1=htmlEditorStyle(hE_table1);
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table1"));
		}

		vbox.getChildren().addAll(hE_table1);
	}

	public static void create_table2(int pID, String testname) throws SQLException {
		HTMLEditor hE_table2 = new HTMLEditor();
		hE_table2.setPrefWidth(700);
		hE_table2.setPrefHeight(400);
		hE_table2.setId("hE_tbl2");

		// hide controls we don't need.
		hE_table2=htmlEditorStyle(hE_table2);
		hE_table2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement(
				"SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table2"));
		}

		vbox.getChildren().addAll(hE_table2);
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
