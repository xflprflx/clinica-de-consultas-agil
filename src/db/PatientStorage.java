package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import model.entities.Patient;

public class PatientStorage {
    private String file;
    private List<Patient> patients = new ArrayList<>();

    public PatientStorage(String file) {
    	this.file = file;
    	
    }

    public List<Patient> getPatients() {
		return patients;
	}


    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public void saveData() {
        File fileCSV = new File(file);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileCSV, StandardCharsets.UTF_8))) {
            for (Patient patient : patients) {
                writer.println(patient.getName() + "," + patient.getTelephone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void loadData() {
        patients.clear();

        File fileCSV = new File(file);

        if (!fileCSV.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileCSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(",");
                String name = dados[0];
                String telephone = dados[1];
                Patient patient = new Patient(name, telephone);
                patients.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}