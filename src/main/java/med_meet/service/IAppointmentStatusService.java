package med_meet.service;

import med_meet.model.AppointmentStatus;

import java.util.List;

public interface IAppointmentStatusService {
    List<AppointmentStatus> getAllAppointmentStatuses();
    AppointmentStatus getAppointmentStatusesById(Integer idAppStatus);
    AppointmentStatus saveAppointmentStatus(AppointmentStatus appointmentStatus);
    void deleteAppointmentStatus(AppointmentStatus appointmentStatus);
}
