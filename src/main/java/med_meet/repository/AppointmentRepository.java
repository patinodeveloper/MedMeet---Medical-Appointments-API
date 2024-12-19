package med_meet.repository;

import med_meet.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    /* Consultas personalizadas */

    // Consultar las citas de un doctor en una fecha especifica
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :idDoctor AND a.date = :date")
    List<Appointment> findAppointmentsByDoctorAndDate(@Param("idDoctor") Integer idDoctor, @Param("date") LocalDate date);

    // Listar todas las citas de un doctor
    List<Appointment> findByDoctorId(Integer idDoctor);

    // Listar todas las citas de un paciente
    List<Appointment> findByPatientId(Integer idPatient);
}
