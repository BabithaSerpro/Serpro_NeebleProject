package testTemplates;

import java.awt.print.PrinterException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import DBConnection.DBConnectivity;
import addTest.addTestController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.StageStyle;

public class PrintableData {
	public static AnchorPane viewReportpane = HeaderController.getPaneTemplate();
//	public static VBox vbox = CreateTestTemplate.getVbox();
	public static int p_id;
	public static String p_name,test_name;
	private static String path;
	private static Connection con;
	private static PreparedStatement ps;
	private static int flag;
	public static Button btn_print=CreateTestTemplate.getBtnPrint();
	
	public static int count = 1;
	
	public void increaseCount() {
	    count++;
	}
	public static void downloadReport(ActionEvent event, String testname) {
		try {
			p_id=Integer.valueOf(HeaderController.getP_id().getText());
			p_name=HeaderController.getP_name().getText();
			test_name=testname;
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
                doc.save(f+"/"+ p_id +"_" + p_name +"_" + test_name +"_"+count+".pdf");
                path=f+"/"+ p_id +"_" + p_name +"_" + test_name +"_"+count+".pdf";
              } else {
            	Alert alert = new Alert(AlertType.WARNING);
  				alert.setTitle("Dr. Subodh App");
  				alert.initStyle(StageStyle.TRANSPARENT);
  				alert.setContentText("Something went wrong..!!Please try again");
  				alert.showAndWait();
              }
            } else {
              doc.save(f+"/"+ p_id +"_" + p_name +"_" + test_name +"_"+count+".pdf");
              path=f+"/"+ p_id +"_" + p_name +"_" + test_name +"_"+count+".pdf";
            }
           
            doc.close();
            output.delete();

			//Save the folder path to DB
			String update = "UPDATE patient_reportmasterdata SET active='Y', folderPath='"+path+"' WHERE regNumber=?";
			con = DBConnectivity.getConnection();
			ps = con.prepareStatement(update);
			ps.setInt(1, p_id);
			flag=ps.executeUpdate();
			
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
