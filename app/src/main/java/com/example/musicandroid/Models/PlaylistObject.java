package com.example.musicandroid.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaylistObject implements Serializable {
    private String keyPlaylist;
    private String imgPath;
    private String namePlaylist;
    private ArrayList<String> keys_song;

    public PlaylistObject(){

    }


    public PlaylistObject(String keyPlaylist, String imgPath, String namePlaylist, ArrayList<String> keys_song) {
        this.keyPlaylist = keyPlaylist;
        this.imgPath = imgPath;
        this.namePlaylist = namePlaylist;
        this.keys_song = keys_song;
    }

    public String getKeyPlaylist() {
        return keyPlaylist;
    }

    public void setKeyPlaylist(String keyPlaylist) {
        this.keyPlaylist = keyPlaylist;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

    public ArrayList<String> getKeys_song() {
        return keys_song;
    }

    public void setKeys_song(ArrayList<String> keys_song) {
        this.keys_song = keys_song;
    }
}
