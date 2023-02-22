package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record AppointmentResponseDto(Long id,
                                     Long doctorId,
                                     Long patientId,
                                     LocalDateTime scheduledDateTime,
                                     Specialty specialty) {

}
