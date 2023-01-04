package br.com.dv.api.controller;

import br.com.dv.api.doctor.DoctorRegistrationData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @PostMapping
    public void register(@RequestBody DoctorRegistrationData doctorRegistrationData) {
        System.out.println(doctorRegistrationData);
    }

}
