package com.example.capstone_0443.Model;

public class History {

public String historyHour;
public String historyDay;
public String historyName;
public String status;

    public History(){};
    public History(String historyName, String historyDay, String historyHour, String status) {
        this.historyHour = historyHour;
        this.historyDay = historyDay;
        this.historyName = historyName;
        this.status = status;
    }

    public String getHistoryHour() {
        return historyHour;
    }

    public void setHistoryHour(String historyHour) {
        this.historyHour = historyHour;
    }

    public String getHistoryDay() {
        return historyDay;
    }

    public void setHistoryDay(String historyDay) {
        this.historyDay = historyDay;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
