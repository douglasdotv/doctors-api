package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record AppointmentListingDto(Long doctorId,
                                    Long patientId,
                                    LocalDateTime scheduledDateTime,
                                    Specialty specialty) {

    public AppointmentListingDto(Appointment appointment) {
        this(
                appointment.getDoctor().getId(),
                appointment.getPatient().getId(),
                appointment.getScheduledDateTime(),
                appointment.getDoctor().getSpecialty()
        );
    }

}
