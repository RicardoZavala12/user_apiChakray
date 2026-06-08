package com.chakrayFZRD.users_api.model;

import java.util.List;

public class User {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String taxId;
    private String createdAt;
    private List<Address> addres;
    
    // mi construcctor vacio
    public User() {
        
    }
    
    // contrucctor con tpodos los campos
    public User(String id, String name, String email, String phone, String password, String taxId, String createdAt,
            List<Address> addres) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.taxId = taxId;
        this.createdAt = createdAt;
        this.addres = addres;
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Address> getAddres() {
        return addres;
    }

    public void setAddres(List<Address> addres) {
        this.addres = addres;
    }

    
}
