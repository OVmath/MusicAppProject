package com.example.musicandroid.Models;

import com.example.musicandroid.PlaylistObject;
import com.example.musicandroid.SongObject;

import java.io.Serializable;
import java.util.ArrayList;

public class userModels implements Serializable {
    //Liem code start
    private String UID, LinkAnh, GioiTinh;
    private ArrayList<String> listPlayList, listLikeSongs, listLocalSongs;

    public userModels() {
    }

    private userModels(String UID){
        this.UID = UID;
    }

    public userModels(String UID, String linkAnh, ArrayList<String> listArtist) {
        this.UID = UID;
        this.LinkAnh = linkAnh;
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


    //end
}
