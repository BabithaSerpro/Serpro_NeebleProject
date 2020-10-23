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
import application.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.time.LocalDate;
import java.time.Period;

import javafx.scene.control.DateCell;

public class AddPatientController implements Initializable {
	@FXML
	public AnchorPane pane_newPatient;

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

	/*
	 * @FXML private DatePicker dob;
	 */

	@FXML
	private Button cancelBtn;

	@FXML
	private Button addBtn;

	@FXML
	private TextField ageLabel;

	private static AnchorPane paneNewPatient;
	private static TextField mblNo, fName, p_email, p_age;
	private static RadioButton p_male, p_female, p_others;
	private static DatePicker p_dob;

	private Connection connection;

	private PreparedStatement ps;

	private ResultSet rs;

	private int flag;

	public static int pId;

	public static int getpId() {
		return pId;
	}

	public void setpId(int id) {
		pId = id;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneNewPatient = pane_newPatient;
		mblNo = mobNo;
		fName = fullName;
		p_email = email;
		p_age = ageLabel;
		p_male = male;
		p_female = female;
		p_others = others;
	//	p_dob = dob;
		// disable editor
	/*
	 * dob.getEditor().setDisable(true); dob.setStyle("-fx-opacity: 1");
	 * dob.getEditor().setStyle("-fx-opacity: 1");
	 */

		// disable future date
		/*
		 * dob.setDayCellFactory(param -> new DateCell() {
		 * 
		 * @Override public void updateItem(LocalDate date, boolean empty) {
		 * super.updateItem(date, empty); setDisable(empty ||
		 * date.compareTo(LocalDate.now()) > 0); } });
		 */

		// set age from calender selection
		/*
		 * dob.setOnAction(e -> { LocalDate today = LocalDate.now(); LocalDate birthday
		 * = dob.getValue(); Period p = Period.between(birthday, today); if
		 * (p.getYears() <= 0) { ageLabel.setText(p.getMonths() + " months"); if
		 * (p.getMonths() <= 0) { ageLabel.setText(p.getDays() + " days"); } } else {
		 * ageLabel.setText(p.getYears() + " years " + p.getMonths() + " months"); } });
		 */
	}

	public void addPatient(ActionEvent event) throws Exception {
		boolean check = checkPatientAlreadyExist(mobNo.getText());

		if (validateFields() && validateMobileNo() && validateName() &&validateAge() && validateEmail()) {

			if (check == true) { // check existing patient

				Parent root = FXMLLoader.load(getClass().getResource("/addPatient/exists.fxml"));
				paneNewPatient.getChildren().add(root);
				clearFields();
			} else { // insert new patient
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
				String insert = "INSERT into patient_masterdata (mobileNumber,patient_name,gender,emailId,age,active,created_timestamp,modified_timestamp) values(?,?,?,?,?,'Y','"
						+ timeStamp + "','" + timeStamp + "')";
				connection = DBConnectivity.getConnection();
				try {
					ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, mobNo.getText());
					ps.setString(2, fullName.getText());
					ps.setString(3, getGender());
					ps.setString(4, email.getText());
					//ps.setString(5, ((TextField) dob.getEditor()).getText());
					ps.setString(5, ageLabel.getText());
					flag = ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						pId = rs.getInt(1);
					}
					if (flag > 0) { // redirecting to dashboard

						Parent root = FXMLLoader.load(getClass().getResource("/addPatient/success.fxml"));
						paneNewPatient.getChildren().add(root);
						clearFields();
					} else {

						Parent root = FXMLLoader.load(getClass().getResource("/addPatient/failure.fxml"));
						pane_newPatient.getChildren().add(root);
						clearFields();

					}
				} catch (SQLException e) { // catching exception if any backend error occurs
					Parent root = FXMLLoader.load(getClass().getResource("/addPatient/failure.fxml"));
					pane_newPatient.getChildren().add(root);
					clearFields();
					e.printStackTrace();
				}
			}
		}
	}

	public void cancelBtn(ActionEvent event) throws IOException {
		// checking if any data present
		if (mblNo.getText() != null || fName.getText() != null || p_email.getText() != null
				|| p_dob.getEditor().getText() != null || p_age.getText() != null) {
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
				MainScreenController.getHomePage().getChildren().get(i).setVisible(true);
				break;
			case "pane_viewDetails":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			case "pane_newPatient":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			}
		}
	}

	public String getGender() { // selection of gender
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
		// null validation of each field
		/*
		 * if (mobNo.getText().isEmpty()) { Alert alert = new Alert(AlertType.WARNING);
		 * alert.setTitle("Dr. Subodh App"); alert.setHeaderText(null);
		 * alert.initStyle(StageStyle.TRANSPARENT);
		 * alert.setContentText("Mobile Number Cannot Be Empty"); DialogPane dialogPane
		 * = alert.getDialogPane();
		 * dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").
		 * toExternalForm()); dialogPane.getStyleClass().add("myDialog");
		 * alert.showAndWait(); return false; } else
		 */ if (fullName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Full Name Cannot Be Empty");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		} else if (!(male.isSelected() || female.isSelected() || others.isSelected())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Select Gender");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		} else if (ageLabel.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Age Cannot be Empty");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;

		}
		return true;

	}

	private boolean validateMobileNo() { // Mobile No. Validation
		if (!(mobNo.getText().isEmpty())) {
			Pattern p = Pattern.compile("(0|91)?[5-9][0-9]{9}");
			Matcher m = p.matcher(mobNo.getText());
			if (m.find() && m.group().equals(mobNo.getText())) {
				return true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Please Enter Valid Mobile Number");
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
				dialogPane.getStyleClass().add("myDialog");
				alert.showAndWait();
				return false;
			}
		}
		return true;
	}
	
	private boolean validateAge() {
		Pattern p = Pattern.compile("^[0-4]\\d*(\\.\\d+)?$");
		Matcher m = p.matcher(ageLabel.getText());
		if (m.find() && m.group().equals(ageLabel.getText())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Enter Valid Age");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		}
	}

	private boolean validateName() { // Name Validation

		Pattern p = Pattern.compile("[a-zA-Z\\s]+");
		Matcher m = p.matcher(fullName.getText());
		if (m.find() && m.group().equals(fullName.getText())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Enter Valid Name");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		}
	}

	private boolean validateEmail() { // Email Validation
		if (!(email.getText().isEmpty())) {
			Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
			Matcher m = p.matcher(email.getText());
			if (m.find() && m.group().equals(email.getText())) {
				return true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dr. Subodh App");
				alert.setHeaderText(null);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.setContentText("Please Enter Valid Email");
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
				dialogPane.getStyleClass().add("myDialog");
				alert.showAndWait();
				return false;
			}

		}
		return true;
	}

	public static void clearFields() { // clearing fileds
		mblNo.clear();
		fName.clear();
		p_email.clear();
		//p_dob.getEditor().setText("");
		p_male.setSelected(false);
		p_female.setSelected(false);
		p_others.setSelected(false);
		p_age.clear();

	}

	public boolean checkPatientAlreadyExist(String mobileNo) throws Exception {
		Connection connection = DBConnectivity.getConnection();
		Statement stm = connection.createStatement();
		ResultSet rst = stm.executeQuery("select * from patient_masterdata where mobileNumber= '" + mobileNo + "' and active='Y' ");
		return rst.next();
	}

	public static AnchorPane getPaneNewPatient() {
		return paneNewPatient;
	}

}