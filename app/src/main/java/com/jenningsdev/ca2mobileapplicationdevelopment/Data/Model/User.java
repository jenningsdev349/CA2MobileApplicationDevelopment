package com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model;

import java.util.List;

public class User {
    String email;
    String name;
    String dob;

    List<Book> books;

    public User(String email, String dob, String name, List<Book> books) {
        this.email = email;
        this.dob = dob;
        this.name = name;
        this.books = books;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
