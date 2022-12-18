package com.example.musicandroid;

import java.io.Serializable;
import java.util.ArrayList;

public class SongObject implements Serializable {
    private String keySong;
    private String nameSong;
    private String linkSong;
    private String duration;
    private String artist;
    private Boolean liked = false;
    private ArrayList<String> lyrics;
    private ArrayList<String> keys_playlist;
    private String imgSong;
    private int listens;
    SongObject(){

    }

    public SongObject(String nameSong, String linkSong, String duration) {
        this.nameSong = nameSong;
        this.linkSong = linkSong;
        this.duration = duration;
    }


    public String getKeySong() {
        return keySong;
    }

    public void setKeySong(String keySong) {
        this.keySong = keySong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public ArrayList<String> getKeys_playlist() {
        return keys_playlist;
    }

    public void setKeys_playlist(ArrayList<String> keys_playlist) {
        this.keys_playlist = keys_playlist;
    }

    public String getImgSong() {
        return imgSong;
    }

    public void setImgSong(String imgSong) {
        this.imgSong = imgSong;
    }

    public int getListens() {
        return listens;
    }

    public void setListens(int listens) {
        this.listens = listens;
    }

    public ArrayList<String> getLyrics() {
        return lyrics;
    }

    public void setLyrics(ArrayList<String> lyrics) {
        this.lyrics = lyrics;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
