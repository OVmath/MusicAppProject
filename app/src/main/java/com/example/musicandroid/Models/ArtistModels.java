package com.example.musicandroid.Models;

import android.widget.ImageView;

import com.example.musicandroid.R;

public class ArtistModels {

    private String name;
    private String imgSrc;

    public ArtistModels() {
    }

    public ArtistModels(String name, String imgSrc) {
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
