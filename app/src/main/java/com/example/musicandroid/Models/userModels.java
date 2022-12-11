package com.example.musicandroid.Models;

import java.io.Serializable;

public class userModels implements Serializable {

    private String TK, MK;
    private int id;

    public userModels() {
    }

    public userModels(String TK, String MK) {
        this.TK = TK;
        this.MK = MK;

    }
}
