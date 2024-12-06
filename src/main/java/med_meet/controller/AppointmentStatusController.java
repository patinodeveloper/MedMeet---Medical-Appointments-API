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
@CrossOrigin("http://localhost:5173")
public class AppointmentStatusController {

    private final Logger logger =
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
            logger.warn("Estatus de la cita con ID {} no encontrado.", idAppStatus);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointmentStatus);
    }

    @PostMapping
    public ResponseEntity<AppointmentStatus> postAppointmentStatus(@RequestBody AppointmentStatus appointmentStatus) {
        logger.info("Estatus a agregar: {}", appointmentStatus);
        AppointmentStatus savedAppointmentStatus = appointmentStatusService.saveAppointmentStatus(appointmentStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointmentStatus);
    }

    @PutMapping("/{idAppStatus}")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable Integer idAppStatus, @RequestBody AppointmentStatus receivedAppointmentStatus) {
        if (receivedAppointmentStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        AppointmentStatus appointmentStatus = appointmentStatusService.getAppointmentStatusById(idAppStatus);
        if (appointmentStatus == null) {
            logger.info("No se encontraron Estatus con el ID: {} en el sistema", idAppStatus);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointmentStatus.setStatus(receivedAppointmentStatus.getStatus());

        appointmentStatusService.saveAppointmentStatus(appointmentStatus);
        return ResponseEntity.ok(appointmentStatus);
    }

    @DeleteMapping("/{idAppStatus}")
    public ResponseEntity<?> deleteAppointmentStatus(@PathVariable Integer idAppStatus) {
        AppointmentStatus appointmentStatus = appointmentStatusService.getAppointmentStatusById(idAppStatus);
        if (appointmentStatus == null) {
            logger.warn("Estatus con ID {} no encontrado para eliminación.", idAppStatus);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointmentStatusService.deleteAppointmentStatus(appointmentStatus);
        logger.info("Estatus con ID {} eliminado exitosamente.", idAppStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointment/{idAppointment}")
    public ResponseEntity<AppointmentStatus> getAppStatusByAppointmentId(@PathVariable Integer idAppointment) {
        AppointmentStatus appointmentStatus = appointmentStatusService.findByAppointmentId(idAppointment);
        if (appointmentStatus == null) {
            logger.info("La cita no contiene un ESTADO");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        logger.info("Estatus: {}", appointmentStatus);
        return ResponseEntity.ok(appointmentStatus);
    }
}
