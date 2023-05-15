package com.example.rrmuchedzi.vimbisomedicare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MedicationTimes implements Serializable {

    public static final long serialVersionUID = 20171211;

    private HashMap <String, String> times = new HashMap<>();
    private int totalTimes;

    public MedicationTimes( HashMap <String, String> times, int totalTimes) {
        this.times = times;
        this.totalTimes = totalTimes;
    }

    public HashMap <String, String> getTimes() {
        return times;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    @Override
    public String toString() {
        return "MedicationTimes{" +
                "times=" + times +
                ", totalTimes=" + totalTimes +
                '}';
    }
}
