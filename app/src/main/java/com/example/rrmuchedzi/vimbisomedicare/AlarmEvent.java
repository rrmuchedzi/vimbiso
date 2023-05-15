package com.example.rrmuchedzi.vimbisomedicare;

import java.sql.Time;
import java.util.Date;
import java.util.List;

class AlarmEvent {

    private String mName;
    private int mQuantity;
    private String mQuantityUnits;
    private Date mStartDate;
    private int [] mActivatedDays;
    private List <Time> mScheduledTimes;
    private String mNotes;
    private Date mEndDate;
    private boolean active;

    public AlarmEvent(String name, int quantity, String quantityUnits, Date startDate, int[] activatedDays, List<Time> scheduledTimes, String notes, Date endDate) {
        mName = name;
        mQuantity = quantity;
        mQuantityUnits = quantityUnits;
        mStartDate = startDate;
        mActivatedDays = activatedDays;
        mScheduledTimes = scheduledTimes;
        mNotes = notes;
        mEndDate = endDate;
    }

    public String getName() {
        return mName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getQuantityUnits() {
        return mQuantityUnits;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public int[] getActivatedDays() {
        return mActivatedDays;
    }

    public List<Time> getScheduledTimes() {
        return mScheduledTimes;
    }

    public String getNotes() {
        return mNotes;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public boolean isActive() {
        return active;
    }
}
