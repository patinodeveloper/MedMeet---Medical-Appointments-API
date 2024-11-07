package med_meet.service.impl;

import med_meet.model.Specialty;
import med_meet.repository.SpecialtyRepository;
import med_meet.service.ISpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements ISpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty getSpecialtyById(Integer idSpecialty) {
        return specialtyRepository.findById(idSpecialty).orElse(null);
    }

    @Override
    public Specialty saveSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public void deleteSpecialty(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }

    @Override
    public Specialty findSpecialtyByName(String name) {
        return specialtyRepository.findSpecialtyByName(name);
    }

    @Override
    public Integer countDoctorsInSpecialty(Integer idSpecialty) {
        return specialtyRepository.countDoctorsInSpecialty(idSpecialty);
    }
}
