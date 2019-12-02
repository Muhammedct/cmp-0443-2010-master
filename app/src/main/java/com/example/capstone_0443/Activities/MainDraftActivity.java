package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.capstone_0443.Model.Door;
import com.example.capstone_0443.Model.History;
import com.example.capstone_0443.Model.PIR;
import com.example.capstone_0443.Model.Temperature;
import com.example.capstone_0443.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.capstone_0443.SendMail;

import java.util.Calendar;
import java.util.Date;

public class MainDraftActivity extends AppCompatActivity {

    String text;
    EditText ed[];
    public String dateHour, dateDay;
    ArrayAdapter<CharSequence> adapterTemp;
    int j = 0;
    int a;

    private String realTime() {
        Date date = new Date();
        dateHour = String.format("%tT", date);
        dateDay = String.format("%tF", date);
        return dateDay + " || " + dateHour;
    }

    private Integer realHour() { //Control your emulator date , it could be different.. !! // only sent mail between 17:00 - 06:00
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        return currentHourIn24Format;
    }

    private String sharedInfos() {
        SharedPreferences sharedPrefs = getSharedPreferences("mailPref", Context.MODE_PRIVATE);
        String stringMail = sharedPrefs.getString("stringMail", "Kayıt Yok");
        String stringPass = sharedPrefs.getString("stringPass", "Kayıt Yok");

        Log.e("test", "" + stringMail + "   " + stringPass);
        if (realHour() >= 17) Log.e("test", "Saat 17:00'i gecti : " + realTime());

        return stringMail;
    }

    private void sendEmailforDoors(String statusDoor, String doorName) {
        //Getting content for email
        String email = sharedInfos();
        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + doorName + " " + statusDoor + " at " + realTime();
        SendMail sm = new SendMail(this, email, subject, message);
        sm.execute();
    }

    private void sendEmailforPir(String statusPir, String PirName) {
        //Getting content for email
        String email = sharedInfos();
        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + PirName + " " + statusPir + " at " + realTime();
        SendMail sm = new SendMail(this, email, subject, message);
        sm.execute();
    }


    LinearLayout layoutDoor, layoutPir, layoutTemp, layoutSave;
    FirebaseDatabase testDb = FirebaseDatabase.getInstance();
    DatabaseReference testRef = testDb.getReference();

    private void newDoor(String name, String status, String hour, String day) {
        Door doors = new Door(name, day, hour, status);
        testRef.child("Doors").child(name).setValue(doors);
    }

    private void newTemperature(String name, String status, String hour, String day) {
        Temperature temperature = new Temperature(status, day, hour, name);

        testRef.child("Temps").child(name).setValue(temperature);

        // DatabaseReference tempRef = testRef.child("Temps").child(name);
        //  tempRef.setValue(temperature);

    }


    private void newHistoryDoor(String name, String day, String hour, String status) {
        History logs = new History(name, day, hour, status);
        testRef.child("Logs").child(day).child(name).child(hour).setValue(logs);
    }

    private void newHistoryPir(String name, String day, String hour, String status) {
        History logs = new History(name, day, hour, status);
        testRef.child("Logs").child(day).child(name).child(hour).setValue(logs);
    }

    private void newHistoryTemps(String name, String day, String hour, String status) {
        History logs = new History(name, day, hour, status);
        testRef.child("Logs").child(day).child(name).child(hour).setValue(logs);
    }

