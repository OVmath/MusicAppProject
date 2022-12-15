package com.example.musicandroid.Models;

import com.example.musicandroid.PlaylistObject;
import com.example.musicandroid.SongObject;

import java.io.Serializable;
import java.util.ArrayList;

public class userModels implements Serializable {
    //Liem code start
    private String UID, LinkAnh, GioiTinh;
    private ArrayList<String> listPlayList, listLikeSongs;
    private ArrayList<SongObject> listSong;

    public userModels() {
    }

    private userModels(String UID){
        this.UID = UID;
    }

    public userModels(String UID, String linkAnh, String gioiTinh, ArrayList<String> listPlayList, ArrayList<String> listLikeSongs, ArrayList<SongObject> listSong) {
        this.UID = UID;
        LinkAnh = linkAnh;
        GioiTinh = gioiTinh;
        this.listPlayList = listPlayList;
        this.listLikeSongs = listLikeSongs;
        this.listSong = listSong;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public ArrayList<String> getListPlayList() {
        return listPlayList;
    }

    public void setListPlayList(ArrayList<String> listPlayList) {
        this.listPlayList = listPlayList;
    }

    public ArrayList<String> getListLikeSongs() {
        return listLikeSongs;
    }

    public void setListLikeSongs(ArrayList<String> listLikeSongs) {
        this.listLikeSongs = listLikeSongs;
    }

    public ArrayList<SongObject> getListSong() {
        return listSong;
    }

    public void setListSong(ArrayList<SongObject> listSong) {
        this.listSong = listSong;
    }

    //end
}
