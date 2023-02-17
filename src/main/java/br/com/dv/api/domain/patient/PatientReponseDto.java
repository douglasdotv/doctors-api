package br.com.dv.api.domain.patient;

import br.com.dv.api.domain.address.Address;

public record PatientReponseDto(Long id,
                                String name,
                                String email,
                                String cpf,
                                Address address) {

    public PatientReponseDto(Patient patient) {
        this(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getCpf(),
                patient.getAddress()
        );
    }

}
