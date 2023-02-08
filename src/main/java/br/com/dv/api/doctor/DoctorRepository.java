package br.com.dv.api.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Cannot resolve property 'isActive', but it is a valid property of the entity and the GET request works.
    // (I'm not sure if this is a bug or if I'm missing something.)
    Page<Doctor> findAllByIsActiveIsTrue(Pageable pageable);

}
