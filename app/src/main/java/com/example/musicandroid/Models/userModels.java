package com.example.musicandroid.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class userModels implements Serializable {

    private String TK, MK;
    private int id;
    private ArrayList<String> listArtist;

    public userModels() {
    }

    public userModels(String TK, String MK) {
        this.TK = TK;
        this.MK = MK;

    }
}
