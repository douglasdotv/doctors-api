package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.doctor.Doctor;
import br.com.dv.api.domain.doctor.Specialty;
import br.com.dv.api.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime scheduledDateTime;

    @Enumerated
    private Specialty specialty;

    private boolean isActive = true;

    public Appointment(AppointmentSchedulingDto dto, Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
        this.scheduledDateTime = dto.scheduledDateTime();
        this.specialty = dto.specialty();
    }

    public void softDelete() {
        this.isActive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Appointment that = (Appointment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
