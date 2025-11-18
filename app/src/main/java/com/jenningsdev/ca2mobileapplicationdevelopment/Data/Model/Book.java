package com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model;

import java.util.List;

public class Book {
    int id;
    String title;
    String author;
    String category;
    String startDate;
    Review review;
    boolean status;

    public Book(int id, String title, String author, String category, String startDate, boolean status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.startDate = startDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
