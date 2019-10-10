package com.pnctraining.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserLoginModel {

    @NotNull
    private String email;
    @NotNull
    @Min(8)
    private  String password;

    public UserLoginModel() {
    }

    public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
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