    private void newPIR(String status, String name, String hour, String day) {
        PIR Pirs = new PIR(status, name, hour, day);
        testRef.child("PIRs").child(name).setValue(Pirs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Date date = new Date();
        dateHour = String.format("%tT", date);
        dateDay = String.format("%tF", date);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_draft);

        Bundle extras = getIntent().getExtras();
        Integer door = Integer.parseInt(extras.getString("door"));
        Integer pir = Integer.parseInt(extras.getString("pir"));
        Integer temp = Integer.parseInt(extras.getString("temperature"));


        layoutDoor = findViewById(R.id.layoutDoor);
        layoutPir = findViewById(R.id.layoutPir);
        layoutTemp = findViewById(R.id.layoutTemp);
        layoutSave = findViewById(R.id.layoutSave);

        for (int i = 1; i <= door; i++) {
            final Button button = new Button(this);
            button.setId(i);
            button.setText("Door " + " " + i);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                    button.getLayoutParams();
            params.weight = 1.0f;
            button.setLayoutParams(params);

            final int a = button.getId();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //changeDoor(a);
                    Date date = new Date();
                    dateHour = String.format("%tT", date);
                    dateDay = String.format("%tF", date);
                    if (j % 2 == 0) {
                        if (realHour() >= 17 || realHour() <= 6)  //Control your emulator date , it could be different.. !!
                            sendEmailforDoors("opened", "Door" + a); // only sent mail between 17:00 - 06:00
                        newDoor("Door" + a, "Opened", "" + dateHour, "" + dateDay);
                        newHistoryDoor("Door" + a, "" + dateDay, "" + dateHour, "Opened");
                        Toast.makeText(getApplicationContext(), "" +
                                "Opened", Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.parseColor("#ba160c"));
                        j++;


                    } else {
                        if (realHour() >= 17 && realHour() < 6)
                            sendEmailforDoors("closed", "Door" + a);
                        newDoor("Door" + a, "Closed", "" + dateHour, "" + dateDay);
                        newHistoryDoor("Door" + a, "" + dateDay, "" + dateHour, "Closed");
                        Toast.makeText(getApplicationContext(), "" +
                                "Closed", Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.parseColor("#00ff00"));
                        j++;

                    }
                }
            });

            layoutDoor.addView(button);


        }

        for (int i = 1; i <= pir; i++) {
            final Button button = new Button(this);
            button.setId(i);
            button.setText("Pir " + " " + i);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            params.weight = 1.0f;
            button.setLayoutParams(params);
            final int a = button.getId();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //changePir(a);
                    Date date = new Date();
                    dateHour = String.format("%tT", date);
                    dateDay = String.format("%tF", date);
                    if (j % 2 == 0) {
                        if (realHour() >= 17 || realHour() <= 6)
                            sendEmailforPir("opened", "Pir" + a);
                        newPIR("Detected", "Pir" + a, "" + dateHour, "" + dateDay);
                        newHistoryPir("Pir" + a, "" + dateDay, "" + dateHour, "Detected");
                        Toast.makeText(getApplicationContext(), "" +
                                "Detected", Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.parseColor("#ba160c"));
                        j++;

                    } else {
                        if (realHour() >= 17 || realHour() <= 6)
                            sendEmailforPir("closed", "Pir" + a);
                        newPIR("Not Detected", "Pir" + a, "" + dateHour, "" + dateDay);
                        newHistoryPir("Pir" + a, "" + dateDay, "" + dateHour, "Not Detected");
                        Toast.makeText(getApplicationContext(), "" +
                                "Not Detected", Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.parseColor("#00ff00"));
                        j++;

                    }
                }
            });
            layoutPir.addView(button);

        }

        Button save = new Button(this);

        save.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        save.setText("Save");
        LinearLayout.LayoutParams paramSave = (LinearLayout.LayoutParams) save.getLayoutParams();
        //paramSave.weight = 1.0f;
        save.setLayoutParams(paramSave);
        layoutSave.addView(save);
//TEMP


        for (int i = 1; i <= temp; i++) {
            final int a = i;
            final EditText editText = new EditText(this);
            editText.setId(i);
            editText.setHint("Temp " + " " + i);

            editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams paramText = (LinearLayout.LayoutParams) save.getLayoutParams();
            paramText.weight = 1.0f;
            editText.setLayoutParams(paramText);
            layoutTemp.addView(editText);


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newTemperature("Temp" + a, "" + editText.getText(), "" + dateHour, "" + dateDay);
                    newHistoryTemps("Temp" + a, "" + dateDay, "" + dateHour, "" + text);
                }
            });


        }


    }


}


