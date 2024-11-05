package med_meet.service;

import med_meet.model.Patient;

import java.util.List;

public interface IPatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(Integer idPatient);
    Patient savePatient(Patient patient);
    void deletePatient(Patient patient);
}