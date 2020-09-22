package testTemplates;

import addTest.viewReportController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

public class FooterController {

	@FXML
	private Button btn_print;

	public static int testID;
	public static AnchorPane contentPane = HeaderController.getPaneTemplate();

	public static int getTestID() {
		return testID;
	}

	public static void setTestID(int testID) {
		viewReportController.testID = testID;
	}

	@FXML
	public void initialize() throws ClassNotFoundException {

	}

	@FXML
	void printReport(ActionEvent event) {
		saveData();
		Printer printer = Printer.getDefaultPrinter();
		printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
		PageLayout pageLayout = printer.getDefaultPageLayout();
		PrinterJob job = PrinterJob.createPrinterJob(printer);

		if (job != null) {
			btn_print.setVisible(false);

			double scaleX = pageLayout.getPrintableWidth() / contentPane.getBoundsInParent().getWidth();
			double scaleY = pageLayout.getPrintableHeight() / contentPane.getBoundsInParent().getHeight();
			Scale scale = new Scale(scaleX, scaleY);
			contentPane.getTransforms().add(scale);
			boolean showDialog = job.showPageSetupDialog(contentPane.getScene().getWindow());
			job.showPrintDialog(contentPane.getScene().getWindow());
			job.printPage(contentPane);
			contentPane.getTransforms().remove(scale);
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
	
	public static void saveData() {
		
	}
}
