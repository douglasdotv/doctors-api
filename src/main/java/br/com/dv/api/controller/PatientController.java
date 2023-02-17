package br.com.dv.api.controller;

import br.com.dv.api.domain.patient.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository repository;

    @Autowired
    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PatientReponseDto> register(@RequestBody @Valid PatientRegistrationDto dto,
                                                      UriComponentsBuilder uriBuilder) {
        var patient = new Patient(dto);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
        return ResponseEntity.created(uri).body(new PatientReponseDto(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientListingDto>> listAll(@PageableDefault(size = 2, sort = {"name"})
                                                               Pageable pagination) {
        var page = repository
                .findAllByIsActiveIsTrue(pagination)
                .map(PatientListingDto::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientReponseDto> list(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientReponseDto(patient));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientReponseDto> update(@RequestBody @Valid PatientUpdateDto dto) {
        var patient = repository.getReferenceById(dto.id());
        patient.updateData(dto);

        return ResponseEntity.ok(new PatientReponseDto(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.softDelete();

        return ResponseEntity.noContent().build();
    }

}
