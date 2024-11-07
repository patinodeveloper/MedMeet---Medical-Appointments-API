package med_meet.service;

import med_meet.model.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Integer idAppointment);
    Appointment saveAppointment(Appointment appointment);
    void deleteAppointment(Appointment appointment);

    // Metodos personalizados
    List<Appointment> getAppointmentsByDoctorAndDate(Integer idDoctor, LocalDate date);
    List<Appointment> getAppsByDoctorId(Integer idDoctor);
    List<Appointment> getAppsByPatientId(Integer idPatient);
    List<Appointment> getAppsByStatusId(Integer idStatus);
}
