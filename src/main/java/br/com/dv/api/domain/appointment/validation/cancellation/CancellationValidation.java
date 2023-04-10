package br.com.dv.api.domain.appointment.validation.cancellation;

import br.com.dv.api.domain.appointment.Appointment;

public interface CancellationValidation {

    void validate(Appointment appointment);

}
