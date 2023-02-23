package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentRepository;
import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClashingAppointmentsValidation {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public ClashingAppointmentsValidation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void validate (AppointmentSchedulingDto dto) {
        /*
        Appointments cannot be scheduled if the doctor is already busy at the same time.
         */

        var isConflictingAppointment = appointmentRepository.existsByDoctorIdAndScheduledDateTime(
                dto.doctorId(),
                dto.scheduledDateTime()
        );

        if (isConflictingAppointment) {
            throw new AppointmentValidationException(
                    """
                    Appointment cannot be scheduled:
                    doctor already has an appointment at the same time.
                    """
            );
        }

    }

}
