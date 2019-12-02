package com.example.capstone_0443.Model;

public class PIR {

    public String pirStatus;
    public String pirName;
    public String pirHour;
    public String pirDay;

    public PIR(){};

    public PIR(String pirStatus, String pirName, String pirHour, String pirDay) {
        this.pirStatus = pirStatus;
        this.pirName = pirName;
        this.pirHour = pirHour;
        this.pirDay = pirDay;
    }

    public String getPirHour() {
        return pirHour;
    }

    public void setPirHour(String pirHour) {
        this.pirHour = pirHour;
    }

    public String getPirDay() {
        return pirDay;
    }

    public void setPirDay(String pirDay) {
        this.pirDay = pirDay;
    }

    public String getPirStatus() {
        return pirStatus;
    }

    public void setPirStatus(String pirStatus) {
        this.pirStatus = pirStatus;
    }

    public String getPirName() {
        return pirName;
    }

    public void setPirName(String pirName) {
        this.pirName = pirName;
    }
}
