//liem code
package com.example.musicandroid.Models;

public class lastestReleaseModels {

    private String name, imgSrc, songSrc;

    public lastestReleaseModels() {
    }

    public lastestReleaseModels(String name, String imgSrc, String songSrc) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.songSrc = songSrc;
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

    public String getSongSrc() {
        return songSrc;
    }

    public void setSongSrc(String songSrc) {
        this.songSrc = songSrc;
    }
}
