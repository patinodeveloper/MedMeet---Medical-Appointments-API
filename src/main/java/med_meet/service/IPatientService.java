package med_meet.service;

import med_meet.model.Patient;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(Integer idPatient);
    Patient savePatient(Patient patient);
    void deletePatient(Patient patient);

    // Metodos personalizados
    List<Patient> findPatientsByNameContainingIgnoreCase(String name);
    List<Patient> findPatientsByDoctorId(Integer idDoctor);

}
