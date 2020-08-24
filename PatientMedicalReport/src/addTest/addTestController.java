package addTest;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import DBConnection.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

	private Connection connection;

	private PreparedStatement ps;

	private ResultSet rs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

}
