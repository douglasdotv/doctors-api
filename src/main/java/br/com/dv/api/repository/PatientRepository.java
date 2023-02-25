package br.com.dv.api.repository;

import br.com.dv.api.domain.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findAllByIsActiveIsTrue(Pageable pageable);

    @Query("""
            SELECT CASE WHEN p.isActive = true THEN 1 ELSE 0 END FROM Patient p
            WHERE p.id = :patientId
            """)
    Integer findIsActiveById(Long patientId);

}
