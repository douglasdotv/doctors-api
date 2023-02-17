package br.com.dv.api.domain.patient;

import br.com.dv.api.domain.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientRegistrationDto(@NotBlank(message = "{name.not.blank}")
                                     String name,

                                     @NotBlank(message = "{email.not.blank}")
                                     @Email(message = "{email.invalid}")
                                     String email,

                                     @NotBlank(message = "{cpf.not.blank}")
                                     @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "{cpf.invalid}")
                                     String cpf,

                                     @NotNull @Valid AddressDto address) {
}
