package model.services;

import java.util.Scanner;

import db.PatientStorage;
import model.entities.Patient;
import model.factory.Factory;
import model.util.Formatting;

public class PatientService {
		
	public void registerPatient(Scanner scanner, PatientStorage patientStorage) {
		System.out.print("Digite o nome do paciente: ");
		scanner.nextLine();
		String name = scanner.nextLine();
		String telephone = getValidTelephone(scanner);
		
		if(patientAlreadyExists(patientStorage, telephone)){
			return;
		}
		
		name = Formatting.removeSpaces(name).toUpperCase();
		telephone = Formatting.removeSpaces(telephone);
		
		Patient newPatient = Factory.newPatient(name, telephone);
		patientStorage.addPatient(newPatient);
		patientStorage.saveData();
		System.out.println("Paciente cadastrado com sucesso");
	}
	
	public void listPatients(PatientStorage storage) {
		System.out.println("Pacientes cadastrados:");
		for (int i = 0; i < storage.getPatients().size(); i++) {
			Patient patient = storage.getPatients().get(i);
			System.out.println((i + 1) + ". " + patient.getName());
		}
	}
	
	public boolean patientAlreadyExists(PatientStorage patientStorage, String telephone) {
		for (Patient patient : patientStorage.getPatients()) {
			if (patient.getTelephone().equals(telephone)) {
				System.out.println("Paciente já cadastrado!");
				return true;
			}
		}
		return false;
	}
	
	public String getValidTelephone(Scanner scanner) {
		String telephone = null;
		boolean validNumber = false;
		while (!validNumber) {
			System.out.print("Digite o telefone do paciente: ");
			telephone = scanner.next();
			validNumber = Formatting.validTelephone(telephone);
			if (!validNumber) {
				System.out.println("Número de telefone inválido!");
			}			
		}
		return telephone;
	}
	
	public boolean emptyPatientList(PatientStorage patientStorage) {
		if (patientStorage.getPatients().isEmpty()) {
			System.out.println("Não existem pacientes cadastrados!");
			return true;
		}
		return false;
	}
}
