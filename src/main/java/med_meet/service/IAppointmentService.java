package med_meet.service;

import med_meet.model.Appointment;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Integer idAppointment);
    Appointment saveAppointment(Appointment appointment);
    void deleteAppointment(Appointment appointment);
}
