//liem code start
package com.example.musicandroid.Models;

import java.util.ArrayList;

public class songModel {


    private ArrayList<String> lyrics;
    private ArrayList<String> keys_playlist;
    private String imgSong;
    private int listens;
    private String keySong;
    private String nameSong;
    private String linkSong;
    private String duration;
    private String artist;

    public songModel() {
    }

    public songModel(ArrayList<String> lyrics, ArrayList<String> keys_playlist, String imgSong,
                     int listens, String keySong, String nameSong, String linkSong,
                     String duration, String artist) {
        this.lyrics = lyrics;
        this.keys_playlist = keys_playlist;
        this.imgSong = imgSong;
        this.listens = listens;
        this.keySong = keySong;
        this.nameSong = nameSong;
        this.linkSong = linkSong;
        this.duration = duration;
        this.artist = artist;
    }

    public ArrayList<String> getLyrics() {
        return lyrics;
    }

    public void setLyrics(ArrayList<String> lyrics) {
        this.lyrics = lyrics;
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
}
//liem end
