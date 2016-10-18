package com.example.owner.movieapp;

import java.io.Serializable;

/**
 * Created by Owner on 11-Oct-16.
 */

public class Movie implements Serializable {
    private int Image;
    private String Description;
    private String Title;
    private double Rate;

    public Movie(int image, String description, String title, double rate) {
        this.Image = image;
        this.Description = description;
        this.Title = title;
        this.Rate = rate;
    }

    public String getDescription() {
        return Description;
    }

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public double getRate() {
        return Rate;
    }
}
