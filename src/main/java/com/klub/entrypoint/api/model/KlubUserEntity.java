package com.klub.entrypoint.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "klub_user")
public class KlubUserEntity extends UuidAsIdEntity {

    private String email;
    private String phoneNumber;
    private String password;

    public KlubUserEntity() {
        super();
    }

    public KlubUserEntity(String id, String email, String phoneNumber, String password) {
        super(id);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
