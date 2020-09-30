package testTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.DBConnectivity;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class TestContent {
	private static Connection con = DBConnectivity.getConnection();;
	private static PreparedStatement ps;
	public static AnchorPane contentPane = EditTDController.getTestContentPane();
	public static VBox vbox = EditTDController.getVbox();
	public static VBox vbox1 = EditTestTemplateController.getVbox();
	public static HTMLEditor history, clinicalImp, fetalParameter, fetaldopStudies, 
	impression, note, table1, table2,testDetstails;
	public static HTMLEditor ehistory, eimpression, enote, etable1, etable2,etestDetstails;

	public static void create_testDetails(int tID) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefHeight(370);
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDetails"));
		}
		
		String text = Test_Template.stripHTMLTags(he_Testdetails.getHtmlText());
		if (!(text.equals(""))) {
			testDetstails = he_Testdetails;
			vbox.getChildren().addAll(he_Testdetails);
		}
	}

	public static void create_pastHistory(int tID) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefHeight(200);
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("PAST HISTORY"));
		}
		if (!hE_pHistory.equals(null)) {
			history = hE_pHistory;
		} 
		vbox.getChildren().addAll(lblphistory, hE_pHistory);
	}

	public static void create_table1(int tID) throws SQLException {
		HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefHeight(600);
		hE_table1.setPrefWidth(700);
		hE_table1.setId("hE_tbl1");

		// hide controls we don't need.
		Node t_node = hE_table1.lookup(".top-toolbar");
		Node b_node = hE_table1.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_tabledetails WHERE testId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table"));
		}
		String st = hE_table1.getHtmlText();
		if (st.contains("</table>")) {
			st=st.replace("</table>", "<button type=\"button\" onclick=\"appendColumn()\">Add Column</button>");
			hE_table1.setHtmlText(st);
			System.out.println(st);
		}
		if (!hE_table1.equals(null)) {
			table1 = hE_table1;
		} 
		vbox.getChildren().addAll(hE_table1);
	}
	
	public static void create_table2(int tID) throws SQLException {
		HTMLEditor hE_table2 = new HTMLEditor();
		hE_table2.setPrefHeight(400);
		hE_table2.setPrefWidth(700);
		hE_table2.setId("hE_tbl2");

		// hide controls we don't need.
		Node t_node = hE_table2.lookup(".top-toolbar");
		Node b_node = hE_table2.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		hE_table2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_tabledetails WHERE testId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table2"));
		}
		if (!hE_table2.equals(null)) {
			table2 = hE_table2;
		} 
		vbox.getChildren().addAll(hE_table2);
	}
	
	public static void create_impression(int tID) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefHeight(150);
		hE_imp.setPrefWidth(700);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT IMPRESSION FROM patient_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("IMPRESSION"));
		}
		if (!hE_imp.equals(null)) {
			impression = hE_imp;
		} 
		vbox.getChildren().addAll(lblimp, hE_imp);
	}

	public static void create_note(int tID) throws SQLException {
		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefHeight(150);
		hE_note.setPrefWidth(700);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT PLEASE_NOTE FROM patient_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("PLEASE_NOTE"));
		}
		if (!hE_note.equals(null)) {
			note = hE_note;
		}
		vbox.getChildren().addAll(lblnote, hE_note);
	}
	//======================================================
	public static void edit_testDetails(int tID) throws SQLException {

		HTMLEditor he_eTestdetails = new HTMLEditor();
		he_eTestdetails.setPrefHeight(370);
		he_eTestdetails.setPrefWidth(700);
		he_eTestdetails.setId("heETestdetails");
		he_eTestdetails.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_eTestdetails.setHtmlText(rs.getString("testDescription"));
		}
		String text = Test_Template.stripHTMLTags(he_eTestdetails.getHtmlText());
		if (!(text.equals(""))) {
			etestDetstails = he_eTestdetails;
			vbox1.getChildren().addAll(he_eTestdetails);
		}
	}
	
	public static void edit_pastHistory(int tID) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_epHistory = new HTMLEditor();
		hE_epHistory.setPrefHeight(200);
		hE_epHistory.setPrefWidth(700);
		hE_epHistory.setId("heEPastHistory");
		hE_epHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_epHistory.setHtmlText(rs.getString("patientHistory"));
		}
		if (!hE_epHistory.equals(null)) {
			ehistory = hE_epHistory;
		} 
		vbox1.getChildren().addAll(lblphistory, hE_epHistory);
	}
	
	public static void edit_table1(int tID) throws SQLException {
		HTMLEditor hE_etable1 = new HTMLEditor();
		hE_etable1.setPrefHeight(600);
		hE_etable1.setPrefWidth(700);
		hE_etable1.setId("hE_Etbl1");

		// hide controls we don't need.
		Node t_node = hE_etable1.lookup(".top-toolbar");
		Node b_node = hE_etable1.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		hE_etable1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_etable1.setHtmlText(rs.getString("table1"));
		}
		if (!hE_etable1.equals(null)) {
			etable1 = hE_etable1;
		} 
		vbox1.getChildren().addAll(hE_etable1);
	}
	
	public static void edit_table2(int tID) throws SQLException {
		HTMLEditor hE_etable2 = new HTMLEditor();
		hE_etable2.setPrefHeight(400);
		hE_etable2.setPrefWidth(700);
		hE_etable2.setId("hE_Etbl2");

		// hide controls we don't need.
		Node t_node = hE_etable2.lookup(".top-toolbar");
		Node b_node = hE_etable2.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		hE_etable2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_etable2.setHtmlText(rs.getString("table2"));
		}
		if (!hE_etable2.equals(null)) {
			etable2 = hE_etable2;
		} 
		vbox1.getChildren().addAll(hE_etable2);
	}
	
	public static void edit_impression(int tID) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_eimp = new HTMLEditor();
		hE_eimp.setPrefHeight(150);
		hE_eimp.setPrefWidth(700);
		hE_eimp.setId("heEImpression");
		hE_eimp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_eimp.setHtmlText(rs.getString("impression"));
		}
		if (!hE_eimp.equals(null)) {
			eimpression = hE_eimp;
		} 
		vbox1.getChildren().addAll(lblimp, hE_eimp);
	}
	
	public static void edit_note(int tID) throws SQLException {
		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_enote = new HTMLEditor();
		hE_enote.setPrefHeight(150);
		hE_enote.setPrefWidth(700);
		hE_enote.setId("heENote");
		hE_enote.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_enote.setHtmlText(rs.getString("note"));
		}
		if (!hE_enote.equals(null)) {
			enote = hE_enote;
		}
		vbox1.getChildren().addAll(lblnote, hE_enote);
	}
}
