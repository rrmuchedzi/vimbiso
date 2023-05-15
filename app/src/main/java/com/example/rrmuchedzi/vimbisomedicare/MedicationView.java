package com.example.rrmuchedzi.vimbisomedicare;

public class MedicationView {

    private final int _ID;
    private String MedicName;
    private int MedicQuantity;
    private String MedicUnits;
    private String MedicTime;
    private String mNotes;
    private boolean MedicTakeStatus;
    private String MedicTakeDate;
    private int MedicPillID;

    public MedicationView(int _ID, String medicName, int medicQuantity, String medicUnits, int medicPillID, boolean medicTakeStatus, String medicTime, String medicTakeDate) {
        this._ID = _ID;
        MedicName = medicName;
        MedicQuantity = medicQuantity;
        MedicUnits = medicUnits;
        MedicTakeStatus = medicTakeStatus;
        MedicTakeDate = medicTakeDate;
        MedicTime = medicTime;
        MedicPillID = medicPillID;
    }

    public int get_ID() {
        return _ID;
    }

    public String getMedicName() {
        return MedicName;
    }

    public void setMedicName(String medicName) {
        MedicName = medicName;
    }

    public int getMedicQuantity() {
        return MedicQuantity;
    }

    public void setMedicQuantity(int medicQuantity) {
        MedicQuantity = medicQuantity;
    }

    public String getMedicUnits() {
        return MedicUnits;
    }

    public void setMedicUnits(String medicUnits) {
        MedicUnits = medicUnits;
    }

    public String getMedicTime() {
        return MedicTime;
    }

    public void setMedicTime(String medicTime) {
        MedicTime = medicTime;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public boolean getMedicTakeStatus() {
        return MedicTakeStatus;
    }

    public void setMedicTakeStatus(boolean medicTakeStatus) {
        MedicTakeStatus = medicTakeStatus;
    }

    public String getMedicTakeDate() {
        return MedicTakeDate;
    }

    public void setMedicTakeDate(String medicTakeDate) {
        MedicTakeDate = medicTakeDate;
    }

    public int getMedicPillID() {
        return MedicPillID;
    }

    @Override
    public String toString() {
        return "MedicationView{" +
                "_ID=" + _ID +
                ", MedicName='" + MedicName + '\'' +
                ", MedicQuantity=" + MedicQuantity +
                ", MedicUnits='" + MedicUnits + '\'' +
                ", MedicTime='" + MedicTime + '\'' +
                ", mNotes='" + mNotes + '\'' +
                ", MedicTakeStatus='" + MedicTakeStatus + '\'' +
                ", MedicTakeDate='" + MedicTakeDate + '\'' +
                '}';
    }
}
