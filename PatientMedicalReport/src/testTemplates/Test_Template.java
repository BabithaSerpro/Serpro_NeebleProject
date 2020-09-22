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
	

	public static void COLOUR_DOPPLER_OBSTRETIC(int pID, String testname) throws SQLException {
		HTMLEditor he_Testdetails = new HTMLEditor();
		he_Testdetails.setPrefHeight(500);
		he_Testdetails.setPrefWidth(700);
		he_Testdetails.setId("heTestdetails");
		he_Testdetails.setStyle("-fx-border-color:white;");
		// hide controls we don't need.
		Node t_TDnode = he_Testdetails.lookup(".top-toolbar");
		Node b_TDnode = he_Testdetails.lookup(".bottom-toolbar");
		t_TDnode.setVisible(false);
		b_TDnode.setVisible(false);
		t_TDnode.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_TDnode.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");

		String text = PrintableData.stripHTMLTags(he_Testdetails.getHtmlText());

		Label lblimp = new Label("Impression: ");
		HTMLEditor hE_imp = new HTMLEditor();
		hE_imp.setPrefHeight(150);
		hE_imp.setPrefWidth(700);
		hE_imp.setId("heImpression");
		hE_imp.setStyle("-fx-border-color:white;");
		// hide controls we don't need.
		Node t_Inode = hE_imp.lookup(".top-toolbar");
		Node b_Inode = hE_imp.lookup(".bottom-toolbar");
		t_Inode.setVisible(false);
		b_Inode.setVisible(false);
		t_Inode.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_Inode.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");

		Label lblnote = new Label("Please Note: ");
		HTMLEditor hE_note = new HTMLEditor();
		hE_note.setPrefHeight(150);
		hE_note.setPrefWidth(700);
		hE_note.setId("heNote");
		hE_note.setStyle("-fx-border-color:white;");

		Node t_Nnode = hE_note.lookup(".top-toolbar");
		Node b_Nnode = hE_note.lookup(".bottom-toolbar");
		t_Nnode.setVisible(false);
		b_Nnode.setVisible(false);
		t_Nnode.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		b_Nnode.setStyle("-fx-max-width: 0px;\r\n" + "	-fx-min-width: 0px;\r\n" + "	-fx-pref-width: 0px;\r\n"
				+ "	-fx-max-height: 0px;\r\n" + "	-fx-min-height: 0px;\r\n" + "	-fx-pref-height: 0px;\r\n"
				+ "        -fx-opacity: 0;");
		
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pID + "' AND testName='" + testname + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			he_Testdetails.setHtmlText(rs.getString("testDescription"));
			hE_imp.setHtmlText(rs.getString("impression"));
			hE_note.setHtmlText(rs.getString("note"));
		}
		
		vbox.getChildren().addAll(he_Testdetails, lblimp, hE_imp, lblnote, hE_note);

	}
	public static void FOLLICULAR_STUDY(int pID, String testname) {
		
	}
//	FOLLICULAR STUDY
//	USG OF PELVIS (VERY EARLY STAGE PREGNANCY)
//	ULTRASONOGRAPHY OF BOTH BREAST
//	USG  OBSTRETIC ANOMALY SCAN
//	USG ABDOMEN AND PELVIS - RUS
//	USG ABDOMEN AND PELVIS - FEMALE
//	USG ABDOMEN AND PELVIS - MALE BPH
//	USG ABDOMEN AND PELVIS BKS
//	USG ABDOMEN AND PELVIS MALE
//	USG ABDOMEN
//	USG CHEST
//	USG GROIN (THIGH)
//	USG KUB BKS
//	USG KUB
//	USG  OBSTRETIC TWINS
//	USG OF OBSTETRIC NT SCAN (I TRIMESTER ANOMALY SCAN)
//	USG  OBSTRETIC 4-9 MTH
//	USG OBSTRETIC EARLY PREGNANCY
//	USG OF SKULL
//	USG OF THYROID
//	USG OF PELVIS (FEMALE)
//	USG OF SCROTUM 
//	USG OF SMALL PART
//	VENOUS DOPPLER OF BOTH LOWER LIMBS
//	VENOUS DOPPLER OF BOTH UPPER LIMBS
//	ARTERIAL DOPPLER OF BOTH LOWER LIMBS
//	ARTERIAL DOPPLER BOTH UPPER LIMBS
//	COLOUR DOPPLER OF  CAROTID AND VERTEBRAL ARTERIES
//	COLOR DOPPLER EXAMINATION OF SCROTUM
//	RENAL COLOR DOPPLER
//	SCROTUM COLOR DOPPLER
//	3D ULTRASONOGRAPHY OF PELVIS
//	CAROTID AND RADIAL COLOUR  DOPPLER
//	COLOR DOPPLER EXAMINATION  OF ABDOMINAL VESSELS
//	COLOR DOPPLER EXAMINATION  OF SUPERIOR MESENTRIC  ARTERY
//	COLOR DOPPLER EXAMINATION OF LEFT SUBCLAVIAN  ARTERY
//	COLOR DOPPLER EXAMINATION OF NECK VESSELS
//	COLOR DOPPLER EXAMINATION OF UPPER EXTREMITY VENOUS CIRCULATION
//	COLOR DOPPLER OF FEMALE PELVIS
//	PENILE COLOR DOPPLER
//	TRANSCRANIAL COLOR  DOPPLER  EXAMINATION
}
