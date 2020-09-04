package addTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.glass.events.ViewEvent;

import DBConnection.DBConnectivity;
import application.MainScreenController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
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
    private static DatePicker test_date,report_date;
    private static TextField ref_doc,txtTestdate,txtReportdate,txtTestname; 
    private static TextArea p_history,test_desc,p_impression,p_note;
    private static Button btnupdate;
    private static Label lbl_id,lbl_name;
    
    public static int test_id;
    public int getTest_id() {
		return test_id;
	}

	public static void setTest_id(int tID) {
		test_id = tID;
	}
    @FXML
	public void initialize() throws ClassNotFoundException, SQLException {
    	txtReportdate=txt_reportdate;
    	txtTestname=txt_testname;
    	txtTestdate=txt_testdate;
    	btnupdate=btn_update;
    	ref_doc=refDoc;
    	p_history=phistory;
    	test_desc=tDesc;
    	p_impression=impression;
    	p_note=note;
    	lbl_id=pId;
    	lbl_name=lblName;
		con = DBConnectivity.getConnection();
		
		pId.setText(ViewPDController.getLblID().getText());
		lblName.setText(ViewPDController.getLblPName().getText());
		
		testName.valueProperty().addListener((o, ov, nv) -> {
			txt_testname.setText(testName.getSelectionModel().getSelectedItem().toString());
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

	public void cancelBtn(ActionEvent event) throws IOException {
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
    
    public void updateTest() {
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
			System.out.println(getTest_id());
			String update = "UPDATE patient_reportmasterdata SET regNumber=?,"
					+ "ref_doctor=?,testName=?,testDate=?,reportDate=?,patientHistory=?,"
					+ "testDescription=?,impression=?,note=?,active='Y',"
					+ " modified_timestamp='"+ timeStamp +"' WHERE id=?";
			con = DBConnectivity.getConnection();
			try {
				ps = con.prepareStatement(update);
				int pid=Integer.parseInt(pId.getText());
				ps.setInt(1, pid);
				ps.setString(2, refDoc.getText());
				ps.setString(3, txtTestname.getText());
				ps.setString(4, ((TextField) testDate.getEditor()).getText());
				ps.setString(5, ((TextField) reportDate.getEditor()).getText());
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
					alert.setContentText("Test data Updated Suucessfully");
					alert.showAndWait();
				} else {
					System.out.println("Not Added");
				}
			} catch (SQLException e) { // catching exception if any backend error occurs
				e.printStackTrace();
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

}