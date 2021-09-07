package com.crm.miniCRM.dto;

import com.crm.miniCRM.model.persistence.PersonAddressID;

public class PersonAddressDto {

    private PersonAddressID Id;

    private String email;
    private String phone;
    private String mobile;
    private char    type;
    private String preference;
    private boolean active;

    public PersonAddressDto(){}

    public PersonAddressDto(PersonAddressID Id, String email, String phone, String mobile, char type, String preference) {
        this.Id = Id;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.type = type;
        this.preference = preference;
        this.active = true;
    }

    public PersonAddressID getId() {
        return Id;
    }

    public void setId(PersonAddressID personAddressID) {
        this.Id = personAddressID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
