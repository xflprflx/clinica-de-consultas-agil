package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.entities.Consultation;
import model.entities.Patient;

public class ConsultationStorage {
    private String file;
    private List<Consultation> consultations = new ArrayList<>();
	
    public ConsultationStorage(String file) {
        this.file = file;
    }
	
    public List<Consultation> getConsultations() {
		return consultations;
	}

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public void removeConsultation(Consultation consultation) {
        consultations.remove(consultation);
    }
    
    public void saveData() {
        File fileCSV = new File(file);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileCSV))) {
            for (Consultation consultation : consultations) {
                writer.println(consultation.getPatient().getName() + "," + consultation.getPatient().getTelephone() + ","
            + consultation.getDay() + 
                		"," + consultation.getHour() + "," + consultation.getSpecialty());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        consultations.clear();

        File fileCSV = new File(file);

        if (!fileCSV.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileCSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                String telephone = data[1];
                String day = data[2];
                String hour = data[3];
                String specialty = data[4];
                Patient patient = new Patient(name, telephone);
                Consultation consultation = new Consultation(patient, day, hour, specialty);
                consultations.add(consultation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}