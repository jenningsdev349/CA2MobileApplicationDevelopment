package com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model;

import java.util.List;

public class Book {
    String title;
    String author;
    String category;
    String startDate;
    List<Review> reviews;
    boolean status;

    public Book(String title, String author, String category, List<Review> reviews, String startDate, boolean status) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.reviews = reviews;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
