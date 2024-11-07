package med_meet.controller;

import med_meet.model.Appointment;
import med_meet.service.IAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/appointments")
public class AppointmentController {

    private Logger logger =
            LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{idAppointment}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer idAppointment) {
        if (idAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        Appointment appointment = appointmentService.getAppointmentById(idAppointment);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<Appointment> postAppointment(@RequestBody Appointment appointment) {
        if (appointment == null) {
            return ResponseEntity.badRequest().build();
        }
        appointmentService.saveAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PutMapping("/{idAppointment}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Integer idAppointment, @RequestBody Appointment receivedAppointment) {
        if (idAppointment == null || receivedAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        Appointment appointment = appointmentService.getAppointmentById(idAppointment);
        appointment.setPatient(receivedAppointment.getPatient());
        appointment.setDoctor(receivedAppointment.getDoctor());
        appointment.setDateTime(receivedAppointment.getDateTime());
        appointment.setStatus(receivedAppointment.getStatus());
        appointmentService.saveAppointment(appointment);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAppointment}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer idAppointment) {
        if (idAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        Appointment appointment = appointmentService.getAppointmentById(idAppointment);
        appointmentService.deleteAppointment(appointment);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{idDoctor}/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorAndDate(
            @PathVariable Integer idDoctor,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByDoctorAndDate(idDoctor, date);
            if (appointments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            // Captura cualquier excepción y registra el error
            logger.error("Error al obtener citas para el doctor con ID {} en la fecha {}", idDoctor, date, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable Integer idDoctor) {
        List<Appointment> appointments = appointmentService.getAppsByDoctorId(idDoctor);
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        if (appointments.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Integer idPatient) {
        List<Appointment> appointments = appointmentService.getAppsByPatientId(idPatient);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/status/{idStatus}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatusId(@PathVariable Integer idStatus) {
        List<Appointment> appointments = appointmentService.getAppsByStatusId(idStatus);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        return ResponseEntity.ok(appointments);
    }
}
