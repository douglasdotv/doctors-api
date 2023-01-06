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

    public void updateData(AddressData address) {
        if (address.streetAddress() != null) {
            this.streetAddress = address.streetAddress();
        }
        if (address.city() != null) {
            this.city = address.city();
        }
        if (address.state() != null) {
            this.state = address.state();
        }
        if (address.zipCode() != null) {
            this.zipCode = address.zipCode();
        }
    }
}
