package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Consultation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Patient patient;
	private String day;
	private String hour;
	private String specialty;

	public Consultation(Patient patient, String day, String hour, String specialty) {
		this.patient = patient;
		this.day = day;
		this.hour = hour;
		this.specialty = specialty;
	}

	public Patient getPatient() {
		return patient;
	}

	public String getDay() {
		return day;
	}

	public String getHour() {
		return hour;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(day, hour, patient, specialty);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consultation other = (Consultation) obj;
		return Objects.equals(day, other.day) && Objects.equals(hour, other.hour)
				&& Objects.equals(patient, other.patient) && Objects.equals(specialty, other.specialty);
	}

	public void toString(int i) {
		System.out.println(i + 1 + ". " + day + " " + hour 
				+ " Especialidade: " + specialty + " Paciente: " + patient.getName());
	}
}
