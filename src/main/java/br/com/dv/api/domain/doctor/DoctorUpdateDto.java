package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.AddressDto;
import jakarta.validation.constraints.NotNull;

public record DoctorUpdateDto(@NotNull
                              Long id,
                              String name,
                              String email,
                              AddressDto address) {
}
