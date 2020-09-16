package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import DBConnection.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import viewPatient.ViewPDController;

public class DashboardController {
	@FXML
	private Button btn_addPatient;

	@FXML
	private TextField txt_searchBox;

	@FXML
	private TableView<PatientData> tbl_patientDetails;

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

	@FXML
    private Pagination pgnation;
	
	private static TableView<PatientData> tblPatientTable;
	private static Connection con;
	private static final int ROWS_PER_PAGE = 10;
	private static ObservableList<PatientData> data = FXCollections.observableArrayList();

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		tblPatientTable = tbl_patientDetails;
		assert tbl_patientDetails != null : "fx:id=\"tbl_patientDetails\" was not injected: check your FXML file 'Dashboard.fxml'.";
		pID.setCellValueFactory(new PropertyValueFactory<PatientData, Integer>("patientId"));
		pName.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientName"));
		pAge.setCellValueFactory(new PropertyValueFactory<PatientData, String>("age"));
		pGender.setCellValueFactory(new PropertyValueFactory<PatientData, String>("gender"));
		pmobileNum.setCellValueFactory(new PropertyValueFactory<PatientData, String>("mobileNumber"));
		pEmail.setCellValueFactory(new PropertyValueFactory<PatientData, String>("emailId"));

		con = DBConnectivity.getConnection();
		refreshTable();
		buildData();

		int totalPage = (int) (Math.ceil(data.size() * 1.0 / ROWS_PER_PAGE));
        pgnation.setPageCount(totalPage);
        pgnation.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pgnation.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));

        tblPatientTable.setOnMouseClicked(e -> {
			if (tblPatientTable.getSelectionModel().getSelectedItem() != null) {
				ClickedPatient(tblPatientTable.getSelectionModel().getSelectedItem().getPatientId());
			}

		});
		
	}

	private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, data.size());

        int minIndex = Math.min(toIndex, data.size());
        SortedList<PatientData> sortedData = new SortedList<>(
                FXCollections.observableArrayList(data.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tblPatientTable.comparatorProperty());

        tblPatientTable.setItems(sortedData);

    }
	
	public static void refreshTable() {
		data.clear();
		try {
			String SQL = "SELECT * FROM patient_masterdata ORDER BY patient_id DESC";
			ResultSet rs = con.createStatement().executeQuery(SQL);
			while (rs.next()) {
				PatientData pd = new PatientData();
				pd.patientId.set(rs.getInt("patient_id"));
				pd.patientName.set(rs.getString("patient_name"));
				pd.dob.set(rs.getString("dob"));
				pd.age.set(rs.getString("age"));
				pd.gender.set(rs.getString("gender"));
				pd.mobileNumber.set(rs.getString("mobileNumber"));
				pd.emailId.set(rs.getString("emailId"));
				data.add(pd);
			}
			tblPatientTable.setItems(data);
			// Search Result
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	public void buildData() {
		try {
			refreshTable();

			txt_searchBox.setOnKeyReleased(e->{
				if(txt_searchBox.getText().equals("")) {
					refreshTable();
				}else {
					try {
						data.clear();
						String SQL;
						SQL = "SELECT * FROM patient_masterdata WHERE patient_name LIKE '%"+ txt_searchBox.getText() + "%'  OR mobileNumber LIKE '%"+ txt_searchBox.getText() +"%'";
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
						tblPatientTable.setItems(data);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});

//			txt_searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
//				flPatient.setPredicate(p -> {
//					// If filter text is empty, display all persons.
//					if (newValue == null || newValue.isEmpty()) {
//						return true;
//					}
//
//					if (String.valueOf(p.getMobileNumber()).contains(txt_searchBox.getText())) {
//						return true; // Filter matches mobile number.
//					} else if (String.valueOf(p.getPatientName()).toLowerCase()
//							.contains(txt_searchBox.getText().toLowerCase())) {
//						return true; // Filter matches name.
//					}
//					return false; // Does not match.
//				});
//			});

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	private static void ClickedPatient(int patientID) {
		try {
			try {
				AnchorPane pane=MainScreenController.getHomePage();
				for(int i=0;i<pane.getChildren().size();i++) {
					String paneID=pane.getChildren().get(i).getId();
					switch (paneID) {
						case "pane_Dashboard":
							MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
							break;
					}
				}
				Parent root = FXMLLoader.load(DashboardController.class.getResource("/viewPatient/ViewPatientDetails.fxml"));
				MainScreenController.getHomePage().getChildren().add(root);
				root.setTranslateX(370);
				root.setTranslateY(30);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ViewPDController.refreshViewDetails(patientID);
			ViewPDController.ViewTestDetails(patientID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void addPatients(ActionEvent event) {
		try {
			AnchorPane pane=MainScreenController.getHomePage();
			for(int i=0;i<pane.getChildren().size();i++) {
				String paneID=pane.getChildren().get(i).getId();
				switch (paneID) {
					case "pane_Dashboard":
						MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
						break;
					case "pane_viewDetails":
						MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
						break;
				}
			}
			Parent root = FXMLLoader.load(DashboardController.class.getResource("/addPatient/AddPatient.fxml"));
			root.getStylesheets().add(getClass().getResource("/cssFiles/addPatient.css").toExternalForm());
			MainScreenController.getHomePage().getChildren().add(root);
			root.setTranslateX(370);
			root.setTranslateY(30);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cant load window");
		}
	}

	public synchronized static TableView<PatientData> getPatienttable() {
		return tblPatientTable;
	}
}
