package med_meet.service;

import med_meet.model.AppointmentStatus;

import java.util.List;

public interface IAppointmentStatusService {
    List<AppointmentStatus> getAllAppointmentStatuses();
    AppointmentStatus getAppointmentStatusById(Integer idAppStatus);
    AppointmentStatus saveAppointmentStatus(AppointmentStatus appointmentStatus);
    void deleteAppointmentStatus(AppointmentStatus appointmentStatus);

    // Metodos personalizados
    AppointmentStatus findByAppointmentId(Integer idAppointment);

}
