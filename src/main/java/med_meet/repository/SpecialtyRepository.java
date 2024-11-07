package med_meet.repository;

import med_meet.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    /* Consultas personalizadas */

    // Listar especialidades medicas por nombre
    @Query("SELECT s FROM Specialty s WHERE s.name = :name")
    Specialty findSpecialtyByName(String name);

    // Contar los doctores por especialidad
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.specialty.id = :idSpecialty")
    Integer countDoctorsInSpecialty(Integer idSpecialty);

}
