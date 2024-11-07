package med_meet.repository;

import med_meet.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    /* Consultas Personalizadas */

    // Lista pacientes por nombre
    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByNameContainingIgnoreCase(@Param("name") String name);

    // Lista pacientes por citas con un doctor en especifico
    @Query("SELECT DISTINCT p FROM Patient p JOIN Appointment a ON p.id = a.patient.id WHERE a.doctor.id = :idDoctor")
    List<Patient> findByDoctorId(@Param("idDoctor") Integer idDoctor);

}
