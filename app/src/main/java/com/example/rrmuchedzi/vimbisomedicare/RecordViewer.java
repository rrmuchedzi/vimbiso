package com.example.rrmuchedzi.vimbisomedicare;

import java.util.Date;

public class RecordViewer {

    private final int _ID;
    private String mMedicationDate;
    private String mMedicationScheduledTime;
    private String mMedicationTakenTime;

    public RecordViewer(int _ID, String medicationDate, String medicationScheduledTime, String medicationTakenTime) {
        this._ID = _ID;
        mMedicationDate = medicationDate;
        mMedicationScheduledTime = medicationScheduledTime;
        mMedicationTakenTime = medicationTakenTime;
    }

    public int get_ID() {
        return _ID;
    }

    public String getMedicationDate() {
        return mMedicationDate;
    }

    public String getMedicationTakenTime() {
        return mMedicationTakenTime;
    }
}
