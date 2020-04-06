package com.bikram.smart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "landline_number")
    private String landlineNumber;

    @Column(name = "alternate_number")
    private String alternateNumber;

    @Column(name = "extra_field")
    private String extraField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Contact mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public Contact landlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
        return this;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public Contact alternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
        return this;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getExtraField() {
        return extraField;
    }

    public Contact extraField(String extraField) {
        this.extraField = extraField;
        return this;
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
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", landlineNumber='" + getLandlineNumber() + "'" +
            ", alternateNumber='" + getAlternateNumber() + "'" +
            ", extraField='" + getExtraField() + "'" +
            "}";
    }
}
