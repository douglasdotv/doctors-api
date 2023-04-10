package br.com.dv.api.domain.appointment.validation.cancellation;

import br.com.dv.api.domain.appointment.Appointment;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceNoticeCancellationValidation implements CancellationValidation {

    private static final int HOURS_IN_ADVANCE = 24;

    public void validate(Appointment appointment) {
        /*
        Appointments must be canceled at least 24 hours in advance.
         */

        var appointmentDateTime = appointment.getScheduledDateTime();
        var now = LocalDateTime.now();
        var diff = Duration.between(now, appointmentDateTime).toHours();

        if (diff < HOURS_IN_ADVANCE) {
            throw new AppointmentValidationException("Appointments must be canceled at least 24 hours in advance.");
        }
    }

}
