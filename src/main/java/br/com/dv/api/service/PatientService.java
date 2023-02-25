package br.com.dv.api.service;

import br.com.dv.api.domain.patient.*;
import br.com.dv.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public PatientResponseDto create(PatientRegistrationDto dto) {
        var patient = new Patient(dto);
        repository.save(patient);
        return new PatientResponseDto(patient);
    }

    public Page<PatientListingDto> getAll(Pageable pageable) {
        return repository
                .findAllByIsActiveIsTrue(pageable)
                .map(PatientListingDto::new);
    }

    public PatientResponseDto get(Long id) {
        var patient = repository.getReferenceById(id);
        return new PatientResponseDto(patient);
    }

    public PatientResponseDto update(PatientUpdateDto dto) {
        var patient = repository.getReferenceById(dto.id());
        patient.updateData(dto);
        return new PatientResponseDto(patient);
    }

    public void delete(Long id) {
        var patient = repository.getReferenceById(id);
        patient.softDelete();
    }

}
