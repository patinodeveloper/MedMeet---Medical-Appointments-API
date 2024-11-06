package med_meet.controller;

import med_meet.model.Schedule;
import med_meet.service.IScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medmeet/api/v1/schedules")
public class ScheduleController {

    private Logger logger =
            LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private IScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedule() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        schedules.forEach(schedule -> logger.info(schedule.toString()));
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{idSchedule}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer idSchedule) {
        Schedule schedule = scheduleService.getScheduleById(idSchedule);
        if (schedule == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<Schedule> postSchedule(@RequestBody Schedule schedule) {
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        scheduleService.saveSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

    @PutMapping("/{idSchedule}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Integer idSchedule, @RequestBody Schedule receivedSchedule) {
        if (receivedSchedule == null) {
            return ResponseEntity.badRequest().build();
        }
        Schedule schedule = scheduleService.getScheduleById(idSchedule);
        schedule.setDoctor(receivedSchedule.getDoctor());
        schedule.setDay(receivedSchedule.getDay());
        schedule.setStartTime(receivedSchedule.getStartTime());
        schedule.setEndTime(receivedSchedule.getEndTime());
        scheduleService.saveSchedule(schedule);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idSchedule}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer idSchedule) {
        if (idSchedule == null) {
            return ResponseEntity.notFound().build();
        }
        Schedule schedule = scheduleService.getScheduleById(idSchedule);
        scheduleService.deleteSchedule(schedule);
        return ResponseEntity.noContent().build();
    }
}
