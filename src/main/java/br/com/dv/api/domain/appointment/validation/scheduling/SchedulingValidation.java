package br.com.dv.api.domain.appointment.validation.scheduling;

import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;

public interface SchedulingValidation {

    void validate(AppointmentSchedulingDto dto);

}
