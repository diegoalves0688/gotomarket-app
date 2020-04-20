package com.example.GoToMarket;

public class User {
    private String id;
    private String name;
    private String password;
    private String email;
    private String address;
    private String payment_id;
    private String payment_key;

    public User(String name, String password, String email, String address, String payment_id, String payment_key) {
        this.setName(name);
        this.setPassword(password);
        this.setEmail(email);
        this.setAddress(address);
        this.setPayment_id(payment_id);
        this.setPayment_key(payment_key);
    }

    public User(String id, String name, String password, String email, String address, String payment_id, String payment_key) {
        this.setId(id);
        this.setName(name);
        this.setPassword(password);
        this.setEmail(email);
        this.setAddress(address);
        this.setPayment_id(payment_id);
        this.setPayment_key(payment_key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_key() {
        return payment_key;
    }

    public void setPayment_key(String payment_key) {
        this.payment_key = payment_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}