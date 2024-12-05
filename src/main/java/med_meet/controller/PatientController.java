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
@CrossOrigin("http://localhost:5173")
public class PatientController {

    private final Logger logger =
            LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private IPatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            logger.info("No se encontraron pacientes en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        patients.forEach(patient -> logger.info(patient.toString()));
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{idPatient}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer idPatient) {
        Patient patient = patientService.getPatientById(idPatient);
        if (patient == null) {
            logger.warn("Paciente con ID {} no encontrado.", idPatient);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Doctor encontrado: {}", patient);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> postPatient(@RequestBody Patient patient) {
        logger.info("Paciente a agregar: {}", patient);
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @PutMapping("/{idPatient}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer idPatient, @RequestBody Patient receivedPatient) {
        if (receivedPatient == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Patient patient = patientService.getPatientById(idPatient);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        patient.setFirstName(receivedPatient.getFirstName());
        patient.setLastName(receivedPatient.getLastName());
        patient.setAge(receivedPatient.getAge());
        patient.setSex(receivedPatient.getSex());
        patient.setPhoneNumber(receivedPatient.getPhoneNumber());
        patient.setAddress(receivedPatient.getAddress());

        patientService.savePatient(patient);

        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{idPatient}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer idPatient) {
        Patient patient = patientService.getPatientById(idPatient);
        if (patient == null) {
            logger.warn("Paciente con ID {} no encontrado para eliminación.", idPatient);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        patientService.deletePatient(patient);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Patient>> getPatientsByName(@PathVariable String name) {
        if (name == null || name.trim().isEmpty()) {
            logger.warn("El nombre no puede ser NULL o vacío");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Patient> patients = patientService.findPatientsByName(name);
        if (patients.isEmpty()) {
            logger.info("No se encontraron pacientes con el nombre: {}", name);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        patients.forEach(patient -> logger.info(patient.toString()));
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<Patient>> getPatientsByDoctorId(@PathVariable Integer idDoctor) {
        if (idDoctor == null || idDoctor <= 0) {
            logger.info("El idDoctor no puede ser NULL o inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Patient> patients = patientService.findPatientsByDoctorId(idDoctor);
        if (patients.isEmpty()) {
            logger.info("No se encontraron pacientes para el doctor con id: {}", idDoctor);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        patients.forEach(patient -> logger.info(patient.toString()));

        return ResponseEntity.ok(patients);
    }
}
