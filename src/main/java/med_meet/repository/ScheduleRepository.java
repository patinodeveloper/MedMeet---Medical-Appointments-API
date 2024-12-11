package med_meet.repository;

import med_meet.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    /* Consultas Personalizadas */

    // Lista todos los horarios de un doctor
    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = :idDoctor")
    List<Schedule> findByDoctorId(@Param("idDoctor") Integer idDoctor);

    // Lista los horarios de un doctor en un dia especifico
    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = :idDoctor AND s.day = :day")
    List<Schedule> findByDoctorIdAndDay(@Param("idDoctor") Integer idDoctor, @Param("day") String day);

    // Lista todos los horarios de un día en específico
    @Query("SELECT s FROM Schedule s WHERE s.day = :day")
    List<Schedule> findSchedulesByDay(@Param("day") String day);
}
