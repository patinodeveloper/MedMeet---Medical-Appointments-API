package med_meet.service;

import med_meet.model.Schedule;

import java.util.List;

public interface IScheduleService {
    List<Schedule> getAllSchedules();
    Schedule getScheduleById(Integer idSchedule);
    Schedule saveSchedule(Schedule schedule);
    void deleteSchedule(Schedule schedule);

    // Metodos personalizados
    List<Schedule> findSchedulesByDoctorId(Integer idDoctor);
    List<Schedule> findSchedulesByDoctorIdAndDay(Integer idDoctor, String day);
}
