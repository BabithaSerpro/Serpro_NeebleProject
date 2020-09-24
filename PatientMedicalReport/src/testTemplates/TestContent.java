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
}
