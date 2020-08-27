package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import DBConnection.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import viewPatient.ViewPDController;


public class DashboardController {
	@FXML
	private AnchorPane mainPane;

	@FXML
	private Button btn_addPatient;

	@FXML
	private TextField txt_searchBox;

	@FXML
	private Label lbl_Wishes;

	@FXML
	private TableView<PatientData> tbl_patientDetails;

	@FXML
	private TableColumn<PatientData, Integer> SNo;
	@FXML
	private TableColumn<PatientData, Integer> pID;
	@FXML
	private TableColumn<PatientData, String> pName;
	@FXML
	private TableColumn<PatientData, String> pAge;
	@FXML
	private TableColumn<PatientData, String> pGender;
	@FXML
	private TableColumn<PatientData, String> pmobileNum;
	@FXML
	private TableColumn<PatientData, String> pEmail;

	private static TableView<PatientData> tblPatientTable;
	private static Connection con;

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		tblPatientTable = tbl_patientDetails;
		assert tbl_patientDetails != null : "fx:id=\"tbl_patientDetails\" was not injected: check your FXML file 'Dashboard.fxml'.";
		SNo.setCellValueFactory(new PropertyValueFactory<PatientData, Integer>("serialNum"));
		pID.setCellValueFactory(new PropertyValueFactory<PatientData, Integer>("patientId"));
		pName.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientName"));
		pAge.setCellValueFactory(new PropertyValueFactory<PatientData, String>("age"));
		pGender.setCellValueFactory(new PropertyValueFactory<PatientData, String>("gender"));
		pmobileNum.setCellValueFactory(new PropertyValueFactory<PatientData, String>("mobileNumber"));
		pEmail.setCellValueFactory(new PropertyValueFactory<PatientData, String>("emailId"));

		con = DBConnectivity.getConnection();

		tblPatientTable.setOnMouseClicked(e -> {
			if (tblPatientTable.getSelectionModel().getSelectedItem() != null) {
				ClickedPatient(tblPatientTable.getSelectionModel().getSelectedItem().getPatientId());
			}

		});
		buildData();
		sidePanel();
	}

	public void sidePanel() {
		Calendar cal = Calendar.getInstance();

		int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

		if (timeOfDay >= 0 && timeOfDay < 12) {
			lbl_Wishes.setText("Good Morning!");
		} else if (timeOfDay >= 12 && timeOfDay < 16) {
			lbl_Wishes.setText("Good Afternoon!");
		} else if (timeOfDay >= 16 && timeOfDay < 21) {
			lbl_Wishes.setText("Good Evening!");
		} else if (timeOfDay >= 21 && timeOfDay < 24) {
			lbl_Wishes.setText("Good Night!");
		}
	}

	private static ObservableList<PatientData> data = FXCollections.observableArrayList();
	private static FilteredList<PatientData> flPatient;

	public static void refreshTable() {
		data.clear();
		try {
			String SQL = "SELECT * FROM patient_masterdata ORDER BY patient_id DESC";
			ResultSet rs = con.createStatement().executeQuery(SQL);
			while (rs.next()) {
				PatientData pd = new PatientData();
				pd.serialNum.set(rs.getRow());
				pd.patientId.set(rs.getInt("patient_id"));
				pd.patientName.set(rs.getString("patient_name"));
				pd.dob.set(rs.getString("dob"));
				pd.age.set(rs.getString("age"));
				pd.gender.set(rs.getString("gender"));
				pd.mobileNumber.set(rs.getString("mobileNumber"));
				pd.emailId.set(rs.getString("emailId"));
				data.add(pd);
			}
//			tblPatientTable.setItems(data);
			// Search Result
			flPatient = new FilteredList(data, p -> true);// Pass the data to a filtered list
			tblPatientTable.setItems(flPatient);// Set the table's items using the filtered list

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	public void buildData() {
		try {
			refreshTable();

//			txt_searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
//				try {
//					data.clear();
//					System.out.println("newvalue "+newValue);
//					String SQL;
//					if(!(newValue.matches("[0-9]"))) {
//						SQL = "SELECT * FROM patient_masterdata WHERE patient_name LIKE '"+ newValue + "'";
//						
//					}else {
//						SQL = "SELECT * FROM patient_masterdata WHERE mobileNumber LIKE '"+ newValue + "'";
//					}
//					ResultSet rs = con.createStatement().executeQuery(SQL);
//					
//					while (rs.next()) {
//						PatientData pd = new PatientData();
//						pd.serialNum.set(rs.getRow());
//						pd.patientId.set(rs.getInt("patient_id"));
//						pd.patientName.set(rs.getString("patient_name"));
//						pd.dob.set(rs.getString("dob"));
//						pd.age.set(rs.getString("age"));
//						pd.gender.set(rs.getString("gender"));
//						pd.mobileNumber.set(rs.getString("mobileNumber"));
//						pd.emailId.set(rs.getString("emailId"));
//						data.add(pd);
//					}
//					tblPatientTable.setItems(data);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});

			txt_searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
				flPatient.setPredicate(p -> {
					// If filter text is empty, display all persons.
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					if (String.valueOf(p.getMobileNumber()).contains(txt_searchBox.getText())) {
						return true; // Filter matches mobile number.
					} else if (String.valueOf(p.getPatientName()).toLowerCase()
							.contains(txt_searchBox.getText().toLowerCase())) {
						return true; // Filter matches name.
					}
					return false; // Does not match.
				});
			});
			//not required
//			txt_searchBox.setOnKeyReleased(keyEvent -> {
//				if (keyEvent.getText().matches("[0-9]")) {
//					flPatient.setPredicate(p -> p.getMobileNumber().contains(txt_searchBox.getText().trim()));
//				} else {
//					flPatient.setPredicate(p -> p.getPatientName().toLowerCase()
//							.contains(txt_searchBox.getText().toLowerCase().trim()));
//				}
//
//			});

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	private static void ClickedPatient(int patientID) {
		try {
			FXMLLoader fxmlloader = new FXMLLoader(DashboardController.class.getResource("/viewPatient/ViewPatientDetails.fxml"));
			Parent workspace = (Parent) fxmlloader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(workspace));
			stage.setTitle("Patient Details");
			stage.getIcons().add(new Image("file:imgs/sdIcon.png"));
			stage.setResizable(false);
			stage.show();

			ViewPDController.ViewDetails(patientID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void addPatients(ActionEvent event) {
		try {
			FXMLLoader fxmlloader = new FXMLLoader(DashboardController.class.getResource("/addPatient/AddPatient.fxml"));
			Parent workspace = (Parent) fxmlloader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(workspace));
			stage.setTitle("New Patient Details");
			stage.getIcons().add(new Image("file:imgs/sdIcon.png"));
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cant load window");
		}
	}

	public synchronized static TableView<PatientData> getPatienttable() {
		return tblPatientTable;
	}
}
