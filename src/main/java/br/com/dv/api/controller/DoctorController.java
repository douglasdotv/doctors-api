package br.com.dv.api.controller;

import br.com.dv.api.doctor.*;
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
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorJsonData> register(@RequestBody @Valid DoctorRegistrationData doctorRegistrationData,
                                                   UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(doctorRegistrationData);
        doctorRepository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorJsonData(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListingData>> listAll(@PageableDefault(size = 2, sort = {"name"})
                                                               Pageable pageable) {
        var page = doctorRepository
                .findAllByIsActiveIsTrue(pageable)
                .map(DoctorListingData::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorJsonData> update(@RequestBody @Valid DoctorUpdateData doctorUpdateData) {
        var doctor = doctorRepository.getReferenceById(doctorUpdateData.id());
        doctor.updateData(doctorUpdateData);

        return ResponseEntity.ok(new DoctorJsonData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        var doctor = doctorRepository.getReferenceById(id);
        doctor.softDelete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DoctorJsonData> list(@PathVariable Long id) {
        var doctor = doctorRepository.getReferenceById(id);

        return ResponseEntity.ok(new DoctorJsonData(doctor));
    }

}
