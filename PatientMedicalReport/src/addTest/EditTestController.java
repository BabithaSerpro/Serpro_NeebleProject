package addTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import viewPatient.ViewPDController;

public class EditTestController {

	@FXML
	private AnchorPane pane_editTest;

	@FXML
	private ComboBox<String> testName;

	@FXML
	private TextField txt_testname;

	@FXML
	private DatePicker testDate;

	@FXML
	private TextField txt_testdate;

	@FXML
	private TextField refDoc;

	@FXML
	private DatePicker reportDate;

	@FXML
	private TextField txt_reportdate;

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
	private Button btn_update;

	@FXML
	private Label pId;

	@FXML
	private Label lblName;

	private static Connection con;
	private PreparedStatement ps;
	private int flag;
	private static ComboBox<String> test_Name;
	private static DatePicker test_date, report_date;
	private static TextField ref_doc, txtTestdate, txtReportdate, txtTestname;
	private static TextArea p_history, test_desc, p_impression, p_note;
	private static Button btnupdate;
	private static Label lbl_id, lbl_name;

	public static int test_id;

	public int getTest_id() {
		return test_id;
	}

	public static void setTest_id(int tID) {
		test_id = tID;
	}

	public static String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	boolean testdateSelected = false;

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		test_Name = testName;
		txtReportdate = txt_reportdate;
		txtTestname = txt_testname;
		txtTestdate = txt_testdate;
		btnupdate = btn_update;
		ref_doc = refDoc;
		p_history = phistory;
		test_desc = tDesc;
		p_impression = impression;
		p_note = note;
		lbl_id = pId;
		lbl_name = lblName;
		con = DBConnectivity.getConnection();

		pId.setText(ViewPDController.getLblID().getText());
		lblName.setText(ViewPDController.getLblPName().getText());

		testName.valueProperty().addListener((o, ov, nv) -> {
			txt_testname.setText(testName.getSelectionModel().getSelectedItem().toString());
		});

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
	}

	@FXML
	void getSelectedTestDate(ActionEvent event) {
		testdateSelected = true;
		txt_testdate.setText(((TextField) testDate.getEditor()).getText());
	}

	@FXML
	void getSelectedReportDate(ActionEvent event) {
		txt_reportdate.setText(((TextField) reportDate.getEditor()).getText());
	}

	public List<String> dropDownValue() throws Exception {
		List<String> options = new ArrayList<String>();

		ps = con.prepareStatement("SELECT testname FROM testdetails");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			options.add(rs.getString("testname"));
		}
		ps.close();
		rs.close();
		return options;

	}

	public static void editTestDetails(int tID) throws SQLException {
		String SQL_view = "SELECT * FROM patient_reportmasterdata WHERE id='" + tID + "'";
		setTest_id(tID);
		try {

			ResultSet rs = con.createStatement().executeQuery(SQL_view);
			while (rs.next()) {
				txtTestname.setText(rs.getString("testName"));
				txtTestdate.setText(rs.getString("testDate"));
				ref_doc.setText(rs.getString("ref_doctor"));
				txtReportdate.setText(rs.getString("reportDate"));
				p_history.setText(rs.getString("patientHistory"));
				test_desc.setText(rs.getString("testDescription"));
				p_impression.setText(rs.getString("impression"));
				p_note.setText(rs.getString("note"));
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/*
	 * public void cancelBtn(ActionEvent event) throws IOException { AnchorPane pane
	 * = MainScreenController.getHomePage(); for (int i = 0; i <
	 * pane.getChildren().size(); i++) { String paneID =
	 * pane.getChildren().get(i).getId(); switch (paneID) { case "pane_Dashboard":
	 * MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
	 * break; case "pane_viewDetails":
	 * MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
	 * break; case "pane_newPatient":
	 * MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
	 * break; case "pane_editTest":
	 * MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
	 * break; } } }
	 */

	public void updateTest() throws NumberFormatException, ParseException {
		if (validateFields() && validateDate() && validateDrName()) {
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());

			String update = "UPDATE patient_reportmasterdata SET regNumber=?,"
					+ "ref_doctor=?,testName=?,testDate=?,reportDate=?,patientHistory=?,"
					+ "testDescription=?,impression=?,note=?,active='Y'," + " modified_timestamp='" + timeStamp
					+ "' WHERE id=?";

			con = DBConnectivity.getConnection();
			try {
				ps = con.prepareStatement(update);
				int pid = Integer.parseInt(pId.getText());
				ps.setInt(1, pid);
				ps.setString(2, refDoc.getText());
				ps.setString(3, txtTestname.getText());
				ps.setString(4, txtTestdate.getText());
				ps.setString(5, txtReportdate.getText());
				ps.setString(6, phistory.getText());
				ps.setString(7, tDesc.getText());
				ps.setString(8, impression.getText());
				ps.setString(9, note.getText());
				ps.setInt(10, getTest_id());

				flag = ps.executeUpdate();

				if (flag > 0) { // redirecting to dashboard
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dr. Subodh App");
					alert.setHeaderText(null);
					alert.setContentText("Test data Updated Sucessfully");
					alert.getButtonTypes().setAll(ButtonType.OK);
					alert.showAndWait().ifPresent(bt -> {
						if (bt == ButtonType.OK) {
							try {
								ViewPDController.refreshTestDetails(pid);
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
								case "pane_editTest":
									MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
									break;
								}
							}
						}
					});
				} else {
					System.out.println("Not Added");
				}
			} catch (SQLException e) { // catching exception if any backend error occurs
				e.printStackTrace();
			}
		}
	}

	public boolean validateFields() { // null validation of field
		if (txtTestname.getText().isEmpty()) {
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
		} else if (txt_testdate.getText().isEmpty()) {
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
		} else if (txt_reportdate.getText().isEmpty()) {
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

	public boolean validateDate() throws ParseException { // Mobile No. Validation

		String tdateValue = txt_testdate.getText();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate tdate = LocalDate.parse(tdateValue, format);
		String rdateValue = txt_reportdate.getText();
		LocalDate rdate = LocalDate.parse(rdateValue, format);

		if (rdate.equals(tdate)) {
			return true;
		} else if (rdate.isAfter(tdate)) {
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
		if (txtTestname.getText() != null || txtTestdate.getText()!= null || txtReportdate.getText()!= null 
				||phistory.getText() != null
				|| tDesc.getText() != null || impression.getText() != null ||note.getText() != null){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Dr Subodh App");
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
			alert.getDialogPane().setHeaderText("Are you Sure You want to Cancel!! Details not saved!!");
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
		case "pane_editTest":
			MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
			break;
		}
	}

}
}