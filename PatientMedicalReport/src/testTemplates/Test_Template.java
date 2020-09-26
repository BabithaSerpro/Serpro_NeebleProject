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

	public static void create_testDetails(int pID, String testname,int id) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDescription"));
		}
		String st = he_Testdetails.getHtmlText();
		if (st.contains("contenteditable=\"true\"")) {
			st = st.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
		}

		he_Testdetails = htmlEditorStyle(he_Testdetails);
		
		Node scPane = he_Testdetails.lookup(".scroll-bar:vertical");
		scPane.setVisible(false);
//		scPane.setDisable(true);
		
		String text = stripHTMLTags(he_Testdetails.getHtmlText());
		he_testDetailsHeight(he_Testdetails, testname);
		he_Testdetails.setDisable(true);
		
		if (!(text.equals(""))) {
			vbox.getChildren().add(he_Testdetails);
		}
	}

	public static void create_pastHistory(int pID, String testname,int id) throws SQLException {
		Label lblphistory = new Label("Relevant past history");
		HTMLEditor hE_pHistory = new HTMLEditor();
		hE_pHistory.setPrefWidth(700);
		hE_pHistory.setId("hePastHistory");
		hE_pHistory.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_pHistory.setHtmlText(rs.getString("patientHistory"));
		}
		// hide controls we don't need.
		hE_pHistory = htmlEditorStyle(hE_pHistory);
		htmlEditorHeight(hE_pHistory, testname);
		hE_pHistory.setDisable(true);
		vbox.getChildren().addAll(lblphistory, hE_pHistory);
	}

	public static void create_table1(int pID, String testname,int id) throws SQLException {
		HTMLEditor hE_table1 = new HTMLEditor();
		hE_table1.setPrefWidth(700);
		hE_table1.setId("hE_tbl1");
		// hide controls we don't need.
		hE_table1 = htmlEditorStyle(hE_table1);
		hE_table1.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_table1.setHtmlText(rs.getString("table1"));
		}
		he_TableHeight(hE_table1,testname);
		hE_table1.setDisable(true);
		Node scPane = hE_table1.lookup(".scroll-bar:vertical");
		scPane.setVisible(false);
		vbox.getChildren().addAll(hE_table1);
	}

	public static void create_table2(int pID, String testname,int id) throws SQLException {
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
		he_TableHeight(hE_table2,testname);
		hE_table2.setDisable(true);
		vbox.getChildren().addAll(hE_table2);
	}

	public static void create_impression(int pID, String testname,int id) throws SQLException {
		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefWidth(700);
		hE_imp.setPrefHeight(150);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_imp.setHtmlText(rs.getString("impression"));
		}
		htmlEditorHeight(hE_imp, testname);
		// hide controls we don't need.
		hE_imp = htmlEditorStyle(hE_imp);
		hE_imp.setDisable(true);
		vbox.getChildren().addAll(lblimp, hE_imp);
	}

	public static void create_note(int pID, String testname, int id) throws SQLException {
		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefWidth(700);
		hE_note.setPrefHeight(100);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");

		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hE_note.setHtmlText(rs.getString("note"));
		}

		hE_note = htmlEditorStyle(hE_note);
		htmlEditorHeight(hE_note, testname);
		hE_note.setDisable(true);
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
			htmleditor.setPrefHeight(1000);
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
			htmleditor.setPrefHeight(700);
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
		int lngth = htmleditor.getHtmlText().length();
		if (lngth > 10 && lngth <= 50) {
			htmleditor.setPrefHeight(10);
		} else if (lngth > 50 && lngth <= 100) {
			htmleditor.setPrefHeight(50);
		} else if (lngth > 100 && lngth <= 200) {
			htmleditor.setPrefHeight(90);
		} else if (lngth > 200 && lngth <= 300) {
			htmleditor.setPrefHeight(100);
		} else if (lngth > 300 && lngth <= 350) {
			htmleditor.setPrefHeight(250);
		} else if (lngth > 350 && lngth <= 400) {
			htmleditor.setPrefHeight(140);
		} else if (lngth > 400 && lngth <= 600) {
			htmleditor.setPrefHeight(430);
		} else if (lngth > 600 && lngth <= 800) {
			htmleditor.setPrefHeight(520);
		} else if (lngth > 900 && lngth <= 1000) {
			htmleditor.setPrefHeight(450);
		} else if (lngth > 1000 && lngth <= 1200) {
			htmleditor.setPrefHeight(550);
		} else if (lngth > 1200 && lngth <= 1400) {
			htmleditor.setPrefHeight(650);
		} else if (lngth > 1400 && lngth <= 1600) {
			htmleditor.setPrefHeight(800);
		} else if (lngth > 1600 && lngth <= 1800) {
			htmleditor.setPrefHeight(1000);
		} else if (lngth > 1800 && lngth <= 2000) {
			htmleditor.setPrefHeight(1100);
		} else if (lngth > 2000 && lngth <= 2200) {
			htmleditor.setPrefHeight(1200);
		}
		return htmleditor;
	}

	public static HTMLEditor he_testDetailsHeight(HTMLEditor htmleditor, String testname) {
		int lngth = htmleditor.getHtmlText().length();
		if (lngth > 200 && lngth <= 350) {
			htmleditor.setPrefHeight(150);
		}else if (lngth > 300 && lngth <= 350) {
			htmleditor.setPrefHeight(150);
		} else if (lngth > 350 && lngth <= 400) {
			htmleditor.setPrefHeight(400);
		} else if (lngth > 400 && lngth <= 470) {
			htmleditor.setPrefHeight(350);
		}else if (lngth > 470 && lngth <= 500) {
			htmleditor.setPrefHeight(300);
		}else if (lngth > 500 && lngth <= 550) {
			htmleditor.setPrefHeight(220);
		}else if (lngth > 550 && lngth <= 600) {
			htmleditor.setPrefHeight(300);
		} else if (lngth > 600 && lngth <= 660) {
			htmleditor.setPrefHeight(450);
		}else if (lngth > 660 && lngth <= 700) {
			htmleditor.setPrefHeight(340);
		} else if (lngth > 700 && lngth <= 750) {
			htmleditor.setPrefHeight(420);
		} else if (lngth > 750 && lngth <= 800) {
			htmleditor.setPrefHeight(500);
		} else if (lngth > 800 && lngth <= 900) {
			htmleditor.setPrefHeight(440);
		} else if (lngth > 900 && lngth <= 950) {
			htmleditor.setPrefHeight(450);
		} else if (lngth > 950 && lngth <= 1000) {
			htmleditor.setPrefHeight(520);
		} else if (lngth > 1000 && lngth <= 1100) {
			htmleditor.setPrefHeight(540);
		} else if (lngth > 1100 && lngth <= 1200) {
			htmleditor.setPrefHeight(510);
		} else if (lngth > 1200 && lngth <= 1300) {
			htmleditor.setPrefHeight(530);
		} else if (lngth > 1300 && lngth <= 1400) {
			htmleditor.setPrefHeight(500);
		} else if (lngth > 1400 && lngth <= 1440) {
			htmleditor.setPrefHeight(530);
		}else if (lngth > 1440 && lngth <= 1500) {
			htmleditor.setPrefHeight(650);
		} else if (lngth > 1500 && lngth <= 1600) {
			htmleditor.setPrefHeight(540);
		} else if (lngth > 1600 && lngth <= 1700) {
			htmleditor.setPrefHeight(550);
		} else if (lngth > 1800 && lngth <= 1900) {
			htmleditor.setPrefHeight(560);
		} else if (lngth > 1900 && lngth <= 2000) {
			htmleditor.setPrefHeight(570);
		} else if (lngth > 2100 && lngth <= 2200) {
			htmleditor.setPrefHeight(580);
		} else if (lngth > 2200 && lngth <= 2300) {
			htmleditor.setPrefHeight(590);
		} else if (lngth > 2300 && lngth <= 2400) {
			htmleditor.setPrefHeight(600);
		} else if (lngth > 2400 && lngth <= 2500) {
			htmleditor.setPrefHeight(630);
		} else if (lngth > 2500 && lngth <= 2600) {
			htmleditor.setPrefHeight(640);
		} else if (lngth > 2600 && lngth <= 2700) {
			htmleditor.setPrefHeight(650);
		} else if (lngth > 2800 && lngth <= 2900) {
			htmleditor.setPrefHeight(660);
		} else if (lngth > 2900 && lngth <= 3000) {
			htmleditor.setPrefHeight(670);
		} else if (lngth > 3100 && lngth <= 3200) {
			htmleditor.setPrefHeight(680);
		} else if (lngth > 3200 && lngth <= 3300) {
			htmleditor.setPrefHeight(620);
		} else if (lngth > 3300 && lngth <= 3400) {
			htmleditor.setPrefHeight(690);
		} else if (lngth > 3400 && lngth <= 3500) {
			htmleditor.setPrefHeight(700);
		} else if (lngth > 3500 && lngth <= 3600) {
			htmleditor.setPrefHeight(710);
		} else if (lngth > 3600 && lngth <= 3700) {
			htmleditor.setPrefHeight(720);
		} else if (lngth > 3800 && lngth <= 3900) {
			htmleditor.setPrefHeight(730);
		} else if (lngth > 3900 && lngth <= 4000) {
			htmleditor.setPrefHeight(740);
		}
		return htmleditor;
	}
	
	public static HTMLEditor he_TableHeight(HTMLEditor htmleditor, String testname) {
		switch (testname) {
		case "COLOUR DOPPLER OBSTRETIC":
			htmleditor.setPrefHeight(600);
			break;
		case "FOLLICULAR STUDY":
			htmleditor.setPrefHeight(600);
			break;
		case "ARTERIAL DOPPLER OF BOTH LOWER LIMBS":
			htmleditor.setPrefHeight(500);
			break;
		case "ARTERIAL DOPPLER BOTH UPPER LIMBS":
			htmleditor.setPrefHeight(500);
			break;
		case "COLOUR DOPPLER OF  CAROTID AND VERTEBRAL ARTERIES":
			htmleditor.setPrefHeight(700);
			break;
		case "RENAL COLOR DOPPLER":
			htmleditor.setPrefHeight(650);
			break;
		case "CAROTID AND RADIAL COLOUR  DOPPLER":
			htmleditor.setPrefHeight(600);
			break;
		case "COLOR DOPPLER EXAMINATION  OF ABDOMINAL VESSELS":
			htmleditor.setPrefHeight(600);
			break;
		case "COLOR DOPPLER EXAMINATION OF LEFT SUBCLAVIAN  ARTERY":
			htmleditor.setPrefHeight(370);
			break;
		case "COLOR DOPPLER EXAMINATION OF NECK VESSELS":
			if(htmleditor.getId().equals("hE_tbl1")) {
				htmleditor.setPrefHeight(550);
			}else {
				htmleditor.setPrefHeight(600);
			}
			break;
		case "COLOR DOPPLER OF FEMALE PELVIS":
			htmleditor.setPrefHeight(280);
			break;
		case "TRANSCRANIAL COLOR  DOPPLER  EXAMINATION":
			htmleditor.setPrefHeight(760);
			break;
		}
		return htmleditor;
	}

}
