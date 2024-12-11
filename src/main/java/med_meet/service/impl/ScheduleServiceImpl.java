package med_meet.service.impl;

import med_meet.model.Schedule;
import med_meet.repository.ScheduleRepository;
import med_meet.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(Integer idSchedule) {
        return scheduleRepository.findById(idSchedule).orElse(null);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> findSchedulesByDoctorId(Integer idDoctor) {
        return scheduleRepository.findByDoctorId(idDoctor);
    }

    @Override
    public List<Schedule> findSchedulesByDoctorIdAndDay(Integer idDoctor, String day) {
        return scheduleRepository.findByDoctorIdAndDay(idDoctor, day);
    }

    @Override
    public List<Schedule> findSchedulesByDay(String day) {
        return scheduleRepository.findSchedulesByDay(day);
    }
}
