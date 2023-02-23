package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import br.com.dv.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatientValidation {

    private final PatientRepository patientRepository;

    @Autowired
    public ActivePatientValidation(PatientRepository patientRepository) {
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
        if (!patientIsActive) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: patient is not active");
        }

    }

}
