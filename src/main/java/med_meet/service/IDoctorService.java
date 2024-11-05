package med_meet.service;

import med_meet.model.Doctor;

import java.util.List;

public interface IDoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Integer idDoctor);
    Doctor saveDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
}
