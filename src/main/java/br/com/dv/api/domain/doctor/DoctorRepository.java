package br.com.dv.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByIsActiveIsTrue(Pageable pageable);

    @Query("""
            SELECT d FROM Doctor d
            WHERE d.isActive = true
            AND d.specialty = :specialty
            AND d.id NOT IN (
                SELECT a.doctor.id FROM Appointment a
                WHERE a.scheduledDateTime = :scheduledDateTime
                )
            ORDER BY RAND()
            LIMIT 1
            """)
    Doctor chooseRandomDoctorBySpecialtyAndAvailability(Specialty specialty, LocalDateTime scheduledDateTime);

    @Query(value = """
            SELECT IF(is_active = 1, 1, 0) FROM doctors
            WHERE id = :doctorId
            """, nativeQuery = true)
    Integer findIsActiveById(Long doctorId);

}
