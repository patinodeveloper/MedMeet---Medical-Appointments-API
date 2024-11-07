package med_meet.service.impl;

import med_meet.model.Doctor;
import med_meet.repository.DoctorRepository;
import med_meet.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements IDoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Integer idDoctor) {
        return doctorRepository.findById(idDoctor).orElse(null);
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> findDoctorsBySpecialty(Integer idSpecialty) {
        return doctorRepository.findDoctorsBySpecialty(idSpecialty);
    }
}
