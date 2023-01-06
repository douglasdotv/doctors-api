package br.com.dv.api.controller;

import br.com.dv.api.doctor.Doctor;
import br.com.dv.api.doctor.DoctorListingData;
import br.com.dv.api.doctor.DoctorRegistrationData;
import br.com.dv.api.doctor.DoctorRepository;
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

}
