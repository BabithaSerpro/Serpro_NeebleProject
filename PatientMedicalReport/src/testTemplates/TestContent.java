package testTemplates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import DBConnection.DBConnectivity;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TestContent {
	private static Connection con = DBConnectivity.getConnection();;
	private static PreparedStatement ps;
	public static AnchorPane contentPane = ModifyTDController.getTestContentPane();
	public static VBox vbox = ModifyTDController.getVbox();
	public static VBox vbox1 = EditTestTemplateController.getVbox();
	public static HTMLEditor history, clinicalImp, fetalParameter, fetaldopStudies, impression, note, testDetstails;
	public static HTMLEditor table1,table2;
	public static HTMLEditor etable1, etable2;
	public static HTMLEditor ehistory, eimpression, enote, etestDetstails;
	public static boolean testdetails_deleted=false;
	
	public static void create_testDetails(int tID) throws SQLException {
		HBox hbox=new HBox(2);
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

		Button btnDelete=new Button("Delete");
		btnDelete.setStyle("-fx-background-color: transparent");
		btnDelete.setTextFill(Color.BLUE);
		hbox.getChildren().addAll(he_Testdetails,btnDelete);
		vbox.getChildren().addAll(hbox);
		btnDelete.setOnAction(e->{
			vbox.getChildren().remove(hbox);
			testdetails_deleted=true;
			he_Testdetails.setHtmlText("");
		});
		String text = Test_Template.stripHTMLTags(he_Testdetails.getHtmlText());
		if (!(text.equals(""))) {
			testDetstails = he_Testdetails;
		}
	}

	public static void create_pastHistory(int tID) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefHeight(200);
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_testdetails WHERE tId='" + tID + "' and active='Y' ");
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
		final HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefWidth(700);
		hE_table1.setPrefHeight(500);
		hE_table1.setId("hE_tbl1");
		// hide controls we don't need.
		Test_Template.htmlEditorStyle(hE_table1);
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_tabledetails WHERE testId='" + tID + "' and active='Y' ");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table"));
		}
		vbox.getChildren().addAll(hE_table1);
		//Add buttons
		addRowsCol(hE_table1,vbox);
		if (!hE_table1.equals(null)) {
			table1 = hE_table1;
		}
	}
	public static void create_table2(int tID) throws SQLException {
		final HTMLEditor hE_table2 = new HTMLEditor();
		hE_table2.setPrefWidth(700);
		hE_table2.setPrefHeight(500);
		hE_table2.setId("hE_tbl2");
		// hide controls we don't need.
		Test_Template.htmlEditorStyle(hE_table2);
		hE_table2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_report_tabledetails WHERE testId='" + tID + "' and active='Y'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table2"));
		}
		vbox.getChildren().addAll(hE_table2);
		//Add buttons
		addRowsCol(hE_table2,vbox);
		if (!hE_table2.equals(null)) {
			table2 = hE_table2;
		}
	}
//	public static void create__table1(int tID) throws SQLException, IOException {
//		WebView webview = new WebView();
//		webview.setVisible(true);
//		webview.setId("table1");
//		WebEngine webengine = webview.getEngine();
//		webengine.setJavaScriptEnabled(true);
//		String home = System.getProperty("user.home");
//
//		ps = con.prepareStatement("SELECT * FROM patient_tabledetails WHERE testId='" + tID + "'");
//		ResultSet rs = ps.executeQuery();
//		StringBuilder contentBuilder = new StringBuilder();
//		BufferedReader in = null;
//		while (rs.next()) {
//			String fileLocation = rs.getString("table1");
//			File file = new File(home + "/" + fileLocation);
//			webengine.load(file.toURI().toURL().toString());
//			in = new BufferedReader(new FileReader(file));
//		}
//		String str;
//		while ((str = in.readLine()) != null) {
//			contentBuilder.append(str);
//		}
//
//		contentBuilder.delete(0, contentBuilder.indexOf("</div>") + 6);
//		contentBuilder.delete(contentBuilder.indexOf("<br>"), contentBuilder.length());
//
//		table1 = contentBuilder;
//		vbox.getChildren().add(webview);
//	}

