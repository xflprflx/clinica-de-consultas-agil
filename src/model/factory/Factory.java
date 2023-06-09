package model.factory;

import db.ConsultationStorage;
import db.PatientStorage;
import model.entities.Consultation;
import model.entities.Patient;

public class Factory {

	public static Patient newPatient(String name, String telephone) {
		return new Patient(name, telephone);
	}
	
	public static Consultation newConsultation(Patient patient, String day, String hour, String specialty) {
		return new Consultation(patient, day, hour, specialty);
	}
	
	public static PatientStorage createPatientStorage() {
		String patientFileName = "data_patients.csv";
		PatientStorage patientStorage = new PatientStorage(patientFileName);
		return patientStorage;
	}
	
	public static ConsultationStorage createConsultationStorage() {
		String consultationFileName = "data_consultations.csv";
		ConsultationStorage consultationStorage = new ConsultationStorage(consultationFileName);
		return consultationStorage;
	}
	
}
