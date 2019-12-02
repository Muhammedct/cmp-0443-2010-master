package com.example.capstone_0443.Model;

public class Door {

    public String doorStatus;
    public String doorName;
    public String doorDay;
    public String doorHour;


    public Door(){};

    public Door(String doorStatus, String doorName, String doorHour, String doorDay) {
        this.doorStatus = doorStatus;
        this.doorName = doorName;
        this.doorHour = doorHour;
        this.doorDay = doorDay;
    }


    public String getDoorHour() {
        return doorHour;
    }

    public void setDoorHour(String doorHour) {
        this.doorHour = doorHour;
    }

    public String getDoorDay() {
        return doorDay;
    }

    public void setDoorDay(String doorDay) {
        this.doorDay = doorDay;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }


}
