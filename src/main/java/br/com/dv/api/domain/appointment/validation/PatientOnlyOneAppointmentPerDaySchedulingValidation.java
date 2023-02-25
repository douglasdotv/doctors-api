package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.repository.AppointmentRepository;
import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientOnlyOneAppointmentPerDaySchedulingValidation implements AppointmentSchedulingValidation {

    private static final int OPENING_HOUR = 7;
    private static final int CLOSING_HOUR = 19;
    private static final int APPOINTMENT_DURATION = 1;

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public PatientOnlyOneAppointmentPerDaySchedulingValidation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void validate(AppointmentSchedulingDto dto) {
        /*
        Patients can schedule only one appointment per day.
         */

        if (dto.patientId() == null) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: patientId is null");
        }

        var firstSlot = dto.scheduledDateTime().withHour(OPENING_HOUR);
        var lastSlot = dto.scheduledDateTime().withHour(CLOSING_HOUR - APPOINTMENT_DURATION);
        var patientHasAppointmentOnSameDay = appointmentRepository.existsByPatientIdAndScheduledDateTimeBetween(
                dto.patientId(),
                firstSlot,
                lastSlot
        );

        if (patientHasAppointmentOnSameDay) {
            throw new AppointmentValidationException(
                    """
                    Appointment cannot be scheduled:
                    patient already has an appointment on the same day.
                    """
            );
        }

    }

}
