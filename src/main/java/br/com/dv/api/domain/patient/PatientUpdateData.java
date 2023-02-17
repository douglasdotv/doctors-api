package br.com.dv.api.domain.patient;

import br.com.dv.api.domain.address.AddressData;
import jakarta.validation.constraints.NotNull;

public record PatientUpdateData(@NotNull
                                Long id,
                                String name,
                                String email,
                                AddressData address) {
}
