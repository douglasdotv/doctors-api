package br.com.dv.api.domain.patient;

import br.com.dv.api.domain.address.AddressDto;
import jakarta.validation.constraints.NotNull;

public record PatientUpdateDto(@NotNull
                               Long id,
                               String name,
                               String email,
                               AddressDto address) {
}
