package addTest;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import viewPatient.ViewPDController;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import screens.HomePageController;

public class addTestController implements Initializable {
	@FXML
    private AnchorPane pane_addTest;
	
	@FXML
    private AnchorPane sc_pane;
	
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
    private AnchorPane pane_note;
	
	@FXML
	private ComboBox<String> testName;

	@FXML
	private Label pId;

	@FXML
	private Label nameL;

	@FXML
	private JFXButton btn_reportTable;

	private Connection connection;

	private PreparedStatement ps;

	private ResultSet rs;
	private int flag;
	private String path;
	public static Stage reportScreen;
	public static AnchorPane scPane,paneAddTest;
	private static ComboBox<String> test_Name;
    private static DatePicker test_date,report_date;
    private static TextField ref_doc,txtTestdate,txtReportdate,txtTestname; 
    private static TextArea p_history,test_desc,p_impression,p_note;
	public static JFXButton btnReport;

	public static Stage getReportScreen() {
		return reportScreen;
	}

	public static void setReportScreen(Stage reportScreen) {
		addTestController.reportScreen = reportScreen;
	}

	public static int pTId;

	public static int getpTId() {
		return pTId;
	}

	public void setpTId(int id) {
		pTId = id;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneAddTest=pane_addTest;
		scPane=sc_pane;
		btnReport=btn_reportTable;
		test_Name=testName;
		test_date=testDate;
		report_date=reportDate;
    	ref_doc=refDoc;
    	p_history=phistory;
    	test_desc=tDesc;
    	p_impression=impression;
    	p_note=note;
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

		testDate.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});

		reportDate.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});

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
		if (validateFields() && validateDate() && validateDrName()) {
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
			String insert = "INSERT into patient_reportmasterdata (regNumber,ref_doctor,testName,testDate,reportDate,patientHistory,testDescription,impression,note,active,created_timestamp,modified_timestamp)"
					+ " values(?,?,?,?,?,?,?,?,?,'Y','" + timeStamp + "','" + timeStamp + "')";
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
				}

				if (flag > 0) { // redirecting to dashboard
					try {
						int pid = Integer.parseInt(pId.getText());
						ViewPDController.refreshTestDetails(pid);					
						HomePageController.refreshtotalTestCount();
						HomePageController.refreshtotalTestCompleted();
						HomePageController.refreshtotalTestPending();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					AnchorPane pane = MainScreenController.getHomePage();					
					for (int i = 0; i < pane.getChildren().size(); i++) {
						String paneID = pane.getChildren().get(i).getId();
						switch (paneID) {
						case "pane_Dashboard":
							MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
							break;
						case "pane_viewDetails":
							MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
							break;
						case "pane_addTest":
							MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
							break;
						}
					}

					reportScreen = new Stage();
					HomePageController.refreshtotalTestCount();
					HomePageController.refreshtotalTestCompleted();
					HomePageController.refreshtotalTestPending();
					Parent root = FXMLLoader.load(getClass().getResource("/addTest/viewReport.fxml"));
					Scene scene = new Scene(root);
					reportScreen.setScene(scene);
					reportScreen.setTitle("Patient Report");
					reportScreen.show();
					setReportScreen(reportScreen);
				} else {
					System.out.println("Not Added");
				}
			} catch (SQLException e) { // catching exception if any backend error occurs
				e.printStackTrace();
			}
		}
	}

	public boolean validateFields() { // null validation of field
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

	public boolean validateDate() { // Mobile No. Validation

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

	private boolean validateDrName() { // Name Validation

		Pattern p = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
		Matcher m = p.matcher(refDoc.getText());
		if (m.find() && m.group().equals(refDoc.getText())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Enter Valid Dr. Name");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		}
	}

	public void cancelBtn(ActionEvent event) throws IOException {
		// checking if any data present
		if (test_Name.getSelectionModel().getSelectedItem() != null || ((TextField) testDate.getEditor()).getText() != null || ((TextField) reportDate.getEditor()).getText() != null
				|| ref_doc.getText() != null || p_history.getText() != null || test_desc.getText() != null || 
				p_impression.getText() != null || p_note.getText() != null ) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Dr Subodh App");
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
			alert.getDialogPane().setHeaderText("Are you Sure!! You want to Close this Page!!");
			alert.showAndWait().ifPresent(bt -> {
				if (bt == ButtonType.YES) {
					close();
				} else if (bt == ButtonType.NO) {
					event.consume();
				}
			});
		} else {
			close();
		}
	}

	public static void close() {
		AnchorPane pane = MainScreenController.getHomePage();
		for (int i = 0; i < pane.getChildren().size(); i++) {
			String paneID = pane.getChildren().get(i).getId();
			switch (paneID) {
			case "pane_Dashboard":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_viewDetails":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
				break;
			case "pane_newPatient":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_addTest":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			}
		}
	}
}