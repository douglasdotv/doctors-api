package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentRepository;
import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;

public class ClashingAppointmentsValidation {

    private AppointmentRepository repository;

    public void validate (AppointmentSchedulingDto dto) {
        /*
        Appointments cannot be scheduled if the doctor is already busy at the same time.
         */

        var isConflictingAppointment = repository.existsByDoctorIdAndScheduledDateTime(
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
