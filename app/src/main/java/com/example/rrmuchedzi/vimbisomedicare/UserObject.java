package com.example.rrmuchedzi.vimbisomedicare;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by Rufaro R.Muchedzi on 12/11/2017.
 */

public class UserObject implements Serializable {

    public static final long serialVersionUID = 20171212;

    private final int _ID;
    private final String NAME;
    private final String SURNAME;
    private final int AVATAR_ID;
    private final String EMAIL;
    private final Integer SECURITY;

    public UserObject(int _ID, String NAME, String SURNAME, int AVATAR_ID, String EMAIL, Integer SECURITY) {
        this._ID = _ID;
        this.NAME = NAME;
        this.SURNAME = SURNAME;
        this.AVATAR_ID = AVATAR_ID;
        this.EMAIL = EMAIL;
        this.SECURITY = SECURITY;
    }

    public int get_ID() {
        return _ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public Integer getAVATAR_ID() {
        return AVATAR_ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public int getSECURITY() {
        return SECURITY;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "_ID=" + _ID +
                ", NAME='" + NAME + '\'' +
                ", SURNAME='" + SURNAME + '\'' +
                ", AVATAR_ID=" + AVATAR_ID +
                ", EMAIL='" + EMAIL + '\'' +
                ", SECURITY=" + SECURITY +
                '}';
    }
}
