package com.bikram.smart.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    private String city;

    private String street;

    private String province;

    private Long longitude;

    private Long latitude;

    private String extraField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if(addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", province='" + getProvince() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", extraField='" + getExtraField() + "'" +
            "}";
    }
}
