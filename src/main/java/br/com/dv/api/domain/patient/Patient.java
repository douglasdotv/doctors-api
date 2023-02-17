package br.com.dv.api.domain.patient;

import br.com.dv.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
// Although the @EqualsAndHashCode annotation throws a warning, I'm keeping it for now for the sake of simplicity.
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String cpf;

    @Embedded
    private Address address;

    private Boolean isActive;

    public Patient(PatientRegistrationData patientRegistrationData) {
        this.isActive = true;
        this.name = patientRegistrationData.name();
        this.email = patientRegistrationData.email();
        this.cpf = patientRegistrationData.cpf();
        this.address = new Address(patientRegistrationData.addressData());
    }

    public void updateData(PatientUpdateData patientUpdateData) {
        if (patientUpdateData.name() != null) {
            this.name = patientUpdateData.name();
        }
        if (patientUpdateData.address() != null) {
            this.address.updateData(patientUpdateData.address());
        }

    }

    public void softDelete() {
        this.isActive = false;
    }
}