//	public static void create__table2(int tID) throws SQLException, IOException {
//		WebView webview = new WebView();
//		webview.setVisible(true);
//		webview.setId("table2");
//		WebEngine webengine = webview.getEngine();
//		webengine.setJavaScriptEnabled(true);
//		String home = System.getProperty("user.home");
//
//		ps = con.prepareStatement("SELECT * FROM patient_tabledetails WHERE testId='" + tID + "'");
//		ResultSet rs = ps.executeQuery();
//		StringBuilder contentBuilder = new StringBuilder();
//		BufferedReader in = null;
//		while (rs.next()) {
//			String fileLocation = rs.getString("table2");
//			File file = new File(home + "/" + fileLocation);
//			webengine.load(file.toURI().toURL().toString());
//			in = new BufferedReader(new FileReader(file));
//		}
//		String str;
//		while ((str = in.readLine()) != null) {
//			contentBuilder.append(str);
//		}
//		contentBuilder.delete(0, contentBuilder.indexOf("</div>") + 6);
//		contentBuilder.delete(contentBuilder.indexOf("<br>"), contentBuilder.length());
//		table2 = contentBuilder;
//		vbox.getChildren().add(webview);
//	}

	public static void create_impression(int tID) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefHeight(150);
		hE_imp.setPrefWidth(700);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT IMPRESSION FROM patient_testdetails WHERE tId='" + tID + "' and active='Y' ");
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

		ps = con.prepareStatement("SELECT PLEASE_NOTE FROM patient_testdetails WHERE tId='" + tID + "' and active='Y' ");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("PLEASE_NOTE"));
		}
		if (!hE_note.equals(null)) {
			note = hE_note;
		}
		vbox.getChildren().addAll(lblnote, hE_note);
	}

	// =====================EDIT TEST DETAILS=================================
	public static void edit_testDetails(int tID) throws SQLException {

		HTMLEditor he_eTestdetails = new HTMLEditor();
		he_eTestdetails.setPrefHeight(370);
		he_eTestdetails.setPrefWidth(700);
		he_eTestdetails.setId("heETestdetails");
		he_eTestdetails.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "' and active='Y'");
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

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "' and active='Y'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_epHistory.setHtmlText(rs.getString("patientHistory"));
		}
		if (!hE_epHistory.equals(null)) {
			ehistory = hE_epHistory;
		}
		vbox1.getChildren().addAll(lblphistory, hE_epHistory);
	}

	public static void edit_table1(int tID) throws SQLException, IOException {
		final HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefWidth(700);
		hE_table1.setPrefHeight(500);
		hE_table1.setId("hE_tbl1");
		// hide controls we don't need.
		Test_Template.htmlEditorStyle(hE_table1);
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "' and active='Y'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table1"));
		}
		vbox1.getChildren().addAll(hE_table1);
		//Add buttons
		addRowsCol(hE_table1,vbox1);
		if (!hE_table1.equals(null)) {
			etable1 = hE_table1;
		}
	}

	public static void edit_table2(int tID) throws SQLException, IOException {
		final HTMLEditor hE_table2 = new HTMLEditor();
		hE_table2.setPrefWidth(700);
		hE_table2.setPrefHeight(500);
		hE_table2.setId("hE_tbl2");
		// hide controls we don't need.
		Test_Template.htmlEditorStyle(hE_table2);
		hE_table2.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "' and active='Y'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table2.setHtmlText(rs.getString("table2"));
		}
		vbox1.getChildren().addAll(hE_table2);
		//Add buttons
		addRowsCol(hE_table2,vbox1);
		if (!hE_table2.equals(null)) {
			etable1 = hE_table2;
		}
	}

	public static void edit_impression(int tID) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_eimp = new HTMLEditor();
		hE_eimp.setPrefHeight(150);
		hE_eimp.setPrefWidth(700);
		hE_eimp.setId("heEImpression");
		hE_eimp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "' and active='Y'");
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

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "' and active='Y'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_enote.setHtmlText(rs.getString("note"));
		}
		if (!hE_enote.equals(null)) {
			enote = hE_enote;
		}
		vbox1.getChildren().addAll(lblnote, hE_enote);
	}

	// ========================ADD/DELETE ROWS/COLOUMNs===========================================
	public static void addRowsCol(HTMLEditor htmlEditor, VBox vbox) {
		Button btnaddRow = new Button("Add Row");
		Button btnaddCol = new Button("Add Coloumn");
		Button btnDeleteRow = new Button("Delete Row");
		Button btnDeleteCol = new Button("Delete Coloumn");
		btnaddRow.setOnAction(e -> {
			String data = htmlEditor.getHtmlText();
			Document document = Jsoup.parse(data, "", Parser.xmlParser());
			int row = document.select("tr").size();
			int totalCol = document.select("td").size();
			int colRow = totalCol / row;
			String numRow = null;

			if (data.contains("</tbody>")) {
				List<String> rowData = new ArrayList<>();
				for (int i = 0; i < colRow; i++) {
					numRow = "<td></td>";
					rowData.add(numRow);
				}
				String str = rowData.toString();
				if (str.contains("[") && str.contains("]") && str.contains(",")) {
					str = str.replace("[", "");
					str = str.replace("]", "");
					str = str.replace(",", " ");
				}
				data = data.replace("</tbody>", "<tr style='width: 78px; height: 35px;'>" + str + "</tr></tbody>");
			}
			htmlEditor.setHtmlText(data);
		});
		btnaddCol.setOnAction(e -> {
			String st = htmlEditor.getHtmlText();
			if (st.contains("</tr>")) {
				st = st.replace("</tr>", "<td style='width: 78px; height: 35px;'></td></tr>");
			}
			htmlEditor.setHtmlText(st);
		});
		btnDeleteRow.setOnAction(e -> {
			String data = htmlEditor.getHtmlText();
			Document document = Jsoup.parse(data, "", Parser.xmlParser());
			Element lastRow = document.select("tr").last();
			lastRow.remove();
			htmlEditor.setHtmlText(document.children().toString());
		});
		
		btnDeleteCol.setOnAction(e -> {
			String data = htmlEditor.getHtmlText();
			Document document = Jsoup.parse(data, "", Parser.xmlParser());
			Elements lastCol = document.select("tr");
			for (Element element : lastCol) {
				Element eachCol = element.select("td").last();
				eachCol.remove();
			}
			htmlEditor.setHtmlText(document.children().toString());
		});
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(btnaddRow, btnaddCol, btnDeleteRow, btnDeleteCol);
		vbox.getChildren().add(hbox);
	}
}
