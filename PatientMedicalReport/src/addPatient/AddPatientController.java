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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private TextField age;

	@FXML
	private Button cancelBtn;

	@FXML
	private Button addBtn;

	@FXML
	private Label valMob;

	@FXML
	private Label valName;

	@FXML
	private Label valGender;

	@FXML
	private Label valEmail;

	@FXML
	private Label valDOB;

	@FXML
	private Label ageLabel;

	private Connection connection;

	private PreparedStatement ps;

	private int flag;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dob.setDayCellFactory(param -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});

		dob.setOnAction(e -> {
			LocalDate today = LocalDate.now();
			LocalDate birthday = dob.getValue();
			int years = Period.between(birthday, today).getYears();
			ageLabel.setText(String.valueOf(years) + " years");
		});
	}

	public void addPatient(ActionEvent event) throws Exception {
		boolean check = checkPatientAlreadyExist(mobNo.getText());

		if (validateFields() && validateMobileNo() && validateName() && validateEmail()) {

			if (check == true) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Exist");
				alert.setHeaderText(null);
				alert.setContentText("Patient Already Exist");
				alert.showAndWait();
				clearFields();
			} else {
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
				String insert = "INSERT into patient_masterdata (mobileNumber,patient_name,gender,emailId,dob,age,active,created_timestamp,modified_timestamp) values(?,?,?,?,?,?,'Y','"
						+ timeStamp + "','" + timeStamp + "')";
				connection = DBConnectivity.getConnection();
				try {
					ps = connection.prepareStatement(insert);
					ps.setString(1, mobNo.getText());
					ps.setString(2, fullName.getText());
					ps.setString(3, getGender());
					ps.setString(4, email.getText());
					ps.setString(5, ((TextField) dob.getEditor()).getText());
				    ps.setString(6, ageLabel.getText());
					flag = ps.executeUpdate();

					if (flag > 0) {
						TableView<PatientData> patientTable = DashboardController.getPatienttable();
						FXMLLoader fxmlLoader;
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Dr Subodh App");
						alert.getButtonTypes().setAll(ButtonType.OK);
						alert.getDialogPane().setHeaderText("Patient added sucessfully!");
						alert.showAndWait().ifPresent(bt -> {
							if (bt == ButtonType.OK) {
								((Node) (event.getSource())).getScene().getWindow().hide();
								DashboardController.refreshTable();
							}
						});
					} else {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Dr Subodh App");
						alert.setContentText("Some Error occured during adding data!!..Please try again!");
						alert.showAndWait();
						clearFields();
					}
				} catch (SQLException e) {
					Alert alert = new Alert(Alert.AlertType.WARNING); 
					alert.setTitle("Dr Subodh App");
					alert.setContentText("Some Error occured during adding data!!..Please try again!");
					alert.showAndWait();
					clearFields();
					e.printStackTrace();
				}
			}
		}
	}

	public void cancelBtn(ActionEvent event) throws IOException {
		cancelBtn.getScene().getWindow().hide();
	}

	public String getGender() {
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

	private boolean validateFields() {
		if (mobNo.getText().isEmpty()) {
			valMob.setText("Please Enter Valid Mobile Number");
			return false;
		} else if (fullName.getText().isEmpty()) {
			valName.setText("Please Enter Valid Full Name");
			return false;
		} else if (!(male.isSelected() || female.isSelected() || others.isSelected())) {
			valGender.setText("Please Select Gender");
			return false;
		} else if (((TextField) dob.getEditor()).getText().isEmpty()) {
			valDOB.setText("Please Select DOB");
			return false;
		}
		return true;

	}

	private boolean validateMobileNo() {
		Pattern p = Pattern.compile("(0|91)?[6-9][0-9]{9}");
		Matcher m = p.matcher(mobNo.getText());
		if (m.find() && m.group().equals(mobNo.getText())) {
			return true;
		} else {
			valMob.setText("Please Enter Valid Mobile Number");
			return false;
		}

	}

	private boolean validateName() {

		Pattern p = Pattern.compile("[a-zA-Z\\s]+");
		Matcher m = p.matcher(fullName.getText());
		if (m.find() && m.group().equals(fullName.getText())) {
			return true;
		} else {
			valName.setText("Please Enter Valid Name");
			return false;
		}
	}

	private boolean validateEmail() {
		if (!(email.getText().isEmpty())) {
			Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
			Matcher m = p.matcher(email.getText());
			if (m.find() && m.group().equals(email.getText())) {
				return true;
			} else {
				valEmail.setText("Please Enter Valid Email");
				return false;
			}

		}
		return true;
	}

	public void clearFields() {
		mobNo.clear();
		fullName.clear();
		email.clear();
		dob.setValue(null);
		male.setSelected(false);
		female.setSelected(false);
		others.setSelected(false);
		ageLabel.setText("");
		valMob.setText("");
		valName.setText("");
		valGender.setText("");
		valEmail.setText("");
		valDOB.setText("");
	}
	public boolean checkPatientAlreadyExist(String mobileNo) throws Exception {
		Connection connection = DBConnectivity.getConnection();
		Statement stm = connection.createStatement();
		ResultSet rst = stm.executeQuery("select * from patient_masterdata where mobileNumber= '" + mobileNo + "' ");
		return rst.next();
	}
}
