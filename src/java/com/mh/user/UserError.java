/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.user;

/**
 *
 * @author saost
 */
public class UserError {
    private String userIdError;
    private String passwordError;
    private String fullNameError;
    private String passwordRepeatError;
    private String addressError;
    private String phoneNumberError;

    public UserError(String userIdError, String passwordError, String fullNameError, String passwordRepeatError, String addressError, String phoneNumberError) {
        this.userIdError = userIdError;
        this.passwordError = passwordError;
        this.fullNameError = fullNameError;
        this.passwordRepeatError = passwordRepeatError;
        this.addressError = addressError;
        this.phoneNumberError = phoneNumberError;
    }

    public UserError(String userIdError, String passwordError, String fullNameError, String passwordRepeatError) {
        this.userIdError = userIdError;
        this.passwordError = passwordError;
        this.fullNameError = fullNameError;
        this.passwordRepeatError = passwordRepeatError;
    }

    public UserError() {

    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPhoneNumberError() {
        return phoneNumberError;
    }

    public void setPhoneNumberError(String phoneNumberError) {
        this.phoneNumberError = phoneNumberError;
    }

    public String getUserIdError() {
        return userIdError;
    }

    public void setUserIdError(String userIdError) {
        this.userIdError = userIdError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getFullNameError() {
        return fullNameError;
    }

    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    public String getPasswordRepeatError() {
        return passwordRepeatError;
    }

    public void setPasswordRepeatError(String passwordRepeatError) {
        this.passwordRepeatError = passwordRepeatError;
    }
}
