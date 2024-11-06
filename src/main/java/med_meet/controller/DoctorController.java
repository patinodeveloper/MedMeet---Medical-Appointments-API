package med_meet.controller;

import med_meet.model.Doctor;
import med_meet.service.IDoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/doctors")
public class DoctorController {

    private Logger logger =
            LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private IDoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        doctors.forEach(doctor -> logger.info(doctor.toString()));
        return ResponseEntity.status(HttpStatus.OK).body(doctors);
    }

    @GetMapping("/{idDoctor}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Integer idDoctor) {
        Doctor doctor = doctorService.getDoctorById(idDoctor);
        logger.info(doctor.toString());
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<Doctor> postDoctor(@RequestBody Doctor doctor) {
        logger.info("Doctor a agregar: " + doctor.toString());
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(doctor);
        }
        doctorService.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @PutMapping("/{idDoctor}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Integer idDoctor, @RequestBody Doctor receivedDoctor) {
        Doctor doctor = doctorService.getDoctorById(idDoctor);
        logger.info("Doctor a editar: " + doctor.toString());
        if (receivedDoctor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(doctor);
        }
        doctor.setFirstName(receivedDoctor.getFirstName());
        doctor.setLastName(receivedDoctor.getLastName());
        doctor.setEmail(receivedDoctor.getEmail());
        doctor.setSpecialty(receivedDoctor.getSpecialty());
        doctorService.saveDoctor(doctor);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idDoctor}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer idDoctor) {
        if (idDoctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Doctor doctor = doctorService.getDoctorById(idDoctor);
        doctorService.deleteDoctor(doctor);
        return ResponseEntity.noContent().build();
    }
}
