package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class OpeningHoursValidation implements AppointmentValidation {

    private static final int OPENING_HOUR = 7;
    private static final int CLOSING_HOUR = 19;
    private static final int APPOINTMENT_DURATION = 1;

    public void validate(AppointmentSchedulingDto dto) {
        /*
        - Can't schedule appointments on Sundays
        - The opening hours are from 7am to 7pm
        - The appointment duration is fixed to 1 hour
         */

        var appointmentDateTime = dto.scheduledDateTime();
        var appointmentDayOfWeek = appointmentDateTime.getDayOfWeek();

        var isSunday = appointmentDayOfWeek == DayOfWeek.SUNDAY;
        var isbeforeOpeningHours = appointmentDateTime.getHour() < OPENING_HOUR;
        var isAfterClosingHours = appointmentDateTime.getHour() > CLOSING_HOUR - APPOINTMENT_DURATION;

        if (isSunday || isbeforeOpeningHours || isAfterClosingHours) {
            throw new AppointmentValidationException("Appointment cannot be scheduled outside opening hours");
        }

    }
}
