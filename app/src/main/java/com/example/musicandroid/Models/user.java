package com.example.musicandroid.Models;

import java.io.Serializable;

public class user implements Serializable {

    private String TK, MK;
    private int id;

    public user() {
    }

    public user(String TK, String MK) {
        this.TK = TK;
        this.MK = MK;

    }
}
