package com.example.owner.movieapp;


public class Reviews {
    private String author_Name;
    private String Content;
    private int imgID;


    public Reviews(String author, String content) {
        this.author_Name = author;
        this.Content = content;
        this.imgID = R.drawable.ownerimg;
    }

    public int getImgID() {
        return imgID;
    }

    public String getContent() {
        return Content;
    }

    public String getAuthor_Name() {
        return author_Name;
    }
}
