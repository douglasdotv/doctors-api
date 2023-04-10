package br.com.dv.api.domain.appointment.validation.scheduling;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceNoticeSchedulingValidation implements SchedulingValidation {

    private static final int MINUTES_IN_ADVANCE = 30;

    public void validate(AppointmentSchedulingDto dto) {
        /*
        Appointments must be scheduled at least 30 minutes in advance.
         */

        var appointmentDateTime = dto.scheduledDateTime();
        var now = LocalDateTime.now();
        var diff = Duration.between(now, appointmentDateTime).toMinutes();

        if (diff < MINUTES_IN_ADVANCE) {
            throw new AppointmentValidationException("Appointments must be scheduled at least 30 minutes in advance.");
        }
    }

}
