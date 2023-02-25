package br.com.dv.api.controller;

import br.com.dv.api.domain.patient.*;
import br.com.dv.api.service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    private final PatientService service;

    @Autowired
    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PatientResponseDto> register(@RequestBody @Valid PatientRegistrationDto dto,
                                                       UriComponentsBuilder uriBuilder) {
        var patient = service.create(dto);
        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.id()).toUri();
        return ResponseEntity.created(uri).body(patient);
    }

    @GetMapping
    public ResponseEntity<Page<PatientListingDto>> listAll(@PageableDefault(size = 2, sort = {"name"})
                                                               Pageable pageable) {
        var page = service.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> list(@PathVariable Long id) {
        var patient = service.get(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientResponseDto> update(@RequestBody @Valid PatientUpdateDto dto) {
        var patient = service.update(dto);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
