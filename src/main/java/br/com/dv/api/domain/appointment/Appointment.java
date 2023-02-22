package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.doctor.Doctor;
import br.com.dv.api.domain.doctor.Specialty;
import br.com.dv.api.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
// Although the @EqualsAndHashCode annotation throws a warning, I'm keeping it for now for the sake of simplicity.
@EqualsAndHashCode(of = "id")
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

}
