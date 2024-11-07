package med_meet.service;

import med_meet.model.Specialty;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISpecialtyService {
    List<Specialty> getAllSpecialties();
    Specialty getSpecialtyById(Integer idSpecialty);
    Specialty saveSpecialty(Specialty specialty);
    void deleteSpecialty(Specialty specialty);

    // Metodos personalizados
    Specialty findSpecialtyByName(String name);
    Integer countDoctorsInSpecialty(Integer idSpecialty);
}
