package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String crm;

    @Enumerated
    private Specialty specialty;

    @Embedded
    private Address address;

    private boolean isActive;

    public Doctor(DoctorRegistrationDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.crm = dto.crm();
        this.specialty = dto.specialty();
        this.address = new Address(dto.address());
        this.isActive = true;
    }

    public void updateData(DoctorUpdateDto dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.email() != null) {
            this.email = dto.email();
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
        Doctor doctor = (Doctor) o;
        return id != null && Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
