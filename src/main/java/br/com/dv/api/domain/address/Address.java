package br.com.dv.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public Address(AddressDto addressDto) {
        this.streetAddress = addressDto.streetAddress();
        this.city = addressDto.city();
        this.state = addressDto.state();
        this.zipCode = addressDto.zipCode();
    }

    public void updateData(AddressDto dto) {
        if (dto.streetAddress() != null) {
            this.streetAddress = dto.streetAddress();
        }
        if (dto.city() != null) {
            this.city = dto.city();
        }
        if (dto.state() != null) {
            this.state = dto.state();
        }
        if (dto.zipCode() != null) {
            this.zipCode = dto.zipCode();
        }
    }
}
