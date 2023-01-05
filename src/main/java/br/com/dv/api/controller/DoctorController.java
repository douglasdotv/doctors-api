package br.com.dv.api.controller;

import br.com.dv.api.doctor.Doctor;
import br.com.dv.api.doctor.DoctorRegistrationData;
import br.com.dv.api.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void register(@RequestBody DoctorRegistrationData doctorRegistrationData) {
        doctorRepository.save(new Doctor(doctorRegistrationData));
    }

}
