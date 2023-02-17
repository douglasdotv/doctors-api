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

    private final PatientRepository patientRepository;

    @Autowired
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PatientJsonData> cadastrar(@RequestBody @Valid PatientRegistrationData patientRegistrationData,
                                                     UriComponentsBuilder uriBuilder) {
        var patient = new Patient(patientRegistrationData);
        patientRepository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
        return ResponseEntity.created(uri).body(new PatientJsonData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientListingData>> listar(@PageableDefault(size = 2, sort = {"name"})
                                                               Pageable pagination) {
        var page = patientRepository
                .findAllByIsActiveIsTrue(pagination)
                .map(PatientListingData::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientJsonData> atualizar(@RequestBody @Valid PatientUpdateData patientUpdateData) {
        var patient = patientRepository.getReferenceById(patientUpdateData.id());
        patient.updateData(patientUpdateData);

        return ResponseEntity.ok(new PatientJsonData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var patient = patientRepository.getReferenceById(id);
        patient.softDelete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientJsonData> detalhar(@PathVariable Long id) {
        var patient = patientRepository.getReferenceById(id);
        return ResponseEntity.ok(new PatientJsonData(patient));
    }


}
