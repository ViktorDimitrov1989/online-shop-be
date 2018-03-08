package com.online.shop.model.binding.user;

public class LoginUserBindingModel {

    private String email;
    private String password;

    public LoginUserBindingModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
