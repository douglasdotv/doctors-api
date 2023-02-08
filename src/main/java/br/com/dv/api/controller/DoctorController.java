package br.com.dv.api.controller;

import br.com.dv.api.doctor.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public void register(@RequestBody @Valid DoctorRegistrationData doctorRegistrationData) {
        doctorRepository.save(new Doctor(doctorRegistrationData));
    }

    @GetMapping
    public Page<DoctorListingData> list(@PageableDefault(size = 2, sort = {"name"}) Pageable pageable) {
        return doctorRepository
                .findAll(pageable)
                .map(DoctorListingData::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DoctorUpdateData doctorUpdateData) {
        var doctor = doctorRepository.getReferenceById(doctorUpdateData.id());
        doctor.updateData(doctorUpdateData);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void remove(@PathVariable Long id) {
        var doctor = doctorRepository.getReferenceById(id);
        doctor.softDelete();
    }

}
