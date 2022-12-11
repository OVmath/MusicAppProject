package com.example.musicandroid.Models;

import android.widget.ImageView;

import com.example.musicandroid.R;

public class ArtistModels {

    private String name;
    //private ImageView img;

    public ArtistModels() {
    }

    public ArtistModels(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
*/
}
