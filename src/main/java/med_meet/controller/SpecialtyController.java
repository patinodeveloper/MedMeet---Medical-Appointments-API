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
public class SpecialtyController {

    private Logger logger =
            LoggerFactory.getLogger(SpecialtyController.class);

    @Autowired
    private ISpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> getSpecialties() {
        List<Specialty> specialties = specialtyService.getAllSpecialties();
        specialties.forEach(specialty -> logger.info(specialty.toString()));
        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{idSpecialty}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable Integer idSpecialty) {
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specialty);
    }

    @PostMapping
    public ResponseEntity<Specialty> postSpecialty(@RequestBody Specialty specialty) {
        if (specialty == null) {
            return ResponseEntity.badRequest().build();
        }
        specialtyService.saveSpecialty(specialty);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialty);
    }

    @PutMapping("/{idSpecialty}")
    public ResponseEntity<?> updateSpecialty(
            @PathVariable Integer idSpecialty, @RequestBody Specialty receivedSpecialty) {
        if (receivedSpecialty == null) {
            return ResponseEntity.badRequest().build();
        }
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            return ResponseEntity.notFound().build();
        }
        specialty.setName(receivedSpecialty.getName());
        specialtyService.saveSpecialty(specialty);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idSpecialty}")
    public ResponseEntity<?> deleteSpecialty(@PathVariable Integer idSpecialty) {
        Specialty specialty = specialtyService.getSpecialtyById(idSpecialty);
        if (specialty == null) {
            return ResponseEntity.notFound().build();
        }
        specialtyService.deleteSpecialty(specialty);

        return ResponseEntity.noContent().build();
    }
}
