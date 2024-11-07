package med_meet.repository;

import med_meet.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentStatusRepository extends JpaRepository<AppointmentStatus, Integer> {
    /* Consultas Personalizadas*/

    // Obtener estado de la cita por id de la cita
    @Query("SELECT a.status FROM Appointment a WHERE a.id = :idAppointment")
    AppointmentStatus findByAppointmentId(@Param("idAppointment") Integer idAppointment);

}
