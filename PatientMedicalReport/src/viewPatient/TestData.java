package viewPatient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestData {
	public final SimpleIntegerProperty patientId = new SimpleIntegerProperty();
	public final SimpleStringProperty testName = new SimpleStringProperty();
	public final SimpleStringProperty date = new SimpleStringProperty();
	public final SimpleStringProperty status = new SimpleStringProperty();
	
	public int getPatientId() {
		return patientId.get();
	}
	
	public SimpleStringProperty getTestName() {
		return testName;
	}

	public SimpleStringProperty getDate() {
		return date;
	}

	public SimpleStringProperty getStatus() {
		return status;
	}
}
