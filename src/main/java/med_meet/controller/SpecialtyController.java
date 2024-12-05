package med_meet.controller;

import med_meet.model.Specialty;
import med_meet.service.ISpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/specialties")
@CrossOrigin("http://localhost:5173")
public class SpecialtyController {

    private final Logger logger =
            LoggerFactory.getLogger(SpecialtyController.class);

    @Autowired
    private ISpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> getSpecialties() {
        List<Specialty> specialties = specialtyService.getAllSpecialties();
        if (specialties.isEmpty()) {
            logger.info("No se encontraron especialidades en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        specialties.forEach(specialty -> logger.info(specialty.toString()));
        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{idSpecialty}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable Integer idSpecialty) {
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            logger.warn("Doctor con ID {} no encontrado.", idSpecialty);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Especialidad encontrada: {}", specialty);
        return ResponseEntity.ok(specialty);
    }

    @PostMapping
    public ResponseEntity<Specialty> postSpecialty(@RequestBody Specialty specialty) {
        logger.info("Especialidad a agregar: {}", specialty);
        Specialty savedSpecialty = specialtyService.saveSpecialty(specialty);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSpecialty);
    }

    @PutMapping("/{idSpecialty}")
    public ResponseEntity<?> updateSpecialty(
            @PathVariable Integer idSpecialty, @RequestBody Specialty receivedSpecialty) {
        if (receivedSpecialty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        specialty.setName(receivedSpecialty.getName());

        specialtyService.saveSpecialty(specialty);
        return ResponseEntity.ok(specialty);
    }

    @DeleteMapping("/{idSpecialty}")
    public ResponseEntity<?> deleteSpecialty(@PathVariable Integer idSpecialty) {
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            logger.warn("Especialidad con ID {} no encontrado para eliminación.", idSpecialty);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        specialtyService.deleteSpecialty(specialty);
        logger.info("Especialidad con ID {} eliminada exitosamente.", idSpecialty);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Specialty> getSpecialtyByName(@PathVariable String name) {
        Specialty specialty = specialtyService.findSpecialtyByName(name);
        if (specialty == null) {
            logger.info("Especialidad no encontrada con el nombre: {}", name);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        logger.info("Especialidad: {}", specialty);
        return ResponseEntity.ok(specialty);
    }

    @GetMapping("/doctors/{idSpecialty}")
    public ResponseEntity<Integer> getCountDoctorsInSpecialty(@PathVariable Integer idSpecialty) {
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            logger.info("Especialidad no encontrada con el ID: {}", idSpecialty);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Integer DoctorsNumber = specialtyService.countDoctorsInSpecialty(idSpecialty);
        if (DoctorsNumber == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        logger.info("Doctores (Cantidad) de la especialidad {}: {}", specialty, DoctorsNumber);
        return ResponseEntity.ok(DoctorsNumber);
    }
}
