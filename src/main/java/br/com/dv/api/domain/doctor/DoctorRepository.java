package br.com.dv.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Cannot resolve property 'isActive', but it is a valid property of the entity and the GET request works.
    Page<Doctor> findAllByIsActiveIsTrue(Pageable pageable);

    @Query("""
            SELECT d FROM Doctor d
                    WHERE d.isActive = true
                    AND d.specialty = :specialty
                    AND d.id NOT IN (
                        SELECT a.doctor.id FROM Appointment a
                        WHERE a.date = :date
                    )
                    ORDER BY FUNCTION('RAND')
            """)
    Doctor chooseRandomDoctorBySpecialtyAndAvailability(Specialty specialty, LocalDateTime date);

}
