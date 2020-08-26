package addPatient;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import DBConnection.DBConnectivity;
import application.DashboardController;
import application.PatientData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.time.LocalDate;
import java.time.Period;

import javafx.scene.control.DateCell;

public class AddPatientController implements Initializable {

	@FXML
	private TextField mobNo;

	@FXML
	private TextField fullName;

	@FXML
	private TextField email;

	@FXML
	private RadioButton male;

	@FXML
	private RadioButton female;

	@FXML
	private RadioButton others;

	@FXML
	private ToggleGroup genderR;

	@FXML
	private DatePicker dob;

	
	@FXML
	private Button cancelBtn;

	@FXML
	private Button addBtn;

	@FXML
	private TextField ageLabel;
	
	private Connection connection;

	private PreparedStatement ps;
	
	private ResultSet rs;

	private int flag;
	
	public int pId;

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        //disable editor
		dob.getEditor().setDisable(true);
		dob.setStyle("-fx-opacity: 1");
		dob.getEditor().setStyle("-fx-opacity: 1");
		
		// disable future date
		dob.setDayCellFactory(param -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});


		// set age from calender selection
		dob.setOnAction(e -> {
			LocalDate today = LocalDate.now();
			LocalDate birthday = dob.getValue();
			Period p = Period.between(birthday, today);
			if (p.getYears() <= 0) {
				ageLabel.setText(p.getMonths() + " months");
				if (p.getMonths() <= 0) {
					ageLabel.setText(p.getDays() + " days");
				}
			} else {
				ageLabel.setText(p.getYears() + " years "  + p.getMonths() + " months" );
			}
		});
	}

	public void addPatient(ActionEvent event) throws Exception {
		boolean check = checkPatientAlreadyExist(mobNo.getText());

		if (validateFields() && validateMobileNo() && validateName() && validateEmail()) {

			if (check == true) { // check existing patient
				/*
				 * Alert alert = new Alert(AlertType.INFORMATION);
				 * alert.setTitle("Dr Subodh App"); alert.setHeaderText(null);
				 * alert.setContentText("Patient Already Exist");
				 * alert.showAndWait().ifPresent(bt -> { if (bt == ButtonType.OK) { ((Node)
				 * (event.getSource())).getScene().getWindow().hide();
				 * DashboardController.refreshTable(); } });
				 */
				
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/addPatient/exists.fxml"));
				Scene scene = new Scene(root);
				stage.initStyle(StageStyle.TRANSPARENT);
				scene.setFill(Color.TRANSPARENT);
				stage.setScene(scene);
				stage.show();
				
			} else { // insert new patient
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
				String insert = "INSERT into patient_masterdata (mobileNumber,patient_name,gender,emailId,dob,age,active,created_timestamp,modified_timestamp) values(?,?,?,?,?,?,'Y','"
						+ timeStamp + "','" + timeStamp + "')";
				connection = DBConnectivity.getConnection();
				try {
					ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, mobNo.getText());
					ps.setString(2, fullName.getText());
					ps.setString(3, getGender());
					ps.setString(4, email.getText());
					ps.setString(5, ((TextField) dob.getEditor()).getText());
					ps.setString(6, ageLabel.getText());
					flag = ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						pId = rs.getInt(1);
						System.out.println(pId);
					}
					System.out.println("Data " + getpId());
					if (flag > 0) { // redirecting to dashboard
						/*
						 * TableView<PatientData> patientTable = DashboardController.getPatienttable();
						 * FXMLLoader fxmlLoader; Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						 * alert.setTitle("Dr Subodh App");
						 * alert.getButtonTypes().setAll(ButtonType.OK);
						 * alert.getDialogPane().setHeaderText("Patient added sucessfully!");
						 * clearFields(); alert.showAndWait().ifPresent(bt -> { if (bt == ButtonType.OK)
						 * { ((Node) (event.getSource())).getScene().getWindow().hide();
						 * DashboardController.refreshTable(); } });
						 */
						
						Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/addPatient/success.fxml"));
						Scene scene = new Scene(root);
						stage.initStyle(StageStyle.TRANSPARENT);
						scene.setFill(Color.TRANSPARENT);
						stage.setScene(scene);
						stage.show();
						clearFields();
					} else {
						/*
						 * Alert alert = new Alert(Alert.AlertType.WARNING);
						 * alert.setTitle("Dr Subodh App"); alert.
						 * setContentText("Some Error occured during adding data!!..Please try again!");
						 * alert.showAndWait(); clearFields();
						 */
						Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/addPatient/failure.fxml"));
						Scene scene = new Scene(root);
						stage.initStyle(StageStyle.TRANSPARENT);
						scene.setFill(Color.TRANSPARENT);
						stage.setScene(scene);
						stage.show();
						clearFields();
						
					}
				} catch (SQLException e) { // catching exception if any backend error occurs
					Stage stage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/addPatient/failure.fxml"));
					Scene scene = new Scene(root);
					stage.initStyle(StageStyle.TRANSPARENT);
					scene.setFill(Color.TRANSPARENT);
					stage.setScene(scene);
					stage.show();
					clearFields();
					e.printStackTrace();
				}
			}
		}
	}

	public void cancelBtn(ActionEvent event) throws IOException {
		cancelBtn.getScene().getWindow().hide();
	}

	public String getGender() { //selection of gender
		String gen = "";
		if (male.isSelected()) {
			gen = "Male";
		} else if (female.isSelected()) {
			gen = "Female";
		} else if (others.isSelected()) {
			gen = "Others";
		}
		return gen;
	}

	private boolean validateFields() { // null validation of each field
		if (mobNo.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Mobile Number Cannot Be Empty");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();
			return false;
		} else if (fullName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Full Name Cannot Be Empty");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();
			return false;
		} else if (!(male.isSelected() || female.isSelected() || others.isSelected())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Please Select Gender");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();
			return false;
		} else if (((TextField) dob.getEditor()).getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Please Select DOB");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();
			return false;

		}
		return true;

	}

	private boolean validateMobileNo() {  //Mobile No. Validation
		Pattern p = Pattern.compile("(0|91)?[5-9][0-9]{9}");
		Matcher m = p.matcher(mobNo.getText());
		if (m.find() && m.group().equals(mobNo.getText())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Valid Mobile Number");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();
			return false;
		}

	}

	private boolean validateName() { //Name Validation

		Pattern p = Pattern.compile("[a-zA-Z\\s]+");
		Matcher m = p.matcher(fullName.getText());
		if (m.find() && m.group().equals(fullName.getText())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Valid Name");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();
			return false;
		}
	}

	private boolean validateEmail() {  //Email Validation
		if (!(email.getText().isEmpty())) {
			Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
			Matcher m = p.matcher(email.getText());
			if (m.find() && m.group().equals(email.getText())) {
				return true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.setContentText("Please Enter Valid Email");
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.showAndWait();
				return false;
			}

		}
		return true;
	}

	public void clearFields() { // clearing fileds 
		mobNo.clear();
		fullName.clear();
		email.clear();
		dob.getEditor().setText("");
		male.setSelected(false);
		female.setSelected(false);
		others.setSelected(false);
		ageLabel.clear();

	}

	public boolean checkPatientAlreadyExist(String mobileNo) throws Exception {
		Connection connection = DBConnectivity.getConnection();
		Statement stm = connection.createStatement();
		ResultSet rst = stm.executeQuery("select * from patient_masterdata where mobileNumber= '" + mobileNo + "' ");
		return rst.next();
	}
	
	public void closeButton(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
