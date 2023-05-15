package com.example.rrmuchedzi.vimbisomedicare;

import java.io.Serializable;

public class AppointmentObject implements Serializable {

    public static final long serialVersionUID = 20171213;

    private final int _ID;
    private String name;
    private String doctorName;
    private int doctorAvatar;
    private String date;
    private String time;
    private String reminderTime;
    private boolean reminderStatus;
    private String location;
    private String notes;

    public AppointmentObject(int _ID, String name, String doctorName, int doctorAvatar, String date, String time, String reminderTime, boolean reminderStatus, String location, String notes) {
        this._ID = _ID;
        this.name = name;
        this.doctorName = doctorName;
        this.doctorAvatar = doctorAvatar;
        this.date = date;
        this.time = time;
        this.reminderTime = reminderTime;
        this.reminderStatus = reminderStatus;
        this.location = location;
        this.notes = notes;
    }

    public int get_ID() {
        return _ID;
    }

    public String getName() {
        return name;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getDoctorAvatar() {
        return doctorAvatar;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public boolean isReminderStatus() {
        return reminderStatus;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "AppointmentObject{" +
                "_ID=" + _ID +
                ", name='" + name + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorAvatar=" + doctorAvatar +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", reminderTime='" + reminderTime + '\'' +
                ", reminderStatus=" + reminderStatus +
                ", location='" + location + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
