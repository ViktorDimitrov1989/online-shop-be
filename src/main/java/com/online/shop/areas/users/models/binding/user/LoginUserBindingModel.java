package com.online.shop.areas.users.models.binding.user;

public class LoginUserBindingModel {

    private String email;
    private String password;
    private Boolean rememberMe;

    public LoginUserBindingModel() {
    }

    public LoginUserBindingModel(String email, String password, Boolean rememberMe){
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
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

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
