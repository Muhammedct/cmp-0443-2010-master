package com.example.capstone_0443.Model;

import com.example.capstone_0443.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class History {


    public String userName;
    public String historyHour;
    public String historyDay;
    public String historyName;
    public String status;

    public History() {
    }


    public History(String userName, String historyName, String historyDay, String historyHour, String status) {
        this.userName = userName;
        this.historyHour = historyHour;
        this.historyDay = historyDay;
        this.historyName = historyName;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
