package addTest;

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
import DBConnection.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import viewPatient.ViewPDController;
import javafx.scene.control.Alert.AlertType;

public class addTestController implements Initializable {
	@FXML
	private DatePicker testDate;

	@FXML
	private TextField refDoc;

	@FXML
	private DatePicker reportDate;

	@FXML
	private TextField phistory;

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

	/*
	 * private String path = "C://Users//neebal//Desktop//PatientReports//2020// ";
	 * private String DEST = path + nameL.getText() + ".pdf";
	 */

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
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
		String insert = "INSERT into patient_reportmasterdata (regNumber,ref_doctor,testName,testDate,reportDate,patientHistory,testDescription,impression,note,active,created_timestamp,modified_timestamp)"
				+ " values(?,?,?,?,?,?,?,?,?,'Y','" + timeStamp + "','" + timeStamp + "')";
		connection = DBConnectivity.getConnection();
		try {
			ps = connection.prepareStatement(insert);

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

			if (flag > 0) { // redirecting to dashboard
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.setContentText("Test Added Suucessfully");
				alert.showAndWait();
			} else {
				System.out.println("Not Added");
			}
		} catch (SQLException e) { // catching exception if any backend error occurs
			e.printStackTrace();
		}

	}

}
