package com.example.musicandroid.Models;

import java.security.PrivateKey;
import java.util.ArrayList;

public class TrendingModels {

    private String imgSrc, songSrc;

    public TrendingModels() {
    }

    public TrendingModels(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public TrendingModels(String imgSrc, String songSrc) {
        this.imgSrc = imgSrc;
        this.songSrc = songSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getSongSrc() {
        return songSrc;
    }

    public void setSongSrc(String songSrc) {
        this.songSrc = songSrc;
    }
}
