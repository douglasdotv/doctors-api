package br.com.dv.api.doctor;

import br.com.dv.api.address.Address;

public record DoctorRegistrationData(String name,
                                     String email,
                                     String crm,
                                     Specialty specialty,
                                     Address address) {

}
