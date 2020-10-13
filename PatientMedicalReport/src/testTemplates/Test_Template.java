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
//	public static VBox vbox = CreateTestTemplate.getVbox();
	public static HTMLEditor history, clinicalImp, fetalParameter, fetaldopStudies, impression, note, table1, table2,
			testDetstails;

	public static void patientreportData(int pID, String testname,int id) {
		try {
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				HeaderController.getRef_doctor().setText(rs.getString("ref_doctor"));
				HeaderController.getTest_date().setText(rs.getString("reportDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void create_testDetails(int pID, String testname,int id, VBox vbox) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setPrefHeight(400);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDescription"));
		}
		String st = he_Testdetails.getHtmlText();
		if (st.contains("<br>")) {
			st = st.replace("<br>", "<br/>");
		}
		if (st.contains("contenteditable=\"true\"")) {
			st = st.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
		}

		he_Testdetails = htmlEditorStyle(he_Testdetails);
		
		Node scPane = he_Testdetails.lookup(".scroll-bar:vertical");
		scPane.setVisible(false);
		
		String text = stripHTMLTags(he_Testdetails.getHtmlText());
//		he_Testdetails.setDisable(true);
		
		if (!(text.equals(""))) {
			vbox.getChildren().add(he_Testdetails);
		}
	}

	public static void create_pastHistory(int pID, String testname,int id, VBox vbox) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setPrefHeight(100);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("patientHistory"));
		}
		// hide controls we don't need.
		hE_pHistory = htmlEditorStyle(hE_pHistory);
//		hE_pHistory.setDisable(true);
		vbox.getChildren().addAll(lblphistory, hE_pHistory);
	}

	public static void create_table1(int pID, String testname,int id,VBox vbox) throws SQLException {
		HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefWidth(700);
		hE_table1.setPrefHeight(500);
		hE_table1.setId("hE_tbl1");
		// hide controls we don't need.
		hE_table1 = htmlEditorStyle(hE_table1);
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table1"));
		}
//		hE_table1.setDisable(true);
		Node scPane = hE_table1.lookup(".scroll-bar:vertical");
		scPane.setVisible(false);
		vbox.getChildren().addAll(hE_table1);
	}

	public static void create_table2(int pID, String testname,int id,VBox vbox) throws SQLException {
		HTMLEditor hE_table2 = new HTMLEditor();
		hE_table2.setPrefWidth(700);
		hE_table2.setPrefHeight(400);
		hE_table2.setId("hE_tbl2");

		// hide controls we don't need.
		hE_table2 = htmlEditorStyle(hE_table2);
		hE_table2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table2"));
		}
//		hE_table2.setDisable(true);
		vbox.getChildren().addAll(hE_table2);
	}

	public static void create_impression(int pID, String testname,int id,VBox vbox) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefWidth(700);
		hE_imp.setPrefHeight(80);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("impression"));
		}
		// hide controls we don't need.
		hE_imp = htmlEditorStyle(hE_imp);
//		hE_imp.setDisable(true);
		vbox.getChildren().addAll(lblimp, hE_imp);
	}

	public static void create_note(int pID, String testname, int id, VBox vbox) throws SQLException {
		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefWidth(700);
		hE_note.setPrefHeight(80);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("note"));
		}

		hE_note = htmlEditorStyle(hE_note);
//		hE_note.setDisable(true);
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
}
