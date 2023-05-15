package com.example.rrmuchedzi.vimbisomedicare;

import java.util.Date;

public class HistoryObject {

    private final int medication_ID;
    private final int medication_image_id;
    private final String medication_name;
    private final Date medication_date;
    private final Date medication_time;
    private final String medication_dosage;
    private final String medication_dosage_quantity;
    private int _ID;

    public HistoryObject(int medication_ID, int medication_image_id, String medication_name, Date medication_date, Date medication_time, String medication_dosage, String medication_dosage_quantity) {
        this.medication_ID = medication_ID;
        this.medication_image_id = medication_image_id;
        this.medication_name = medication_name;
        this.medication_date = medication_date;
        this.medication_time = medication_time;
        this.medication_dosage = medication_dosage;
        this.medication_dosage_quantity = medication_dosage_quantity;
    }

    public int getMedication_ID() {
        return medication_ID;
    }

    public int getMedication_image_id() {
        return medication_image_id;
    }

    public String getMedication_name() {
        return medication_name;
    }

    public Date getMedication_date() {
        return medication_date;
    }

    public Date getMedication_time() {
        return medication_time;
    }

    public String getMedication_dosage() {
        return medication_dosage;
    }

    public String getMedication_dosage_quantity() {
        return medication_dosage_quantity;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    @Override
    public String toString() {
        return "HistoryObject{" +
                "medication_ID=" + medication_ID +
                ", medication_image_id=" + medication_image_id +
                ", medication_name='" + medication_name + '\'' +
                ", medication_date=" + medication_date +
                ", medication_time=" + medication_time +
                ", medication_dosage='" + medication_dosage + '\'' +
                ", medication_dosage_quantity='" + medication_dosage_quantity + '\'' +
                '}';
    }
}
