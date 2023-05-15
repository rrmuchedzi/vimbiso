package com.example.rrmuchedzi.vimbisomedicare;

import java.io.Serializable;

/**
 * Created by Rufaro R.Muchedzi on 12/8/2017.
 */

public class DoctorObject implements Serializable {

    public static final long serialVersionUID = 20171209;

    private final int _ID;
    private String name;
    private byte Avatar;
    private String speciality;
    private String firstNumber;
    private String firstNumber_type;
    private String secondNumber;
    private String secondNumber_type;
    private String email;
    private String address;
    private String hospitalName;
    private String notes;

    public DoctorObject(int _ID, String name, byte avatar, String speciality, String firstNumber, String firstNumber_type, String secondNumber, String secondNumber_type, String email, String address, String hospitalName, String notes) {
        this._ID = _ID;
        this.name = name;
        Avatar = avatar;
        this.speciality = speciality;
        this.firstNumber = firstNumber;
        this.firstNumber_type = firstNumber_type;
        this.secondNumber = secondNumber;
        this.secondNumber_type = secondNumber_type;
        this.email = email;
        this.address = address;
        this.hospitalName = hospitalName;
        this.notes = notes;
    }

    public int get_ID() {
        return _ID;
    }

    public String getName() {
        return name;
    }

    public byte getAvatar() {
        return Avatar;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getFirstNumber() {
        return firstNumber;
    }

    public String getFirstNumber_type() {
        return firstNumber_type;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public String getSecondNumber_type() {
        return secondNumber_type;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "DoctorObject{" +
                "_ID=" + _ID +
                ", name='" + name + '\'' +
                ", Avatar=" + Avatar +
                ", speciality='" + speciality + '\'' +
                ", firstNumber='" + firstNumber + '\'' +
                ", firstNumber_type='" + firstNumber_type + '\'' +
                ", secondNumber='" + secondNumber + '\'' +
                ", secondNumber_type='" + secondNumber_type + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
