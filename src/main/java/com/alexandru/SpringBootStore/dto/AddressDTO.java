package com.alexandru.SpringBootStore.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddressDTO {


    private long addressId;

    @NotEmpty
    @Size(min = 4)
    private String address1;


    @Size(min = 4)
    private String address2;

    @NotEmpty
    @Size(min = 4)
    private String city;

    @NotEmpty
    @Size(min = 4)
    private String country;

    private String getAllAddress;


    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
