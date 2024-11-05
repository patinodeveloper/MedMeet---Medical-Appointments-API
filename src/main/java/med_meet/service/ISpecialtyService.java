package med_meet.service;

import med_meet.model.Specialty;

import java.util.List;

public interface ISpecialtyService {
    List<Specialty> getAllSpecialty();
    Specialty getSpecialtyById(Integer idSpecialty);
    Specialty saveSpecialty(Specialty specialty);
    void deleteSpecialty(Specialty specialty);
}
