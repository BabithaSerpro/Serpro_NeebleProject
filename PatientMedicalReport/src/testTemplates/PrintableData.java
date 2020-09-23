package testTemplates;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

public class PrintableData {
	public static AnchorPane viewReportpane = HeaderController.getPaneTemplate();
	public static Button btn_print=CreateTestTemplate.getBtnPrint();
    public static void printReport(ActionEvent event) {
		Printer printer = Printer.getDefaultPrinter();
		printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
	    PageLayout pageLayout=printer.getDefaultPageLayout();
	    PrinterJob job = PrinterJob.createPrinterJob(printer);
	    
	    if(job != null){
	    	btn_print.setVisible(false);
	 		double scaleX = pageLayout.getPrintableWidth() / viewReportpane.getBoundsInParent().getWidth();
			double scaleY = pageLayout.getPrintableHeight() / viewReportpane.getBoundsInParent().getHeight();
			Scale scale = new Scale(scaleX, scaleY);
			viewReportpane.getTransforms().add(scale);
			boolean showDialog = job.showPageSetupDialog(viewReportpane.getScene().getWindow());
	 		job.showPrintDialog(viewReportpane.getScene().getWindow()); 
			job.printPage(viewReportpane);
			viewReportpane.getTransforms().remove(scale);
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
