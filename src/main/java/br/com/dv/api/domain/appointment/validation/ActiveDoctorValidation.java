package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.doctor.DoctorRepository;

public class ActiveDoctorValidation {

    private DoctorRepository repository;

    public void validate(AppointmentSchedulingDto dto) {
        /*
        Appointments can only be scheduled with active doctors.
         */

        if (dto.doctorId() == null) {
            return;
        }

        var doctorIsActive = repository.findIsActiveById(dto.doctorId());
        if (!doctorIsActive) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: doctor is not active");
        }

    }

}
