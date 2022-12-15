package com.example.musicandroid.Models;

import android.widget.ImageView;

import com.example.musicandroid.R;

import java.util.ArrayList;
//liem code
public class ArtistModels {

    private String name;
    private String imgSrc;
    private ArrayList<String> listSong;

    public ArtistModels() {
    }

    public ArtistModels(String name, String imgSrc, ArrayList<String> listSong) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.listSong = listSong;
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

    public ArrayList<String> getListSong() {
        return listSong;
    }

    public void setListSong(ArrayList<String> listSong) {
        this.listSong = listSong;
    }
}
