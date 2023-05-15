package com.example.rrmuchedzi.vimbisomedicare;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rufaro R.Muchedzi on 11/27/2017.
 */

class DuplicatedScheduled implements Serializable {

    private final int _ID;
    private String MedicName;
    private int MedicQuantity;
    private String MedicUnits;
    private Date StartDate;
    private Date EndDate;
    private int Duration;
    private String DurationUnits;
    private int MedicImageID;
    private String mNotes;
    private boolean isMedicStatus;
    private boolean takenStatus = false;
    private Date time;

    private static final String TAG = "MedicationScheduled";

    public DuplicatedScheduled(int _ID, Date timeA, String medicName, int medicQuantity, String medicUnits, Date startDate, Date endDate, int duration, String durationUnits, int medicImageID, String notes, boolean active) {
        this._ID = _ID;
        MedicName = medicName;
        MedicQuantity = medicQuantity;
        MedicUnits = medicUnits;
        StartDate = startDate;
        EndDate = endDate;
        Duration = duration;
        DurationUnits = durationUnits;
        MedicImageID = medicImageID;
        mNotes = notes;
        isMedicStatus = active;
        time = timeA;
    }

    public int get_ID() {
        return _ID;
    }

    public String getMedicName() {
        return MedicName;
    }

    public int getMedicQuantity() {
        return MedicQuantity;
    }

    public String getMedicUnits() {
        return MedicUnits;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public int getDuration() {
        return Duration;
    }

    public String getDurationUnits() {
        return DurationUnits;
    }

    public int getMedicImageID() {
        return MedicImageID;
    }

    public String getNotes() {
        return mNotes;
    }

    public boolean isMedicStatus() {
        return isMedicStatus;
    }

    public Date getTime() {
        return this.time;
    }

    public boolean isTakenStatus() {
        return takenStatus;
    }

    public void setTakenStatus(boolean takenStatus) {
        this.takenStatus = takenStatus;
    }

    @Override
    public String toString() {
        return "DuplicatedScheduled{" +
                "_ID=" + _ID +
                ", MedicName='" + MedicName + '\'' +
                ", MedicQuantity=" + MedicQuantity +
                ", MedicUnits='" + MedicUnits + '\'' +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", Duration=" + Duration +
                ", DurationUnits='" + DurationUnits + '\'' +
                ", MedicImageID=" + MedicImageID +
                ", mNotes='" + mNotes + '\'' +
                ", isMedicStatus=" + isMedicStatus +
                ", takenStatus=" + takenStatus +
                ", time=" + time +
                '}';
    }
}
