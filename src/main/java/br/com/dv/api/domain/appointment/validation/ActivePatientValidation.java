package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.patient.PatientRepository;

public class ActivePatientValidation {

    private PatientRepository repository;

    public void validate(AppointmentSchedulingDto dto) {
        /*
        Appointments can only be scheduled by active patients.
         */

        if (dto.patientId() == null) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: patientId is null");
        }

        var patientIsActive = repository.findIsActiveById(dto.patientId());
        if (!patientIsActive) {
            throw new AppointmentValidationException("Appointment cannot be scheduled: patient is not active");
        }

    }

}
