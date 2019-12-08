package com.example.capstone_0443.Model;

public class Temperature {

    public String userName;
    public String temp;
    public String tempDay;
    public String tempHour;
    public String tempName;


    public Temperature(){};

    public Temperature(String userName,String temp, String tempDay, String tempHour, String tempName) {
        this.userName = userName;
        this.temp = temp;
        this.tempDay = tempDay;
        this.tempHour = tempHour;
        this.tempName = tempName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTempDay() {
        return tempDay;
    }

    public void setTempDay(String tempDay) {
        this.tempDay = tempDay;
    }

    public String getTempHour() {
        return tempHour;
    }

    public void setTempHour(String tempHour) {
        this.tempHour = tempHour;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {

        this.temp = temp;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }



}
