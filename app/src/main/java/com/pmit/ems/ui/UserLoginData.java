package com.pmit.ems.ui;

public class UserLoginData {
    private String name;
    private String token;
    private String user_id;
    private String email;
    private String dept_name;
    private String user_code;
    private String user_image;
    private String phoneNumber;
    public UserLoginData(String name, String token, String user_id, String email, String dept_name,String user_code,String user_image,String phoneNumber){
        this.name=name;
        this.token=token;
        this.user_id=user_id;
        this.email=email;
        this.dept_name=dept_name;
        this.user_code=user_code;
        this.user_image=user_image;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
