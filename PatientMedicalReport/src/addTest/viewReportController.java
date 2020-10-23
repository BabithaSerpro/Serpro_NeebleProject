package addTest;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import DBConnection.DBConnectivity;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.StageStyle;

public class viewReportController {

	@FXML
	private AnchorPane pane_viewReport;
	
	@FXML
	private AnchorPane reportPane;
	 
	@FXML
	private Label pname;

	@FXML
	private Label pAge;

	@FXML
	private Label pGender;

	@FXML
	private Label refDoc;

	@FXML
	private Label pId;

	@FXML
	private Label testDate;

	@FXML
	private Label reportDate;

	
	@FXML
	private Label pHistory;

	@FXML
	private Label testObservation;

	@FXML
	private Label impression;

	@FXML
	private Label note;

	@FXML
	private Label testName;
	
	@FXML
    private Button btn_download;
	
	@FXML
    private Button btn_print;
	
	private static AnchorPane viewReportpane;
	private static Label p_name,p_age,p_gender,ref_doctor,p_id,test_date,report_date,p_history,test_observation,p_impression,p_note,test_name;
	private static Connection con;
	private static PreparedStatement ps;
	private static String path;
	private int flag;
	public static int testID;
	
	public static int getTestID() {
		return testID;
	}

	public static void setTestID(int testID) {
		viewReportController.testID = testID;
	}

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		viewReportpane=reportPane;
		p_name=pname;
		p_age=pAge;
		p_gender=pGender;
		ref_doctor=refDoc;
		p_id=pId;
		test_date=testDate;
		report_date=reportDate;
		p_history=pHistory;
		test_observation=testObservation;
		p_impression=impression;
		p_note=note;
		test_name=testName;
		int testId = addTestController.getpTId();
		try {
			con = DBConnectivity.getConnection();
			viewReport(testId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void viewReport(int testId) {
		try {
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement(
					"SELECT pm.patient_name,pm.age,pm.gender,pr.ref_doctor,pr.regNumber,pr.testDate,pr.reportDate,pr.patientHistory,pr.testName,\r\n"
							+ "pr.testDescription,pr.impression,pr.note\r\n"
							+ "FROM patient_masterdata pm, patient_reportmasterdata pr WHERE\r\n"
							+ "pm.patient_id= pr.regNumber AND pr.id=? AND pm.active='Y' AND pr.active='Y'");
			ps.setInt(1, testId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p_name.setText(rs.getString(1));
				p_age.setText(rs.getString(2));
				p_gender.setText(rs.getString(3));
				ref_doctor.setText(rs.getString(4));
				p_id.setText(String.valueOf(rs.getInt(5)));
				test_date.setText(rs.getString(6));
				report_date.setText(rs.getString(7));
				p_history.setText(rs.getString(8));
				test_name.setText(rs.getString(9));
				test_observation.setText(rs.getString(10));
				p_impression.setText(rs.getString(11));
				p_note.setText(rs.getString(12));
			}
			setTestID(testId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
    void downloadReport(ActionEvent event) {
		try {
			btn_download.setVisible(false);
	    	btn_print.setVisible(false);
			double pixelScale=2.0;
			WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*viewReportpane.getWidth()), (int)Math.rint(pixelScale*viewReportpane.getHeight()));
		    SnapshotParameters spa = new SnapshotParameters();
		    spa.setTransform(Transform.scale(pixelScale, pixelScale));
            WritableImage snapshot = viewReportpane.snapshot(spa, writableImage);

            String home = System.getProperty("user.home");
			File output = new File(home+"/Downloads/snapshot.png");
            
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
			
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            PDImageXObject pdimage;
            PDPageContentStream content;

            pdimage = PDImageXObject.createFromFile(home+"/Downloads/snapshot.png", doc);
            content = new PDPageContentStream(doc, page);
            PDRectangle box = page.getMediaBox();
            double factor = Math.min(box.getWidth() / snapshot.getWidth(), box.getHeight() / snapshot.getHeight());
            float height = (float) (snapshot.getHeight() * factor);
            
            content.drawImage(pdimage, 10, box.getHeight() - height-10, (float) (snapshot.getWidth() * factor), height-4);
            content.close();
            doc.addPage(page);
           
            String path_savefile = home+"/Desktop/PatientReports/"+java.time.LocalDate.now().getYear();
            File f = new File(path_savefile);
            if (!f.isDirectory()) {
              boolean success = f.mkdirs();
              if (success) {
                doc.save(f+"/"+ p_id.getText() +"_" + p_name.getText() +"_" + test_name.getText() +".pdf");
                path=f+"/"+ p_id.getText() +"_" + p_name.getText() +"_" + test_name.getText() +".pdf";
              } else {
            	Alert alert = new Alert(AlertType.WARNING);
  				alert.setTitle("Dr. Subodh App");
  				alert.initStyle(StageStyle.TRANSPARENT);
  				alert.setContentText("Something went wrong..!!Please try again");
  				alert.showAndWait();
              }
            } else {
              doc.save(f+"/"+ p_id.getText() +"_" + p_name.getText() +"_" + test_name.getText() +".pdf");
              path=f+"/"+ p_id.getText() +"_" + p_name.getText() +"_" + test_name.getText() +".pdf";
            }
            doc.close();
            output.delete();

			//Save the folder path to DB
			String update = "UPDATE patient_reportmasterdata SET active='Y', folderPath='"+path+"' WHERE id=?";
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement(update);
			ps.setInt(1, getTestID());
			flag=ps.executeUpdate();
			
			if (flag > 0) { // redirecting to dashboard
				btn_download.setVisible(true);
		    	btn_print.setVisible(true);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Dr. Subodh App");
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("File Saved!!");
				alert.getButtonTypes().setAll(ButtonType.OK);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
				alert.showAndWait().ifPresent(bt -> {
					if (bt == ButtonType.OK) {
						addTestController.getReportScreen().close();
					}
				});
			}
			
		} catch (Exception e) { // catching exception if any backend error occurs
			e.printStackTrace();
		}
	}
	
	@FXML
    void printReport(ActionEvent event) {
		Printer printer = Printer.getDefaultPrinter();
		printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
	    PageLayout pageLayout=printer.getDefaultPageLayout();
	    PrinterJob job = PrinterJob.createPrinterJob(printer);
	    
	    if(job != null){
	    	btn_download.setVisible(false);
	    	btn_print.setVisible(false);
	 		double scaleX = pageLayout.getPrintableWidth() / viewReportpane.getBoundsInParent().getWidth();
			double scaleY = pageLayout.getPrintableHeight() / viewReportpane.getBoundsInParent().getHeight();
			Scale scale = new Scale(scaleX, scaleY);
			viewReportpane.getTransforms().add(scale);
			boolean showDialog = job.showPageSetupDialog(viewReportpane.getScene().getWindow());
	 		job.showPrintDialog(viewReportpane.getScene().getWindow()); 
			job.printPage(viewReportpane);
			viewReportpane.getTransforms().remove(scale);
			btn_download.setVisible(true);
	    	btn_print.setVisible(true);
	    } 
	    job.endJob();
	    
	    Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Dr Subodh App");
		Label label = new Label("Print");
		label.textProperty().bind(job.jobStatusProperty().asString());
		alert.setHeaderText(label.getText());
		alert.showAndWait();
    }
}
