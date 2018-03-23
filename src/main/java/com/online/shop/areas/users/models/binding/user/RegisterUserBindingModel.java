package com.online.shop.areas.users.models.binding.user;

import com.online.shop.areas.users.annotations.IsPasswordsMatching;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@IsPasswordsMatching
public class RegisterUserBindingModel {

    @NotNull(message = "Password cant be empty")
    @Size(min = 8, message = "Password is too short.")
    private String password;
    
    @NotNull(message = "First name cant be empty")
    @Size(min = 3, message = "First name is too short")
    private String firstName;

    @NotNull(message = "Last name cant be empty")
    @Size(min = 3, message = "Last name is too short")
    private String lastName;

    @NotNull(message = "Email cant be empty")
    @Size(min = 5, message = "Email is too short")
    private String email;

    @NotNull(message = "Phone number cant be empty")
    @Size(min = 5, message = "Phone number is too short")
    private String phoneNumber;

    @NotNull(message = "Confirm password cant be empty")
    private String confirmPassword;

    @NotNull(message = "City can't be empty.")
    private String city;

    private Integer postCode;

    @NotNull(message = "Street can't be empty.")
    private String street;

    @NotNull(message = "Address can't be empty.")
    private String adress;


    public RegisterUserBindingModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
