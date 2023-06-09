package model.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import application.UI;
import db.ConsultationStorage;
import db.PatientStorage;
import model.entities.Consultation;
import model.entities.Patient;
import model.factory.Factory;
import model.util.Formatting;

public class ConsultationService {

	private PatientService patientService = new PatientService();

	public void scheduleConsultation(Scanner scanner, PatientStorage patientStorage, ConsultationStorage consultationStorage) {

		if (patientService.emptyPatientList(patientStorage)) {
			return;
		}

		patientService.listPatients(patientStorage);
		System.out.print("Selecione o número do paciente: ");
		int patientIdx = scanner.nextInt() - 1;
		if (patientIdx < 0 || patientIdx >= patientStorage.getPatients().size()) {
			System.out.println("Opção inválida!");
			return;
		}

		Patient patient = patientStorage.getPatients().get(patientIdx);
		String day = getDateValid(scanner);
		String hour = getHourValid(day, scanner);
		System.out.print("Digite a especialidade da consulta: ");
		scanner.nextLine();
		String specialty = scanner.nextLine();
		specialty = Formatting.removeSpaces(specialty).toUpperCase();

		Consultation newConsultation = Factory.newConsultation(patient, day, hour, specialty);
		if (ConsultationService.schedulingConflict(newConsultation, consultationStorage)) {
			return;
		}		
		consultationStorage.addConsultation(newConsultation);
		consultationStorage.saveData();
		System.out.println("Consulta agendada com sucesso");
	}

	public void rescheduleConsultation(int consultationIdx, ConsultationStorage consultationStorage, Scanner scanner) {

		if (consultationIdx < 0 || consultationIdx >= consultationStorage.getConsultations().size()) {
			System.out.println("Opção inválida!");
			return;
		}
		
		Patient patient = consultationStorage.getConsultations().get(consultationIdx).getPatient();
		String specialty = consultationStorage.getConsultations().get(consultationIdx).getSpecialty();
		String day = getDateValid(scanner);
		String hour = getHourValid(day, scanner);
		
		Consultation consultationRescheduled = Factory.newConsultation(patient, day, hour, specialty);
		if (ConsultationService.schedulingConflict(consultationRescheduled, consultationStorage)) {
			return;
		} else {
			consultationStorage.getConsultations().remove(consultationIdx);
			consultationStorage.getConsultations().add(consultationRescheduled);
			consultationStorage.saveData();
			System.out.println("Consulta remarcada com sucesso!");
		}
	}

	public void cancelConsultation(int consultationIdx, ConsultationStorage consultationStorage) {
		if (consultationIdx < 0 || consultationIdx >= consultationStorage.getConsultations().size()) {
			System.out.println("Opção inválida!");
			return;
		}
		consultationStorage.getConsultations().remove(consultationIdx);
		consultationStorage.saveData();
		System.out.println("Consulta cancelada com sucesso!");
	}

	public void editConsultation(ConsultationStorage consultationStorage, Scanner scanner) {
		if (consultationStorage.getConsultations().size() <= 0) {
			System.out.println("Nenhuma consulta agendada");
			return;
		}
		System.out.println("Agendamentos existentes:");

		for (int i = 0; i < consultationStorage.getConsultations().size(); i++) {
			Consultation consultation = consultationStorage.getConsultations().get(i);
			consultation.toString(i);
		}

		System.out.println("Selecione um agendamento para editar");
		while (!scanner.hasNextInt()) {
		    System.out.println("Opção inválida. Digite um número inteiro válido:");
		    scanner.next(); // Descarta a entrada inválida
		}
		int consultationIdx = scanner.nextInt() - 1;

		validateOptionConsultation(consultationIdx, consultationStorage, scanner);
		UI.consultationManager();
		while (!scanner.hasNextInt()) {
		    System.out.println("Opção inválida. Digite um número inteiro válido:");
		    scanner.next(); // Descarta a entrada inválida
		}
		int opcao = scanner.nextInt();
		switch (opcao) {
		case 1:
			rescheduleConsultation(consultationIdx, consultationStorage, scanner);
			break;
		case 2:
			cancelConsultation(consultationIdx, consultationStorage);
			break;
		case 3:

			break;
		default:
			System.out.println("Opção inválida!");
			break;
		}
	}

	public void validateOptionConsultation(int consultationIdx, ConsultationStorage consultationStorage, Scanner scanner) {
		if (consultationIdx < 0 || consultationIdx >= consultationStorage.getConsultations().size()) {
			System.out.println("Opção inválida!");
			editConsultation(consultationStorage, scanner);
			return;
		}
	}

	public boolean retroactiveDay(String typedDay) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate day = LocalDate.parse(typedDay, formatter);
		return day.isBefore(LocalDate.now());
	}

	public boolean retroactiveHour(String typedDay, String typedHour) {
		DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate day = LocalDate.parse(typedDay, formatDay);
		if (day.isEqual(LocalDate.now())) {
			DateTimeFormatter formatoHour = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime hour = LocalTime.parse(typedHour, formatoHour);
			return hour.isBefore(LocalTime.now());
		}
		return false;
	}

	public String getDateValid(Scanner scanner) {
		String day = null;
		boolean retroactiveDay = true;
		while (retroactiveDay) {
			boolean validFormatt = false;
			while (!validFormatt) {
				System.out.print("Digite a data da consulta (dd/mm/aaaa): ");
				day = scanner.next();
				validFormatt = Formatting.validateFormatDate(day);
				if (validFormatt == false) {
					System.out.println("Formato de data inválido!");
				}
			}

			retroactiveDay = retroactiveDay(day);
			if (retroactiveDay) {
				System.out.println("Não é possível realizar agendamento em datas passadas");
			}
		}
		day = Formatting.removeSpaces(day);
		return day;
	}

	private String getHourValid(String day, Scanner scanner) {
		String hour = null;
		boolean retroactiveHour = true;
		while (retroactiveHour) {
			boolean validFormatt = false;
			while (!validFormatt) {
				System.out.print("Digite a hora da consulta (hh:mm): ");
				hour = scanner.next();
				validFormatt = Formatting.validateFormatHour(hour);
				if (validFormatt == false) {
					System.out.println("Formato de hora inválido!");
				}
			}
			retroactiveHour = retroactiveHour(day, hour);
			if (retroactiveHour == true) {
				System.out.println("Não é possível realizar agendamento em horários passados");
			}
		}
		hour = Formatting.removeSpaces(hour);
		return hour;
	}
	
	public static boolean schedulingConflict(Consultation newConsultation, ConsultationStorage consultationStorage) {
		for (Consultation consultation : consultationStorage.getConsultations()) {
			if (consultation.getDay().equals(newConsultation.getDay()) && consultation.getHour().equals(newConsultation.getHour())
					&& (consultation.getPatient().equals(newConsultation.getPatient())
							|| consultation.getSpecialty().equals(newConsultation.getSpecialty()))) {
				System.out.println("Paciente e/ou especialista já tem uma consulta marcada nesse dia e horário!");
				return true;
			}
		}
		return false;
	}
}
