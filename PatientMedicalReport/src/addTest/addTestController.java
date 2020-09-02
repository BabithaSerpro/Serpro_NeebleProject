package addTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import viewPatient.ViewPDController;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class addTestController implements Initializable {
	@FXML
	private DatePicker testDate;

	@FXML
	private TextField refDoc;

	@FXML
	private DatePicker reportDate;

	@FXML
	private TextArea phistory;

	@FXML
	private TextArea tDesc;

	@FXML
	private TextArea impression;

	@FXML
	private TextArea note;

	@FXML
	private Button cancelBtn;

	@FXML
	private Button saveBtn;

	@FXML
	private Button gnRepBtn;

	@FXML
	private ComboBox<String> testName;

	@FXML
	private Label pId;

	@FXML
	private Label nameL;

	private Connection connection;

	private PreparedStatement ps;

	private ResultSet rs;
	private int flag;

	private String path;
	
	public static int pTId;

	public static int getpTId() {
		return pTId;
	}

	public void setpTId(int id) {
		pTId = id;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		pId.setText(ViewPDController.getLblID().getText());
		nameL.setText(ViewPDController.getLblPName().getText());

		try {
			testName.setItems(FXCollections.observableArrayList(dropDownValue()));
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dr Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Cannot Load Screen");
			alert.showAndWait();
			e.printStackTrace();
		}

		// disable editor
		testDate.getEditor().setDisable(true);
		testDate.setStyle("-fx-opacity: 1");
		testDate.getEditor().setStyle("-fx-opacity: 1");

		reportDate.getEditor().setDisable(true);
		reportDate.setStyle("-fx-opacity: 1");
		reportDate.getEditor().setStyle("-fx-opacity: 1");

		testDate.setDayCellFactory(param -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});

		reportDate.setDayCellFactory(param -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});

	}

	public List<String> dropDownValue() throws Exception {
		List<String> options = new ArrayList<String>();

		connection = DBConnectivity.getConnection();
		ps = connection.prepareStatement("SELECT testname FROM testdetails");
		rs = ps.executeQuery();

		while (rs.next()) {
			options.add(rs.getString("testname"));
		}
		ps.close();
		rs.close();
		return options;

	}

	public void addTest(ActionEvent event) throws Exception {

		// insert new test
		if (validateFields() && validateDate()) {
			generatePdfReport();
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
			String insert = "INSERT into patient_reportmasterdata (regNumber,ref_doctor,testName,testDate,reportDate,patientHistory,testDescription,impression,note,folderPath,active,created_timestamp,modified_timestamp)"
					+ " values(?,?,?,?,?,?,?,?,?,'" + path + "','Y','" + timeStamp + "','" + timeStamp + "')";
			connection = DBConnectivity.getConnection();
			try {
				ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, pId.getText());
				ps.setString(2, refDoc.getText());
				String value = testName.getValue();
				ps.setString(3, value);
				ps.setString(4, ((TextField) testDate.getEditor()).getText());
				ps.setString(5, ((TextField) reportDate.getEditor()).getText());
				ps.setString(6, phistory.getText());
				ps.setString(7, tDesc.getText());
				ps.setString(8, impression.getText());
				ps.setString(9, note.getText());

				flag = ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					pTId = rs.getInt(1);
					System.out.println("Generation of PID"+ pTId);
				}

				if (flag > 0) { // redirecting to dashboard
					Stage add = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/addTest/viewReport.fxml"));
					Scene scene = new Scene(root);
					add.setScene(scene);
					add.show();
				} else {
					System.out.println("Not Added");

				}
			} catch (SQLException e) { // catching exception if any backend error occurs
				e.printStackTrace();
			}
		}

	}

	private boolean validateFields() { // null validation of field
		if (testName.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Select Test from Dropdowm");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		} else if (((TextField) testDate.getEditor()).getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Select Test Date");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		} else if (((TextField) reportDate.getEditor()).getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Select Report Date");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;

		}
		return true;

	}

	private boolean validateDate() { // Mobile No. Validation

		if (reportDate.getValue().equals(testDate.getValue())) {
			return true;
		} else if (reportDate.getValue().isAfter(testDate.getValue())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Report Date should be greater or equal to Test Date");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		}

	}

	private void generatePdfReport() throws Exception {

		String value1 = nameL.getText();
		String value2 = ViewPDController.getLblAge().getText();
		String value3 = ViewPDController.getLblGender().getText();
		String value4 = refDoc.getText();
		String value5 = pId.getText();
		String value6 = ((TextField) testDate.getEditor()).getText();
		String value7 = ((TextField) reportDate.getEditor()).getText();
		String value8 = phistory.getText();
		String value9 = testName.getSelectionModel().getSelectedItem();
		String value10 = tDesc.getText();
		String value11 = impression.getText();
		String value12 = note.getText();

		Document document = new Document();
		path = "E://Users//neebal//Desktop//PatientReports//2020//" + pId.getText() + "_" + value1 + "_" + value9
				+ ".pdf";
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		Image image = Image.getInstance("bin/images/tempsnip.png");
		image.setAlignment(Element.ALIGN_CENTER);
		document.add(image);
		Paragraph name = new Paragraph("Patient Name:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		name.setAlignment(Element.ALIGN_CENTER);
		name.add(value1);
		document.add(name);
		Paragraph age = new Paragraph("Age:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		age.add(value2);
		document.add(age);
		Paragraph gender = new Paragraph("Gender:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		gender.add(value3);
		document.add(gender);
		Paragraph refDoc = new Paragraph("Ref Doctor:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		refDoc.add(value4);
		document.add(refDoc);
		Paragraph pId = new Paragraph("PatientId:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		pId.add(value5);
		document.add(pId);
		Paragraph testDate = new Paragraph("Test Date:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		testDate.add(value6);
		document.add(testDate);
		Paragraph repDate = new Paragraph("Report Date:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		repDate.add(value7);
		document.add(repDate);
		Paragraph pHistory = new Paragraph("Patient History:",
				FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		pHistory.add(value8);
		document.add(pHistory);
		Paragraph testName = new Paragraph("Test Name:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		testName.add(value9);
		document.add(testName);
		Paragraph testDesc = new Paragraph("Test Description:",
				FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		testDesc.add(value10);
		document.add(testDesc);
		Paragraph impression = new Paragraph("Impression:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		impression.add(value11);
		document.add(impression);
		Paragraph note = new Paragraph("Note:", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));
		note.add(value12);
		document.add(note);

		document.close();
		writer.close();

	}

	public void cancelBtn(ActionEvent event) throws IOException {
		AnchorPane pane = MainScreenController.getHomePage();
		for (int i = 0; i < pane.getChildren().size(); i++) {
			String paneID = pane.getChildren().get(i).getId();
			switch (paneID) {
			case "pane_Dashboard":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
				break;
			case "pane_viewDetails":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_newPatient":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_newTest":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			}
		}
	}

	

}
