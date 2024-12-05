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
@CrossOrigin("http://localhost:5173")
public class DoctorController {

    private final Logger logger =
            LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private IDoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            logger.info("No se encontraron doctores en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        doctors.forEach(doctor -> logger.info(doctor.toString()));
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{idDoctor}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Integer idDoctor) {
        Doctor doctor = doctorService.getDoctorById(idDoctor);
        if (doctor == null) {
            logger.warn("Doctor con ID {} no encontrado.", idDoctor);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Doctor encontrado: {}", doctor);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<Doctor> postDoctor(@RequestBody Doctor doctor) {
        logger.info("Doctor a agregar: {}", doctor);
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @PutMapping("/{idDoctor}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Integer idDoctor, @RequestBody Doctor receivedDoctor) {
        // Verifica si el recibido doctor no es nulo
        if (receivedDoctor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Doctor doctor = doctorService.getDoctorById(idDoctor);
        // Si el doctor no existe, retorna un error 404
        if (doctor == null) {
            logger.info("No se encontraron Doctores con el ID: {} en el sistema", idDoctor);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        // Actualiza la información del doctor
        doctor.setFirstName(receivedDoctor.getFirstName());
        doctor.setLastName(receivedDoctor.getLastName());
        doctor.setEmail(receivedDoctor.getEmail());
        doctor.setSpecialty(receivedDoctor.getSpecialty());

        // Guarda el doctor actualizado
        doctorService.saveDoctor(doctor);
        // Devuelve el doctor actualizado
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{idDoctor}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer idDoctor) {
        Doctor doctor = doctorService.getDoctorById(idDoctor);
        if (doctor == null) {
            logger.warn("Doctor con ID {} no encontrado para eliminación.", idDoctor);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        doctorService.deleteDoctor(doctor);
        logger.info("Doctor con ID {} eliminado exitosamente.", idDoctor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/specialty/{idSpecialty}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialtyId(@PathVariable Integer idSpecialty) {
        List<Doctor> doctors = doctorService.findDoctorsBySpecialty(idSpecialty);
        if (doctors.isEmpty()) {
            logger.info("No se encontraron doctores en el sistema para la especialidad con el ID: {}", idSpecialty);
            // Retornamos NO_CONTENT para que no afecte al eliminar una especialidad
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        doctors.forEach(doctor -> logger.info(doctor.toString()));
        return ResponseEntity.ok(doctors);
    }
}
