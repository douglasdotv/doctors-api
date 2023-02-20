package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.doctor.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentSchedulingDto(Long doctorId,

                                       @NotNull
                                       Long patientId,

                                       @NotNull
                                       @Future
                                       LocalDateTime date,

                                       Specialty specialty) {
}
