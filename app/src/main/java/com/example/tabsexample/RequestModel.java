package com.example.tabsexample;

import java.util.Calendar;

public class RequestModel {
    private int line;
    private String date;
    private String time;

    public RequestModel() {

    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLine() {
        return line;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RequestModel(int line, String date, String time) {
        this.line = line;
        this.date = date;
        this.time = time;
    }
}