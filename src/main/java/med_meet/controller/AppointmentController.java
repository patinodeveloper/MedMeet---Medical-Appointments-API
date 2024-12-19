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
@CrossOrigin("http://localhost:5173")
public class AppointmentController {

    private final Logger logger =
            LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            logger.info("No se encontraron citas en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{idAppointment}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer idAppointment) {
        Appointment appointment = appointmentService.getAppointmentById(idAppointment);
        if (appointment == null) {
            logger.warn("Cita con ID {} no encontrada.", idAppointment);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Cita encontrada: {}", appointment);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<Appointment> postAppointment(@RequestBody Appointment appointment) {
        logger.info("Cita a agregar: {}", appointment);
        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @PutMapping("/{idAppointment}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Integer idAppointment, @RequestBody Appointment receivedAppointment) {
        Appointment appointment = appointmentService.getAppointmentById(idAppointment);
        if (appointment == null) {
            logger.info("No se encontraron Citas con el ID: {} en el sistema", idAppointment);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointment.setPatient(receivedAppointment.getPatient());
        appointment.setDoctor(receivedAppointment.getDoctor());
        appointment.setDate(receivedAppointment.getDate());
        appointment.setStartTime(receivedAppointment.getStartTime());
        appointment.setEndTime(receivedAppointment.getEndTime());
        appointment.setStatus(receivedAppointment.getStatus());

        appointmentService.saveAppointment(appointment);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{idAppointment}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer idAppointment) {
        Appointment appointment = appointmentService.getAppointmentById(idAppointment);
        if (appointment == null) {
            logger.warn("Cita con ID {} no encontrada para eliminación.", idAppointment);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointmentService.deleteAppointment(appointment);
        logger.info("Cita con ID {} eliminada exitosamente.", idAppointment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cita eliminada con éxito.");
    }

    @GetMapping("/doctor/{idDoctor}/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorAndDate(
            @PathVariable Integer idDoctor,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByDoctorAndDate(idDoctor, date);
            if (appointments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        if (appointments.isEmpty()) {
            logger.info("No se encontraron citas con el Doctor ID: {}", idDoctor);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Integer idPatient) {
        List<Appointment> appointments = appointmentService.getAppsByPatientId(idPatient);
        if (appointments.isEmpty()) {
            logger.info("No se encontraron citas con el Paciente ID: {}", idPatient);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        appointments.forEach(appointment -> logger.info(appointment.toString()));
        return ResponseEntity.ok(appointments);
    }

}
