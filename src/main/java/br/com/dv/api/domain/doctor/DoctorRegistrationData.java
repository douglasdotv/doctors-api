package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.AddressData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorRegistrationData(@NotBlank(message = "{name.not.blank}")
                                     String name,

                                     @NotBlank(message = "{email.not.blank}")
                                     @Email(message = "{email.invalid}")
                                     String email,

                                     @NotBlank(message = "{crm.not.blank}")
                                     @Pattern(regexp = "\\d{4,6}", message = "{crm.invalid}")
                                     String crm,

                                     @NotNull(message = "{specialty.not.null}")
                                     Specialty specialty,

                                     @NotNull(message = "{address.not.null}")
                                     @Valid
                                     AddressData addressData
) {
}
