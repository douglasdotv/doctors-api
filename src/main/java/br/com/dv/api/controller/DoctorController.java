package br.com.dv.api.controller;

import br.com.dv.api.domain.doctor.DoctorListingDto;
import br.com.dv.api.domain.doctor.DoctorRegistrationDto;
import br.com.dv.api.domain.doctor.DoctorResponseDto;
import br.com.dv.api.domain.doctor.DoctorUpdateDto;
import br.com.dv.api.service.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorResponseDto> register(@RequestBody @Valid DoctorRegistrationDto dto,
                                                      UriComponentsBuilder uriBuilder) {
        var createdDoctor = service.create(dto);
        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(createdDoctor.id()).toUri();
        return ResponseEntity.created(uri).body(createdDoctor);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListingDto>> listAll(@PageableDefault(size = 2, sort = {"name"})
                                                               Pageable pageable) {
        var page = service.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DoctorResponseDto> list(@PathVariable Long id) {
        var doctor = service.get(id);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorResponseDto> update(@RequestBody @Valid DoctorUpdateDto dto) {
        var doctor = service.update(dto);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
