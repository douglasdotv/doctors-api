package br.com.dv.api.service;

import br.com.dv.api.domain.doctor.*;
import br.com.dv.api.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    @Autowired
    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public DoctorResponseDto create(DoctorRegistrationDto dto) {
        var doctor = new Doctor(dto);
        repository.save(doctor);
        return new DoctorResponseDto(doctor);
    }

    public Page<DoctorListingDto> getAll(Pageable pageable) {
        return repository
                .findAllByIsActiveIsTrue(pageable)
                .map(DoctorListingDto::new);
    }

    public DoctorResponseDto get(Long id) {
        var doctor = repository.getReferenceById(id);
        return new DoctorResponseDto(doctor);
    }

    public DoctorResponseDto update(DoctorUpdateDto dto) {
        var doctor = repository.getReferenceById(dto.id());
        doctor.updateData(dto);
        return new DoctorResponseDto(doctor);
    }

    public void delete(Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.softDelete();
    }

}
