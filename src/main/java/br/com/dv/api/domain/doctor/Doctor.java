package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
// Although the @EqualsAndHashCode annotation throws a warning, I'm keeping it for now for the sake of simplicity.
@EqualsAndHashCode(of = "id")
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

}
