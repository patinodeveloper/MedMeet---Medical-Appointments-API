package med_meet.repository;

import med_meet.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    /* Consultas personalizadas */

    // Consultar doctores por especialidad
    @Query("SELECT d FROM Doctor d WHERE d.specialty.id = :idSpecialty")
    List<Doctor> findDoctorsBySpecialty(@Param("idSpecialty") Integer idSpecialty);

}
