package br.com.dv.api.address;

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

    public Address(AddressData addressData) {
        this.streetAddress = addressData.streetAddress();
        this.city = addressData.city();
        this.state = addressData.state();
        this.zipCode = addressData.zipCode();
    }

}
