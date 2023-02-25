package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import br.com.dv.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatientSchedulingValidation implements AppointmentSchedulingValidation {

    private final PatientRepository patientRepository;

    @Autowired
    public ActivePatientSchedulingValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void validate(AppointmentSchedulingDto dto) {
        /*
        Appointments can only be scheduled by active patients.
         */

        if (dto.patientId() == null) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: patientId is null");
        }

        var patientIsActive = patientRepository.findIsActiveById(dto.patientId());
        if (patientIsActive != 1) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: patient is not active");
        }

    }

}
