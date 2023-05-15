package com.example.rrmuchedzi.vimbisomedicare;

import java.util.Date;

/**
 * Created by Rufaro R.Muchedzi on 12/18/2017.
 */

public class ScheduledEvent {

    private final int _ID;
    private final int medication_ID;
    private final Date schedule_date;
    private final Date scheduled_time;

    public ScheduledEvent(int _ID, int medication_ID, Date schedule_date, Date scheduled_time) {
        this._ID = _ID;
        this.medication_ID = medication_ID;
        this.schedule_date = schedule_date;
        this.scheduled_time = scheduled_time;
    }

    public int get_ID() {
        return _ID;
    }

    public int getMedication_ID() {
        return medication_ID;
    }

    public Date getSchedule_date() {
        return schedule_date;
    }

    public Date getScheduled_time() {
        return scheduled_time;
    }

    @Override
    public String toString() {
        return "ScheduledEvent{" +
                "_ID=" + _ID +
                ", medication_ID=" + medication_ID +
                ", schedule_date=" + schedule_date +
                ", scheduled_time=" + scheduled_time +
                '}';
    }
}
