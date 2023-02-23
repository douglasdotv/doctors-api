package br.com.dv.api.domain.appointment.validation;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;

public interface AppointmentValidation {

    void validate(AppointmentSchedulingDto dto);

}
