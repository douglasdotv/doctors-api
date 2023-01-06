package br.com.dv.api.doctor;

import br.com.dv.api.address.AddressData;
import jakarta.validation.constraints.NotNull;

public record DoctorUpdateData(@NotNull
                               Long id,
                               String name,
                               String email,
                               AddressData address) {
}
