package addPatient;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewPatient.ViewPDController;

public class EditPatientController implements Initializable {

	
	@FXML
	private AnchorPane pane_editPatient;

	@FXML
	private TextField mobNo;

	@FXML
	private TextField fullName;

	@FXML
	private TextField email;

	@FXML
	private RadioButton male;

	@FXML
	private ToggleGroup genderR;

	@FXML
	private RadioButton others;

	@FXML
	private RadioButton female;

	@FXML
	private DatePicker dob;

	@FXML
	private Button updateBtn;

	@FXML
	private Button cancelBtn;

	@FXML
	private TextField ageLabel;

	@FXML
	private Button btn_Close;

	@FXML
	private Label pId;

	@FXML
	private Label pname;

	private Connection connection;

	private ResultSet rs;

	private PreparedStatement ps;

	private static AnchorPane paneEditPatient;
	private static TextField mblNo,fName,p_email,p_age;
	private static RadioButton p_male,p_female,p_others;
	private static DatePicker p_dob;
	private int flag;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneEditPatient=pane_editPatient;
		mblNo=mobNo;
		fName=fullName;
		p_email=email;
		p_age=ageLabel;
		p_male=male;
		p_female=female;
		p_others=others;
		p_dob=dob;
		
		pId.setText(ViewPDController.getLblID().getText());
		pname.setText(ViewPDController.getLblPName().getText());

		try {
			ViewDetails();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// disable editor
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
				ageLabel.setText(p.getYears() + " years " + p.getMonths() + " months");
			}
		});

	}

	public void ViewDetails() throws SQLException {
		String update = "SELECT * FROM patient_masterdata WHERE patient_id='" + pId.getText() + "'";
		try {
			connection = DBConnectivity.getConnection();
			ps = connection.prepareStatement(update);
			rs = ps.executeQuery();

			while (rs.next()) {
				mobNo.setText(rs.getString("mobileNumber"));
				if ("Male".equals(rs.getString("gender"))) {
					male.setSelected(true);
				} else if ("Female".equals(rs.getString("gender"))) {
					female.setSelected(true);
				} else if ("Other".equals(rs.getString("gender"))) {
					others.setSelected(true);
				} else {
					male.setSelected(false);
					female.setSelected(false);
					others.setSelected(false);
				}
				fullName.setText(rs.getString("patient_name"));
				email.setText(rs.getString("emailId"));
				dob.getEditor().setText(rs.getString("dob"));
				ageLabel.setText(rs.getString("age"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void cancelBtn(ActionEvent event) throws IOException {
		//checking if any data present
		if(mblNo.getText()!=null || fName.getText()!=null || p_email.getText()!=null || p_dob.getEditor().getText()!=null || p_age.getText()!=null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Dr Subodh App");
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
			alert.getDialogPane().setHeaderText("Are you Sure!! Details not saved!!");
			alert.showAndWait().ifPresent(bt -> {
				if (bt == ButtonType.YES) {
					close();
				}else if(bt == ButtonType.NO) {
					event.consume();
				}
			});
		}else {
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
			case "pane_editPatient":
				MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
				break;
			}
		}
	}
	public void updatePatient() throws Exception {
		if (validateFields() && validateMobileNo() && validateName() && validateEmail()) {
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());

			String update = "UPDATE patient_masterdata SET mobileNumber=?,"
					+ "patient_name=?,gender=?,emailId=?,dob=?,age=?,active='Y', modified_timestamp='" + timeStamp
					+ "' WHERE patient_id=?";

			connection = DBConnectivity.getConnection();
			try {
				ps = connection.prepareStatement(update);
				ps.setString(1, mobNo.getText());
				ps.setString(2, fullName.getText());
				ps.setString(3, getGender());
				ps.setString(4, email.getText());
				ps.setString(5, ((TextField) dob.getEditor()).getText());
				ps.setString(6, ageLabel.getText());
				int pid = Integer.parseInt(pId.getText());
				ps.setInt(7, pid);

				flag = ps.executeUpdate();

				if (flag > 0) { // redirecting to dashboard
					Parent root = FXMLLoader.load(getClass().getResource("/addPatient/successupdate.fxml"));
					paneEditPatient.getChildren().add(root);
					clearFields();
					ViewPDController.refreshViewDetails(pid);
				} else {
					Parent root = FXMLLoader.load(getClass().getResource("/addPatient/unsuccessUpdate.fxml"));
					paneEditPatient.getChildren().add(root);
					clearFields();
				}
			} catch (SQLException e) { // catching exception if any backend error occurs
				Parent root = FXMLLoader.load(getClass().getResource("/addPatient/failure.fxml"));
				Stage primaryStage = new Stage();
				Scene scene = new Scene(root,400,400);
				primaryStage.setScene(scene);
				primaryStage.show();
				
				e.printStackTrace();
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

	private boolean validateFields() { // null validation of each field
		if (mobNo.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Mobile Number Cannot Be Empty");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;
		} else if (fullName.getText().isEmpty()) {
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
		} else if (((TextField) dob.getEditor()).getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dr. Subodh App");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setContentText("Please Select DOB");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");
			alert.showAndWait();
			return false;

		}
		return true;

	}

	private boolean validateMobileNo() { // Mobile No. Validation
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

	public static void clearFields() { // clearing fileds
		mblNo.clear();
		fName.clear();
		p_email.clear();
		p_dob.getEditor().setText("");
		p_male.setSelected(false);
		p_female.setSelected(false);
		p_others.setSelected(false);
		p_age.clear();

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
	public static AnchorPane getPaneEditPatient() {
		return paneEditPatient;
	}

}
