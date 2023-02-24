package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.Appointment;

public interface AppointmentCancellationValidation {

    void validate(Appointment appointment);

}
