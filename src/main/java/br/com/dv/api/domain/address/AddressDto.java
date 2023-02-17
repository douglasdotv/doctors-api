package br.com.dv.api.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDto(
        @NotBlank(message = "{streetaddress.not.blank}")
        String streetAddress,

        @NotBlank(message = "{city.not.blank}")
        String city,

        @NotBlank(message = "{state.not.blank}")
        String state,

        @NotBlank(message = "{zipcode.not.blank}")
        @Pattern(regexp = "\\d{8}", message = "{zipcode.invalid}")
        String zipCode) {
}
