package br.com.dv.api.doctor;

import br.com.dv.api.address.AddressData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorRegistrationData(@NotBlank
                                     String name,
                                     @NotBlank
                                     @Email
                                     String email,
                                     @NotBlank
                                     @Pattern(regexp = "\\d{4,6}")
                                     String crm,
                                     @NotNull
                                     Specialty specialty,
                                     @NotNull
                                     @Valid
                                     AddressData addressData) {
}
