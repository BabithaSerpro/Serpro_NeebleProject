package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientData {
	public final SimpleIntegerProperty serialNum = new SimpleIntegerProperty();
	public final SimpleIntegerProperty patientId = new SimpleIntegerProperty();
	public final SimpleStringProperty patientName = new SimpleStringProperty();
	public final SimpleStringProperty dob = new SimpleStringProperty();
	public final SimpleStringProperty age = new SimpleStringProperty();
	public final SimpleStringProperty gender = new SimpleStringProperty();
	public final SimpleStringProperty mobileNumber = new SimpleStringProperty();
	public final SimpleStringProperty emailId = new SimpleStringProperty();

	public int getSerialNum() {
		return serialNum.get();
	}
	
	public int getPatientId() {
		return patientId.get();
	}

	public String getPatientName() {
		return patientName.get();
	}

	public String getDob() {
		return dob.get();
	}

	public String getAge() {
		return age.get();
	}

	public String getGender() {
		return gender.get();
	}

	public String getMobileNumber() {
		return mobileNumber.get();
	}

	public String getEmailId() {
		return emailId.get();
	}
}
