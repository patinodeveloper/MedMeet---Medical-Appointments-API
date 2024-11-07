package med_meet.service.impl;

import med_meet.model.AppointmentStatus;
import med_meet.repository.AppointmentStatusRepository;
import med_meet.service.IAppointmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentStatusServiceImpl implements IAppointmentStatusService {
    @Autowired
    private AppointmentStatusRepository appointmentStatusRepository;

    @Override
    public List<AppointmentStatus> getAllAppointmentStatuses() {
        return appointmentStatusRepository.findAll();
    }

    @Override
    public AppointmentStatus getAppointmentStatusById(Integer idAppStatus) {
        return appointmentStatusRepository.findById(idAppStatus).orElse(null);
    }

    @Override
    public AppointmentStatus saveAppointmentStatus(AppointmentStatus appointmentStatus) {
        return appointmentStatusRepository.save(appointmentStatus);
    }

    @Override
    public void deleteAppointmentStatus(AppointmentStatus appointmentStatus) {
        appointmentStatusRepository.delete(appointmentStatus);
    }

    @Override
    public AppointmentStatus findByAppointmentId(Integer idAppointment) {
        return appointmentStatusRepository.findByAppointmentId(idAppointment);
    }
}
