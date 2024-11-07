package med_meet.controller;

import med_meet.model.Patient;
import med_meet.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/patients")
public class PatientController {

    private Logger logger =
            LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private IPatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getAllPatients();
        patients.forEach(patient -> logger.info(patient.toString()));
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{idPatient}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer idPatient) {
        if (idPatient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Patient patient = patientService.getPatientById(idPatient);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> postPatient(@RequestBody Patient patient) {
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PutMapping("/{idPatient}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer idPatient, @RequestBody Patient receivedPatient) {
        if (receivedPatient == null) {
            return ResponseEntity.badRequest().build();
        }
        Patient patient = patientService.getPatientById(idPatient);
        patient.setFirstName(receivedPatient.getFirstName());
        patient.setLastName(receivedPatient.getLastName());
        patient.setAge(receivedPatient.getAge());
        patient.setSex(receivedPatient.getSex());
        patient.setPhoneNumber(receivedPatient.getPhoneNumber());
        patient.setAddress(receivedPatient.getAddress());
        patientService.savePatient(patient);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idPatient}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer idPatient) {
        if (idPatient == null) {
            return ResponseEntity.notFound().build();
        }
        Patient patient = patientService.getPatientById(idPatient);
        patientService.deletePatient(patient);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Patient>> getPatientsByName(@PathVariable String name) {
        if (name == null) {
            logger.info("El nombre no puede ser NULL");
            return ResponseEntity.badRequest().build();
        }
        List<Patient> patients = patientService.findPatientsByNameContainingIgnoreCase(name);
        patients.forEach(patient -> logger.info(patient.toString()));
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<Patient>> getPatientsByDoctorId(@PathVariable Integer idDoctor) {
        if (idDoctor == null) {
            logger.info("El id no puede ser NULL");
            return ResponseEntity.badRequest().build();
        }
        List<Patient> patients = patientService.findPatientsByDoctorId(idDoctor);
        patients.forEach(patient -> logger.info(patient.toString()));
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }
}
