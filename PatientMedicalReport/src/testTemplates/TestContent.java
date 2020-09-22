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
	public static HTMLEditor history, clinicalImp, fetalParameter, fetaldopStudies, 
	impression, note, table1, table2,testDetstails;

	public static void create_testDetails(int tID) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefHeight(370);
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDetails"));
		}
		String text = PrintableData.stripHTMLTags(he_Testdetails.getHtmlText());

		
		if (!he_Testdetails.equals(null)) {
			testDetstails = he_Testdetails;

		} else {
			testDetstails.setHtmlText("");		
		}
		if (!(text.equals(""))) {
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

		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("PAST HISTORY"));
		}
		if (!hE_pHistory.equals(null)) {
			history = hE_pHistory;

		} else {

			history.setHtmlText("");

		}
		vbox.getChildren().addAll(lblphistory, hE_pHistory);
	}

	public static void create_clinicalImp(int tID) throws SQLException {
		Label lblclinicalImp = new Label("CLINICAL IMPRESSION");
		HTMLEditor hE_clinicalImp = new HTMLEditor();
		hE_clinicalImp.setPrefHeight(370);
		hE_clinicalImp.setPrefWidth(700);
		hE_clinicalImp.setId("hePastHistory");
		hE_clinicalImp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_clinicalImp.setHtmlText(rs.getString("CLINICAL_IMPRESSION"));
		}
		if (!hE_clinicalImp.equals(null)) {
			clinicalImp = hE_clinicalImp;
		} else {
			clinicalImp.setHtmlText("");
		}
		vbox.getChildren().addAll(lblclinicalImp, hE_clinicalImp);
	}

	public static void create_fetalParameter(int tID) {
		try {
			Label lblfp = new Label("FETAL PARAMETERS: ");

			HTMLEditor hE_fp = new HTMLEditor();
			hE_fp.setPrefHeight(200);
			hE_fp.setPrefWidth(700);
			hE_fp.setId("heFetalParameter");
			hE_fp.setStyle("-fx-border-color:white;");

			ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='" + tID + "'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hE_fp.setHtmlText(rs.getString("FETAL PARAMETER"));
			}
			if (!hE_fp.equals(null)) {
				fetalParameter = hE_fp;
			} else {
				fetalParameter.setHtmlText("");
			}
			vbox.getChildren().addAll(lblfp, hE_fp);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void create_fetaldopStudies(int tID) throws SQLException {
		HTMLEditor hE_fds = new HTMLEditor();
		hE_fds.setPrefHeight(200);
		hE_fds.setPrefWidth(700);
		hE_fds.setId("heFetalDS");
		hE_fds.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_fds.setHtmlText(rs.getString("FETAL DOPPLER STUDIES"));
		}
		if (!hE_fds.equals(null)) {
			fetaldopStudies = hE_fds;
		} else {
			fetaldopStudies.setHtmlText("");
		}
		vbox.getChildren().addAll(hE_fds);
	}

	public static void create_table1(int tID) throws SQLException {
		HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefHeight(450);
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
		if (!hE_table1.equals(null)) {
			table1 = hE_table1;
		} else {
			table1.setHtmlText("");
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
			hE_table2.setHtmlText(rs.getString("table"));
		}
		if (!hE_table2.equals(null)) {
			table2 = hE_table2;
		} else {
			table2.setHtmlText("");
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

		ps = con.prepareStatement("SELECT IMPRESSION FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("IMPRESSION"));
		}
		if (!hE_imp.equals(null)) {
			impression = hE_imp;
		} else {
			impression.setHtmlText("");
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

		ps = con.prepareStatement("SELECT PLEASE_NOTE FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("PLEASE_NOTE"));
		}
		if (!hE_note.equals(null)) {
			note = hE_note;
		} else {
			note.setHtmlText("");
		}
		vbox.getChildren().addAll(lblnote, hE_note);
	}
}
