package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.AddressData;
import jakarta.validation.constraints.NotNull;

public record DoctorUpdateData(@NotNull
                               Long id,
                               String name,
                               String email,
                               AddressData address) {
}
