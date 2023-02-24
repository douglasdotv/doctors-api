package br.com.dv.api.controller;

import br.com.dv.api.domain.doctor.*;
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
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    private final DoctorRepository repository;

    @Autowired
    public DoctorController(DoctorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorResponseDto> register(@RequestBody @Valid DoctorRegistrationDto dto,
                                                      UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(dto);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorResponseDto(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListingDto>> listAll(@PageableDefault(size = 2, sort = {"name"})
                                                               Pageable pageable) {
        var page = repository
                .findAllByIsActiveIsTrue(pageable)
                .map(DoctorListingDto::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DoctorResponseDto> list(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);

        return ResponseEntity.ok(new DoctorResponseDto(doctor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorResponseDto> update(@RequestBody @Valid DoctorUpdateDto dto) {
        var doctor = repository.getReferenceById(dto.id());
        doctor.updateData(dto);

        return ResponseEntity.ok(new DoctorResponseDto(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.softDelete();

        return ResponseEntity.noContent().build();
    }

}
