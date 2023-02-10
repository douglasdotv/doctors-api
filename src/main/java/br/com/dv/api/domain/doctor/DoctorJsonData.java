package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.Address;

public record DoctorJsonData(Long id,
                             String name,
                             String email,
                             String crm,
                             Specialty specialty,
                             Address address) {

    public DoctorJsonData(Doctor doctor) {
        this(doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getCrm(),
                doctor.getSpecialty(),
                doctor.getAddress()
        );
    }

}
