package testTemplates;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.text.StyledEditorKit.AlignmentAction;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import DBConnection.DBConnectivity;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.scene.web.HTMLEditor;
import javafx.stage.StageStyle;

public class PrintableData {
	public static int p_id;
	public static String p_name, test_name;
	private static String path;
	private static Connection con;
	private static PreparedStatement ps;
	private static int flag;
	public static Button btn_print = CreateTestTemplate.getBtnPrint();

	public static String test_details, past_history, table1, table2, impression, note;
	public static int tID;

	public static Font normalFontBlack = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL);

	public static int count;

	public static int increaseCount(int p_id, String test_name) throws SQLException {
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + p_id + "' AND testName='"
				+ test_name + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			count = rs.getInt("id");
		}
		return count;
	}

	public static void generatePdfReport(String testname, int pid, int id, VBox vbox) throws Exception {
		p_id = Integer.valueOf(HeaderController.getP_id().getText());
		p_name = HeaderController.getP_name().getText();
		test_name = testname;
		count = increaseCount(p_id, test_name);

		String home = System.getProperty("user.home");
		String path_savefile = home + "/Desktop/PatientReports/" + java.time.LocalDate.now().getYear();
		File f = new File(path_savefile);
		if (!f.isDirectory()) {
			boolean success = f.mkdirs();
			if (success) {
				path = f + "/" + p_id + "_" + p_name + "_" + test_name + "_" + count + ".pdf";
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Something went wrong..!!Please try again");
				alert.showAndWait();
			}
		} else {
			path = f + "/" + p_id + "_" + p_name + "_" + test_name + "_" + count + ".pdf";
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 30, 20, 120, 100);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
		writer.setPageEvent(event);
		writer.setPageSize(PageSize.A4);

		document.open();
		addPatientDetails(document, pid, id);
		addPatientTestReport(document, pid, id,writer);
		document.close();
	}

	public static void addPatientDetails(Document document, int pid, int id) throws SQLException {
		try {
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement("SELECT * FROM patient_masterdata WHERE patient_id='" + pid + "'");
			ResultSet rs = ps.executeQuery();

			PdfPTable titleHeader = new PdfPTable(2);
			titleHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			while (rs.next()) {
				PdfPTable t1 = new PdfPTable(2);
				t1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				PdfPCell pnameCell = new PdfPCell();
				Paragraph paneParagraph = new Paragraph();
				paneParagraph.add(new Chunk("Patient Name", normalFontBlack));
				pnameCell.addElement(paneParagraph);
				pnameCell.setBorder(Rectangle.NO_BORDER);
				t1.addCell(pnameCell);

				PdfPCell pnameValueCell = new PdfPCell();
				Paragraph pnameValueParagraph = new Paragraph();
				pnameValueParagraph.add(new Chunk(rs.getString("patient_name"), normalFontBlack));
				pnameValueCell.addElement(pnameValueParagraph);
				pnameValueCell.setBorder(Rectangle.NO_BORDER);
				t1.addCell(pnameValueCell);

				AddRefDoctor(t1, pid, id);

				PdfPCell regnumCell = new PdfPCell();
				Paragraph regnumParagraph = new Paragraph();
				regnumParagraph.add(new Chunk("Reg. No", normalFontBlack));
				regnumCell.addElement(regnumParagraph);
				regnumCell.setBorder(Rectangle.NO_BORDER);
				t1.addCell(regnumCell);

				PdfPCell regnumValueCell = new PdfPCell();
				Paragraph regnumValueParagraph = new Paragraph();
				regnumValueParagraph.add(new Chunk(String.valueOf(pid), normalFontBlack));
				regnumValueCell.addElement(regnumValueParagraph);
				regnumValueCell.setBorder(Rectangle.NO_BORDER);
				t1.addCell(regnumValueCell);
				titleHeader.addCell(t1);

				PdfPTable t2 = new PdfPTable(2);
				t2.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				PdfPCell ageCell = new PdfPCell();
				Paragraph ageParagraph = new Paragraph();
				ageParagraph.add(new Chunk("Age", normalFontBlack));
				ageCell.addElement(ageParagraph);
				ageCell.setBorder(Rectangle.NO_BORDER);
				t2.addCell(ageCell);

				PdfPCell ageValueCell = new PdfPCell();
				Paragraph ageValueParagraph = new Paragraph();
				ageValueParagraph.add(new Chunk(rs.getString("age"), normalFontBlack));
				ageValueCell.addElement(ageValueParagraph);
				ageValueCell.setBorder(Rectangle.NO_BORDER);
				t2.addCell(ageValueCell);

				PdfPCell genderCell = new PdfPCell();
				Paragraph genderParagraph = new Paragraph();
				genderParagraph.add(new Chunk("Gender", normalFontBlack));
				genderCell.addElement(genderParagraph);
				genderCell.setBorder(Rectangle.NO_BORDER);
				t2.addCell(genderCell);

				PdfPCell genderValueCell = new PdfPCell();
				Paragraph genderValueParagraph = new Paragraph();
				genderValueParagraph.add(new Chunk(rs.getString("gender"), normalFontBlack));
				genderValueCell.addElement(genderValueParagraph);
				genderValueCell.setBorder(Rectangle.NO_BORDER);
				t2.addCell(genderValueCell);
				AddReportDate(t2, pid, id);

				titleHeader.addCell(t2);
			}
			document.add(titleHeader);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		}
	}

	private static void AddRefDoctor(PdfPTable t1, int pid, int id) throws SQLException {
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pid + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			PdfPCell refdocCell = new PdfPCell();
			Paragraph refdocParagraph = new Paragraph();
			refdocParagraph.add(new Chunk("Ref. Doctor", normalFontBlack));
			refdocCell.addElement(refdocParagraph);
			refdocCell.setBorder(Rectangle.NO_BORDER);
			t1.addCell(refdocCell);

			PdfPCell prefdocValueCell = new PdfPCell();
			Paragraph prefdocValueParagraph = new Paragraph();
			prefdocValueParagraph.add(new Chunk(rs.getString("ref_doctor"), normalFontBlack));
			prefdocValueCell.addElement(prefdocValueParagraph);
			prefdocValueCell.setBorder(Rectangle.NO_BORDER);
			t1.addCell(prefdocValueCell);
		}
	}

	private static void AddReportDate(PdfPTable t2, int pid, int id) throws SQLException {
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pid + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			PdfPCell visitdateCell = new PdfPCell();
			Paragraph visitdateParagraph = new Paragraph();
			visitdateParagraph.add(new Chunk("Visit Date", normalFontBlack));
			visitdateCell.addElement(visitdateParagraph);
			visitdateCell.setBorder(Rectangle.NO_BORDER);
			t2.addCell(visitdateCell);

			PdfPCell visitdateValueCell = new PdfPCell();
			Paragraph visitdateValueParagraph = new Paragraph();
			visitdateValueParagraph.add(new Chunk(rs.getString("reportDate"), normalFontBlack));
			visitdateValueCell.addElement(visitdateValueParagraph);
			visitdateValueCell.setBorder(Rectangle.NO_BORDER);
			t2.addCell(visitdateValueCell);
		}
	}

	public static void addPatientTestReport(Document document, int pid, int id, PdfWriter writer) throws SQLException, IOException, DocumentException {
		con = DBConnectivity.getConnection();
		ps = con.prepareStatement("SELECT * FROM patient_reportmasterdata WHERE regNumber='" + pid + "' AND id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Paragraph testnameParagraph = new Paragraph();
			testnameParagraph.add(new Chunk(rs.getString("testName"), normalFontBlack));
			testnameParagraph.setAlignment(Element.ALIGN_CENTER);
			document.add(testnameParagraph);
			if(!(rs.getString("patientHistory").equals(""))){
				document.add( Chunk.NEWLINE );
				Paragraph psParagraph = new Paragraph();
				psParagraph.add(new Chunk("Past History", normalFontBlack));
				document.add(psParagraph);
				StringBuilder htmlPH = new StringBuilder();
				htmlPH.append(new String(rs.getString("patientHistory")));
				InputStream is = new ByteArrayInputStream(htmlPH.toString().getBytes());
		        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			}
			
			if(!(rs.getString("testDescription").equals(""))){
				document.add( Chunk.NEWLINE );
				StringBuilder htmlTD = new StringBuilder();
				htmlTD.append(new String(rs.getString("testDescription")));
				InputStream is = new ByteArrayInputStream(htmlTD.toString().getBytes());
		        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			}
			
			if(!(rs.getString("table1").equals(""))){
				document.add( Chunk.NEWLINE );
				StringBuilder htmlTbl1 = new StringBuilder();
				htmlTbl1.append(new String(rs.getString("table1")));
				InputStream is = new ByteArrayInputStream(htmlTbl1.toString().getBytes());
		        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			}
			
			if(!(rs.getString("table2").equals(""))){
				document.add( Chunk.NEWLINE );
				StringBuilder htmlTbl2 = new StringBuilder();
				htmlTbl2.append(new String(rs.getString("table2")));
				InputStream is = new ByteArrayInputStream(htmlTbl2.toString().getBytes());
		        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			}
			if(!(rs.getString("impression").equals(""))){
				document.add( Chunk.NEWLINE );
				Paragraph impParagraph = new Paragraph();
				impParagraph.add(new Chunk("Impression", normalFontBlack));
				document.add(impParagraph);
				StringBuilder htmlImp = new StringBuilder();
				htmlImp.append(new String(rs.getString("impression")));
				InputStream is = new ByteArrayInputStream(htmlImp.toString().getBytes());
		        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			}
			if(!(rs.getString("note").equals(""))){
				document.add( Chunk.NEWLINE );
				Paragraph noteParagraph = new Paragraph();
				noteParagraph.add(new Chunk("Note", normalFontBlack));
				document.add(noteParagraph);
				StringBuilder htmlNt = new StringBuilder();
				htmlNt.append(new String(rs.getString("note")));
				InputStream is = new ByteArrayInputStream(htmlNt.toString().getBytes());
		        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			}
		}
	}

	public static void downloadReport(ActionEvent event, String testname, AnchorPane viewReportpane, VBox vbox) {
		try {
			p_id = Integer.valueOf(HeaderController.getP_id().getText());
			p_name = HeaderController.getP_name().getText();
			test_name = testname;
			btn_print.setVisible(false);
			double pixelScale = 2.0;
			WritableImage writableImage = new WritableImage(
					(int) Math.rint(pixelScale * viewReportpane.getWidth()) - 161,
					(int) Math.rint(pixelScale * viewReportpane.getHeight()));
			SnapshotParameters spa = new SnapshotParameters();
			spa.setTransform(Transform.scale(pixelScale, pixelScale));
			WritableImage snapshot = viewReportpane.snapshot(spa, writableImage);

//			for (int i = 0; i < vbox.getChildren().size(); i++) {
//				if(vbox.getChildren().get(i).getTypeSelector().equals("HTMLEditor")) {
//					HTMLEditor htmleditor=(HTMLEditor) vbox.getChildren().get(i);
//					
//					double pixelScale1 = 2.0;
//					WritableImage writableImage1 = new WritableImage(
//							(int) Math.rint(pixelScale * htmleditor.getWidth()),
//							(int) Math.rint(pixelScale * htmleditor.getHeight()));
//					SnapshotParameters spa1 = new SnapshotParameters();
//					spa.setTransform(Transform.scale(pixelScale, pixelScale));
//					WritableImage snapshot1 = htmleditor.snapshot(spa1, writableImage);
//					String home = System.getProperty("user.home");
//					File output1 = new File(home + "/Downloads/snapshot" + i + ".png");
//					ImageIO.write(SwingFXUtils.fromFXImage(snapshot1, null), "png", output1);
//				}
//			}
			String home = System.getProperty("user.home");
			File output = new File(home + "/Downloads/snapshot.png");

			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);

			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			PDImageXObject pdimage;
			PDPageContentStream content;

			pdimage = PDImageXObject.createFromFile(home + "/Downloads/snapshot.png", doc);
			content = new PDPageContentStream(doc, page);
			PDRectangle box = page.getMediaBox();
			double factor = Math.min(box.getWidth() / snapshot.getWidth(), box.getHeight() / snapshot.getHeight());
			float height = (float) (snapshot.getHeight() * factor);

			content.drawImage(pdimage, 28, box.getHeight() - height - 5, (float) (snapshot.getWidth() * factor),
					height - 5);
			content.close();
			doc.addPage(page);

			count = increaseCount(p_id, test_name);

			String path_savefile = home + "/Desktop/PatientReports/" + java.time.LocalDate.now().getYear();
			File f = new File(path_savefile);
			if (!f.isDirectory()) {
				boolean success = f.mkdirs();
				if (success) {
					doc.save(f + "/" + p_id + "_" + p_name + "_" + test_name + "_" + count + ".pdf");
					path = f + "/" + p_id + "_" + p_name + "_" + test_name + "_" + count + ".pdf";
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Dr. Subodh App");
					alert.initStyle(StageStyle.TRANSPARENT);
					alert.setContentText("Something went wrong..!!Please try again");
					alert.showAndWait();
				}
			} else {
				doc.save(f + "/" + p_id + "_" + p_name + "_" + test_name + "_" + count + ".pdf");
				path = f + "/" + p_id + "_" + p_name + "_" + test_name + "_" + count + ".pdf";
			}

			doc.close();
			output.delete();

			// Save the folder path to DB
			String update = "UPDATE patient_reportmasterdata SET active='Y', folderPath='" + path
					+ "' WHERE regNumber=?";
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement(update);
			ps.setInt(1, p_id);
			flag = ps.executeUpdate();

			if (flag > 0) { // redirecting to dashboard
				btn_print.setVisible(true);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Dr. Subodh App");
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.getButtonTypes().setAll(ButtonType.OK);
				alert.getDialogPane().setHeaderText("File Saved!!");
				alert.showAndWait().ifPresent(bt -> {
					if (bt == ButtonType.OK) {
						CreateTestTemplate.getReportScreen().close();
					}
				});
			}
		} catch (Exception e) { // catching exception if any backend error occurs
			e.printStackTrace();
		}
	}
}
