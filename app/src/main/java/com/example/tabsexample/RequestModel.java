package com.example.tabsexample;

import java.util.Calendar;

public class RequestModel {
    private int line;
    private String date;

    public RequestModel() {
    }

    public int getLine() {
        return line;
    }

    public String getDate() {
        return date;
    }

    public void setCalendar(String date) {
        this.date = date;
    }

    public RequestModel(int line, String date) {
        this.line = line;
        this.date = date;
    }
}
