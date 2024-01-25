package com.pmit.ems.model;

public class UserLoginMach {
    private String userPhone;
    public UserLoginMach(String userPhone){
        this.userPhone=userPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
