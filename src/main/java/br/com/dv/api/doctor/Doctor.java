package br.com.dv.api.doctor;

import br.com.dv.api.address.Address;
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

    public Doctor(DoctorRegistrationData doctorRegistrationData) {
        this.name = doctorRegistrationData.name();
        this.email = doctorRegistrationData.email();
        this.crm = doctorRegistrationData.crm();
        this.specialty = doctorRegistrationData.specialty();
        this.address = new Address(doctorRegistrationData.addressData());
    }

    public void updateData(DoctorUpdateData doctorUpdateData) {
        if (doctorUpdateData.name() != null) {
            this.name = doctorUpdateData.name();
        }
        if (doctorUpdateData.email() != null) {
            this.email = doctorUpdateData.email();
        }
        if (doctorUpdateData.address() !=null) {
            this.address.updateData(doctorUpdateData.address());
        }
    }

}
