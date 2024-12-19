package med_meet.service.impl;

import med_meet.model.Appointment;
import med_meet.repository.AppointmentRepository;
import med_meet.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Integer idAppointment) {
        return appointmentRepository.findById(idAppointment).orElse(null);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndDate(Integer idDoctor, LocalDate date) {
        return appointmentRepository.findAppointmentsByDoctorAndDate(idDoctor, date);
    }

    @Override
    public List<Appointment> getAppsByDoctorId(Integer idDoctor) {
        return appointmentRepository.findByDoctorId(idDoctor);
    }

    @Override
    public List<Appointment> getAppsByPatientId(Integer idPatient) {
        return appointmentRepository.findByPatientId(idPatient);
    }

}
