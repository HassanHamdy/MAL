package com.example.owner.movieapp;

import java.io.Serializable;


public class Movie implements Serializable {
    private String Image;
    private String BackgroundImage;
    private String Description;
    private String Title;
    private double Rate;
    private String RDate;
    private int ID;

    public Movie(String image, String description, String title, double rate, String date, String backgroundImage, int id) {
        this.Image = image;
        this.Description = description;
        this.Title = title;
        this.Rate = rate;
        this.RDate = date;
        this.BackgroundImage = backgroundImage;
        this.ID = id;
    }

    public Movie(int id, String title, String image, String date, double rate) {
        this.Image = image;
        this.Title = title;
        this.Rate = rate;
        this.RDate = date;
        this.ID = id;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public double getRate() {
        return Rate;
    }

    public String getRDate() {
        return RDate;
    }

    public int getID() {
        return ID;
    }

    public String getBackgroundImage() {
        return BackgroundImage;
    }
}
