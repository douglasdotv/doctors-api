package br.com.dv.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByIsActiveIsTrue(Pageable pageable);

    @Query(value = """
            SELECT * FROM doctors
            WHERE is_active = true
            AND specialty = :specialty
            AND id NOT IN (
                SELECT doctor_id FROM appointments
                WHERE scheduled_date_time = :scheduledDateTime
            )
            ORDER BY RAND()
            LIMIT 1;
            """, nativeQuery = true)
    Doctor chooseRandomDoctorBySpecialtyAndAvailability(Specialty specialty, LocalDateTime scheduledDateTime);

    @Query(value = """
            SELECT is_active FROM doctors
            WHERE id = :doctorId
            """, nativeQuery = true)
    Boolean findIsActiveById(Long doctorId);

}
