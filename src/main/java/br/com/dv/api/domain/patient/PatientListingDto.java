package br.com.dv.api.domain.patient;

public record PatientListingDto(Long id,
                                String name,
                                String email,
                                String cpf) {

    public PatientListingDto(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }

}
