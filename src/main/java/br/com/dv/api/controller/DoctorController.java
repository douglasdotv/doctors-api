package br.com.dv.api.controller;

import br.com.dv.api.doctor.Doctor;
import br.com.dv.api.doctor.DoctorListingData;
import br.com.dv.api.doctor.DoctorRegistrationData;
import br.com.dv.api.doctor.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<DoctorListingData> list() {
        return doctorRepository
                .findAll()
                .stream()
                .map(DoctorListingData::new)
                .toList();
    }

}
