package com.example.class_projet_app;

public class Member {
    String id;
    String name;
    String  email;
    String password;
    String phone;

    public Member(){}
    public Member(String id,String name,String email, String password, String phone){
        this.id = id;
        this.name= name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getPhone(){return phone;}

    public String getId() {
        return id;
    }
}
