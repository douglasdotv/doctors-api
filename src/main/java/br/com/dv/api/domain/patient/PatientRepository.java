package br.com.dv.api.domain.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Cannot resolve property 'isActive', but it is a valid property of the entity and the GET request works.
    // (I'm not sure if this is a bug or if I'm missing something.)
    Page<Patient> findAllByIsActiveIsTrue(Pageable pageable);

}
