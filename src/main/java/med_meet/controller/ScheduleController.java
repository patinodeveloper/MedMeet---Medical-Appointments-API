package med_meet.controller;

import med_meet.model.Schedule;
import med_meet.service.IScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/schedules")
@CrossOrigin("http://localhost:5173")
public class ScheduleController {

    private final Logger logger =
            LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private IScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedule() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        if (schedules.isEmpty()) {
            logger.info("No se encontraron horarios en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        schedules.forEach(schedule -> logger.info(schedule.toString()));
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{idSchedule}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer idSchedule) {
        Schedule schedule = scheduleService.getScheduleById(idSchedule);
        if (schedule == null) {
            logger.warn("Horario con ID {} no encontrado.", idSchedule);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<Schedule> postSchedule(@RequestBody Schedule schedule) {
        logger.info("Horario a agregar: {}", schedule);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    @PutMapping("/{idSchedule}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Integer idSchedule, @RequestBody Schedule receivedSchedule) {
        if (receivedSchedule == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Schedule schedule = scheduleService.getScheduleById(idSchedule);
        if (schedule == null) {
            logger.info("No se encontraron Horarios con el ID: {} en el sistema", idSchedule);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        schedule.setDoctor(receivedSchedule.getDoctor());
        schedule.setDay(receivedSchedule.getDay());
        schedule.setStartTime(receivedSchedule.getStartTime());
        schedule.setEndTime(receivedSchedule.getEndTime());

        scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{idSchedule}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer idSchedule) {
        Schedule schedule = scheduleService.getScheduleById(idSchedule);
        if (schedule == null) {
            logger.warn("Horario con ID {} no encontrado para eliminación.", idSchedule);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        scheduleService.deleteSchedule(schedule);
        logger.info("Horario con ID {} eliminado exitosamente.", idSchedule);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<Schedule>> getSchedulesByDoctorId(@PathVariable Integer idDoctor) {
        List<Schedule> schedules = scheduleService.findSchedulesByDoctorId(idDoctor);
        if (schedules.isEmpty()) {
            logger.info("No se encontraron horarios para el doctor con el ID: {}", idDoctor);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        schedules.forEach(schedule -> logger.info(schedule.toString()));
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/doctor/{idDoctor}/day/{day}")
    public ResponseEntity<List<Schedule>> getSchedulesByDoctorIdAndDay(
            @PathVariable Integer idDoctor, @PathVariable String day) {
        if (idDoctor == null || day == null || day.trim().isEmpty()) {
            logger.warn("Parámetros inválidos: idDoctor o day son NULL o vacíos.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Schedule> schedules = scheduleService.findSchedulesByDoctorIdAndDay(idDoctor, day);
        if (schedules.isEmpty()) {
            logger.info("No se encontraron horarios el día: {} para el doctor con el ID: {}", day, idDoctor);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        schedules.forEach(schedule -> logger.info(schedule.toString()));
        return ResponseEntity.ok(schedules);
    }
}
