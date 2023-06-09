package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import db.ConsultationStorage;
import db.PatientStorage;
import model.factory.Factory;
import model.services.ConsultationService;
import model.services.PatientService;

public class Program {
	
	public static void main(String[] args) {
		PatientStorage patientStorage = Factory.createPatientStorage();
		ConsultationStorage consultationStorage = Factory.createConsultationStorage();
		
		Scanner scanner = new Scanner(System.in);
		PatientService patientService = new PatientService();
		ConsultationService consultationService = new ConsultationService();
		
		patientStorage.loadData();
		consultationStorage.loadData();
		
		int option = 0;
		do {
			UI.mainMenu();
			try {
				option = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Opção inválida!");
				Program.main(args);
			}
			switch (option) {
			case 1:
				patientService.registerPatient(scanner, patientStorage);
				break;
			case 2:
				consultationService.scheduleConsultation(scanner, patientStorage, consultationStorage);
				break;
			case 3:
				consultationService.editConsultation(consultationStorage, scanner);
				break;
			case 4:
				System.out.println("Encerrando o programa...");
				break;
			default:
				System.out.println("Opção inválida!");
				break;
			}
		} while (option != 4);
		scanner.close();
	}
}
