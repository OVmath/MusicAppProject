package com.example.musicandroid.Models;

import com.example.musicandroid.PlaylistObject;
import com.example.musicandroid.SongObject;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {
    //Liem code start
    private String UID, LinkAnh, GioiTinh, TenHT;
    private ArrayList<PlaylistObject> listPlayList = new ArrayList<>();
    private ArrayList<String> listLikeSongs;
    private ArrayList<SongObject> listSong;

    public UserModel() {
        this.TenHT = "";
        this.LinkAnh = "";
        this.listSong = new ArrayList<>();
    }

    public UserModel(String UID){
        this.UID = UID;
        LinkAnh = "";
        GioiTinh = "";
        TenHT = "";
        this.listPlayList = new ArrayList<>();
        this.listLikeSongs = new ArrayList<>();
        this.listSong = new ArrayList<>();
    }

    public UserModel(String linkAnh, String gioiTinh, String tenHT) {
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

    public ArrayList<PlaylistObject> getListPlayList() {
        return listPlayList;
    }

    public void setListPlayList(ArrayList<PlaylistObject> listPlayList) {
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
        if (TenHT.equals("")){
            return "";
        }
        return TenHT + "";
    }

    public void setTenHT(String tenHT) {
        TenHT = tenHT;
    }

    //end
}
