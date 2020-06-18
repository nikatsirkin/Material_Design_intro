package com.example.tabsexample;

public class RequestModel {
    private int line;
    private String date, time;

    public RequestModel() {
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public RequestModel(int line, String date, String time) {
        this.line = line;
        this.date = date;
        this.time = time;
    }
}
