package com.example.capstone_0443.Model;

public class Door {

    public String userName;
    public String doorStatus;
    public String doorName;
    public String doorDay;
    public String doorHour;


    public Door() { }



    public Door(String userName, String doorStatus, String doorName, String doorHour, String doorDay) {
        this.userName = userName;
        this.doorStatus = doorStatus;
        this.doorName = doorName;
        this.doorHour = doorHour;
        this.doorDay = doorDay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
