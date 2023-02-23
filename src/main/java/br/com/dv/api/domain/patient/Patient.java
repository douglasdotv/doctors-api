package br.com.dv.api.domain.patient;

import br.com.dv.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public Patient(PatientRegistrationDto dto) {
        this.isActive = true;
        this.name = dto.name();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.address = new Address(dto.address());
    }

    public void updateData(PatientUpdateDto dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.address() != null) {
            this.address.updateData(dto.address());
        }

    }

    public void softDelete() {
        this.isActive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Patient patient = (Patient) o;
        return id != null && Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
