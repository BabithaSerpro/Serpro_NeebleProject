package testTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import DBConnection.DBConnectivity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class TestContent {
	private static Connection con = DBConnectivity.getConnection();;
	private static PreparedStatement ps;
	private ResultSet rs;
	public static AnchorPane contentPane=Test_Screens.getContentPane();
	public static VBox vbox=Test_Screens.getVbox();
	
	public static void create_pastHistory(int tID) throws SQLException {
		Label lblphistory=new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefHeight(370);
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");
		
		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("PAST HISTORY"));
		}
		
		vbox.getChildren().addAll(lblphistory,hE_pHistory);
	}
	
	public static void create_clinicalImp(int tID) throws SQLException {
		Label lblclinicalImp=new Label("CLINICAL IMPRESSION");
		HTMLEditor hE_clinicalImp = new HTMLEditor();
		hE_clinicalImp.setPrefHeight(370);
		hE_clinicalImp.setPrefWidth(700);
		hE_clinicalImp.setId("hePastHistory");
		hE_clinicalImp.setStyle("-fx-border-color:white;");
		
		ps = con.prepareStatement("SELECT CLINICAL_IMPRESSION FROM patient_report_testdetails WHERE tId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_clinicalImp.setHtmlText(rs.getString("CLINICAL_IMPRESSION"));
		}
		vbox.getChildren().addAll(lblclinicalImp,hE_clinicalImp);
	}
	
	public static void create_fetalParameter(int tID) {
		try {
			Label lblfp=new Label("FETAL PARAMETERS: ");
			
			HTMLEditor hE_fp = new HTMLEditor();
			hE_fp.setPrefHeight(200);
			hE_fp.setPrefWidth(700);
			hE_fp.setId("heFetalParameter");
			hE_fp.setStyle("-fx-border-color:white;");
			
			ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='"+tID+"'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hE_fp.setHtmlText(rs.getString("FETAL PARAMETER"));
			}
			vbox.getChildren().addAll(lblfp,hE_fp);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void create_fetaldopStudies(int tID) throws SQLException {
		HTMLEditor hE_fds = new HTMLEditor();
		hE_fds.setPrefHeight(200);
		hE_fds.setPrefWidth(700);
		hE_fds.setId("heFetalDS");
		hE_fds.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_fds.setHtmlText(rs.getString("FETAL DOPPLER STUDIES"));
		}
		vbox.getChildren().addAll(hE_fds);
	}
	
	public static void create_table1(int tID) throws SQLException {
		HTMLEditor hE_table1= new HTMLEditor();
		hE_table1.setPrefHeight(700);
		hE_table1.setPrefWidth(700);
		hE_table1.setId("hE_tbl1");
		
		// hide controls we don't need.
		Node t_node = hE_table1.lookup(".top-toolbar");
		Node b_node = hE_table1.lookup(".bottom-toolbar");		
		t_node.setVisible(false);
		b_node.setVisible(false);
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_tabledetails WHERE testId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table"));
		}
		vbox.getChildren().addAll(hE_table1);
	}
	
	public static void create_table2(int tID) throws SQLException {
		HTMLEditor hE_table2= new HTMLEditor();
		hE_table2.setPrefHeight(700);
		hE_table2.setPrefWidth(700);
		hE_table2.setId("hE_tbl2");
		
		// hide controls we don't need.
		Node t_node = hE_table2.lookup(".top-toolbar");
		Node b_node = hE_table2.lookup(".bottom-toolbar");		
		t_node.setVisible(false);
		b_node.setVisible(false);
		hE_table2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_tabledetails WHERE testId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table"));
		}
		vbox.getChildren().addAll(hE_table2);
	}
	
	public static void create_impression(int tID) throws SQLException {
		Label lblimp=new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefHeight(150);
		hE_imp.setPrefWidth(700);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");
		
		ps = con.prepareStatement("SELECT IMPRESSION FROM patient_report_testdetails WHERE tId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("IMPRESSION"));
		}
		vbox.getChildren().addAll(lblimp,hE_imp);
	}
	public static void create_note(int tID) throws SQLException{
		Label lblnote=new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefHeight(150);
		hE_note.setPrefWidth(700);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");
		
		ps = con.prepareStatement("SELECT PLEASE_NOTE FROM patient_report_testdetails WHERE tId='"+tID+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("PLEASE_NOTE"));
		}
		vbox.getChildren().addAll(lblnote,hE_note);
	}
}
