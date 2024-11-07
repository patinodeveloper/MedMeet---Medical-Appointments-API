package med_meet.controller;

import med_meet.model.AppointmentStatus;
import med_meet.service.IAppointmentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/appointment-status")
public class AppointmentStatusController {

    private Logger logger =
            LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private IAppointmentStatusService appointmentStatusService;

    @GetMapping
    public ResponseEntity<List<AppointmentStatus>> getAppointmentStatuses() {
        List<AppointmentStatus> appointmentStatuses = appointmentStatusService.getAllAppointmentStatuses();
        appointmentStatuses.forEach(appointmentStatus -> logger.info(appointmentStatus.toString()));
        return ResponseEntity.ok(appointmentStatuses);
    }

    @GetMapping("/{idAppStatus}")
    public ResponseEntity<AppointmentStatus> getAppointmentStatusById(@PathVariable Integer idAppStatus) {
        AppointmentStatus appointmentStatus = appointmentStatusService.getAppointmentStatusById(idAppStatus);
        if (appointmentStatus == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointmentStatus);
    }

    @PostMapping
    public ResponseEntity<AppointmentStatus> postAppointmentStatus(@RequestBody AppointmentStatus appointmentStatus) {
        if (appointmentStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        appointmentStatusService.saveAppointmentStatus(appointmentStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentStatus);
    }

    @PutMapping("/{idAppStatus}")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable Integer idAppStatus, @RequestBody AppointmentStatus receivedAppointmentStatus) {
        if (receivedAppointmentStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        AppointmentStatus appointmentStatus = appointmentStatusService.getAppointmentStatusById(idAppStatus);
        if (appointmentStatus == null) {
            return ResponseEntity.notFound().build();
        }
        appointmentStatus.setStatus(receivedAppointmentStatus.getStatus());
        appointmentStatusService.saveAppointmentStatus(appointmentStatus);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAppStatus}")
    public ResponseEntity<?> deleteAppointmentStatus(@PathVariable Integer idAppStatus) {
        AppointmentStatus appointmentStatus = appointmentStatusService.getAppointmentStatusById(idAppStatus);
        if (appointmentStatus == null) {
            return ResponseEntity.notFound().build();
        }
        appointmentStatusService.deleteAppointmentStatus(appointmentStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointment/{idAppointment}")
    public ResponseEntity<AppointmentStatus> getAppStatusByAppointmentId(@PathVariable Integer idAppointment) {
        if (idAppointment == null) {
            return ResponseEntity.badRequest().build();
        }
        AppointmentStatus appointmentStatus = appointmentStatusService.findByAppointmentId(idAppointment);
        if (appointmentStatus == null) {
            logger.info("La cita no contiene un ESTADO");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointmentStatus);
    }
}
