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

public class Test_Template {
	private static Connection con = DBConnectivity.getConnection();;
	private static PreparedStatement ps;
	private ResultSet rs;
	public static AnchorPane contentPane = HeaderController.getPaneTemplate();
	public static VBox vbox = CreateTestTemplate.getVbox();
	public static HTMLEditor history, clinicalImp, fetalParameter, fetaldopStudies, impression, note, table1, table2,
			testDetstails;

	public static void create_testDetails(int pID, String testname) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setPrefHeight(-1);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDescription"));
		}

		// hide controls we don't need.
		Node t_node = he_Testdetails.lookup(".top-toolbar");
		Node b_node = he_Testdetails.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");

		String text = PrintableData.stripHTMLTags(he_Testdetails.getHtmlText());
		if (!(text.equals(""))) {
			vbox.getChildren().add(he_Testdetails);
		}
	}

	public static void create_pastHistory(int pID) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setPrefHeight(-1);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("patientHistory"));
		}
		// hide controls we don't need.
		Node t_node = hE_pHistory.lookup(".top-toolbar");
		Node b_node = hE_pHistory.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");

		vbox.getChildren().addAll(lblphistory, hE_pHistory);
	}

	public static void create_clinicalImp(int pID) throws SQLException {
		Label lblclinicalImp = new Label("CLINICAL IMPRESSION");
		HTMLEditor hE_clinicalImp = new HTMLEditor();
		hE_clinicalImp.setPrefHeight(-1);
		hE_clinicalImp.setPrefWidth(700);
		hE_clinicalImp.setId("hePastHistory");
		hE_clinicalImp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_clinicalImp.setHtmlText(rs.getString("clinicalImpression"));
		}
		// hide controls we don't need.
		Node t_node = hE_clinicalImp.lookup(".top-toolbar");
		Node b_node = hE_clinicalImp.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		vbox.getChildren().addAll(lblclinicalImp, hE_clinicalImp);
	}

	public static void create_fetalParameter(int pID) {
		try {
			Label lblfp = new Label("FETAL PARAMETERS: ");

			HTMLEditor hE_fp = new HTMLEditor();
			hE_fp.setPrefWidth(700);
			hE_fp.setPrefHeight(-1);
			hE_fp.setId("heFetalParameter");
			hE_fp.setStyle("-fx-border-color:white;");

			ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hE_fp.setHtmlText(rs.getString("fetalParameter"));
			}
			// hide controls we don't need.
			Node t_node = hE_fp.lookup(".top-toolbar");
			Node b_node = hE_fp.lookup(".bottom-toolbar");
			t_node.setVisible(false);
			b_node.setVisible(false);
			t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
					+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
					+ "        -fx-opacity: 0;");
			b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
					+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
					+ "        -fx-opacity: 0;");
			vbox.getChildren().addAll(lblfp, hE_fp);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void create_fetaldopStudies(int pID) throws SQLException {
		HTMLEditor hE_fds = new HTMLEditor();
		hE_fds.setPrefWidth(700);
		hE_fds.setPrefHeight(-1);
		hE_fds.setId("heFetalDS");
		hE_fds.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_fds.setHtmlText(rs.getString("fetalDoplerStudies"));
		}
		// hide controls we don't need.
		Node t_node = hE_fds.lookup(".top-toolbar");
		Node b_node = hE_fds.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		vbox.getChildren().addAll(hE_fds);
	}

	public static void create_table1(int pID) throws SQLException {
		HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefWidth(700);
		hE_table1.setPrefHeight(-1);
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

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table1"));
		}

		vbox.getChildren().addAll(hE_table1);
	}

	public static void create_table2(int pID) throws SQLException {
		HTMLEditor hE_table2 = new HTMLEditor();
		hE_table2.setPrefWidth(700);
		hE_table2.setPrefHeight(-1);
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

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table2"));
		}

		vbox.getChildren().addAll(hE_table2);
	}

	public static void create_impression(int pID) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefWidth(700);
		hE_imp.setPrefHeight(-1);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("impression"));
		}
		// hide controls we don't need.
		Node t_node = hE_imp.lookup(".top-toolbar");
		Node b_node = hE_imp.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");

		vbox.getChildren().addAll(lblimp, hE_imp);
	}

	public static void create_note(int tID) throws SQLException {
		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefWidth(700);
		hE_note.setPrefHeight(-1);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_testdetails WHERE tId='" + tID + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("note"));
		}
		// hide controls we don't need.
		Node t_node = hE_note.lookup(".top-toolbar");
		Node b_node = hE_note.lookup(".bottom-toolbar");
		t_node.setVisible(false);
		b_node.setVisible(false);
		t_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_node.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		vbox.getChildren().addAll(lblnote, hE_note);
	}

}
