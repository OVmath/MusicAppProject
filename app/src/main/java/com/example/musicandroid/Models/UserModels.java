package com.example.musicandroid.Models;

import com.example.musicandroid.SongObject;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModels implements Serializable {
    //Liem code start
    private String UID, LinkAnh, GioiTinh, TenHT;
    private ArrayList<String> listPlayList, listLikeSongs;
    private ArrayList<SongObject> listSong;

    public UserModels() {
    }

    public UserModels(String UID){
        this.UID = UID;
        LinkAnh = "";
        GioiTinh = "";
        TenHT = "";
        this.listPlayList = new ArrayList<>();
        this.listLikeSongs = new ArrayList<>();
        this.listSong = new ArrayList<>();
    }

    public UserModels(String linkAnh, String gioiTinh, String tenHT) {
        LinkAnh = linkAnh;
        GioiTinh = gioiTinh;
        TenHT = tenHT;
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

    public String getTenHT() {
        return TenHT;
    }

    public void setTenHT(String tenHT) {
        TenHT = tenHT;
    }

    //end
}
