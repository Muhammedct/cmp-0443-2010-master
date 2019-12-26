package com.example.capstone_0443.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.capstone_0443.Model.Door;
import com.example.capstone_0443.Model.History;
import com.example.capstone_0443.Model.PIR;
import com.example.capstone_0443.Model.Temperature;
import com.example.capstone_0443.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.capstone_0443.SendMail;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainDraftActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    public String dateHour, dateDay;
    int j = 0;
    EditText editText, editTextSecond;


    private String authEmail() {
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.e("LogOn", "Login olmuş user yok");
                }
            }
        };
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getEmail();
    }

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

    private Integer sharedInfosTemp1() {
        SharedPreferences sharedPrefs = getSharedPreferences("mailPreferences", MODE_PRIVATE);
        Integer temp1Int = sharedPrefs.getInt("temp1Int", 30);
        return temp1Int;
    }

    private Integer sharedInfosTemp2() {
        SharedPreferences sharedPrefs = getSharedPreferences("mailPreferences", MODE_PRIVATE);
        Integer temp2Int = sharedPrefs.getInt("temp2Int", 30);
        return temp2Int;
    }
    private Integer sharedInfosHour1() {
        SharedPreferences sharedPrefs = getSharedPreferences("mailPreferences", MODE_PRIVATE);
        Integer hour1Int = sharedPrefs.getInt("hour1Int", 06);
        //Toast.makeText(this, "--"+hour1Int, Toast.LENGTH_SHORT).show();
        Log.e("sharedInfo1",""+hour1Int);
        return hour1Int;
    }
    private Integer sharedInfosHour2() {
        SharedPreferences sharedPrefs = getSharedPreferences("mailPreferences", MODE_PRIVATE);
        Integer hour2Int = sharedPrefs.getInt("hour2Int", 17);
        Log.e("sharedInfo2",""+hour2Int);
        //Toast.makeText(this, "--"+hour2Int, Toast.LENGTH_SHORT).show();
        return hour2Int;
    }


    private void sendEmailforDoors(String statusDoor, String doorName) {
        //Getting content for email
        // String email = sharedInfos();

        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + doorName + " " + statusDoor + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }

    private void sendEmailforTemps(String statusTemp, String tempName, Integer limitValue) {
        //Getting content for email
        String subject = "Capstone0443 Security Mail ";
        String message = "Your" + " temperature sensor" + "( " + tempName + " )" + " has exceeded the expected value" +
                "<p>" + "Detected temperature : " + "<font size=\"3\" color=\"red\"> <b>"+statusTemp +"</b>"+ "</p> </font>" +
                "<p>" + "Expected temperature : " + "<b>"+limitValue +"</b>"+ "</p>"+
                "\n" + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }
    private void sendEmailforTemps2(String statusTemp, String tempName, Integer limitValue) {
        //Getting content for email
        String subject = "Capstone0443 Security Mail ";
        String message = "Your" + " temperature sensor" + "( " + tempName + " )" + " has exceeded the expected value" +
                "<p>" + "Detected temperature : " + "<font size=\"3\" color=\"red\"> <b>"+statusTemp +"</b>"+ "</p> </font>" +
                "<p>" + "Expected temperature : " + "<b>"+limitValue +"</b>"+ "</p>"+
                "\n" + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }

    private void sendEmailforPir(String statusPir, String PirName) {
        //Getting content for email
        //String email = sharedInfos();
        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + PirName + " " + statusPir + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }


    LinearLayout layoutDoor, layoutPir, layoutTemp, layoutSave;
    FirebaseDatabase testDb = FirebaseDatabase.getInstance();
    DatabaseReference testRef = testDb.getReference();

    private void newDoor(String userName, String doorName, String status, String hour, String day) {
        Door doors = new Door (userName, status, doorName, hour, day);
        testRef.child("Users").child(userName).child("Data").child("Doors").child(doorName).setValue(doors);
    }

    private void newTemperature(String userName, String temperatureName, String status, String hour, String day) {
        Temperature temperature = new Temperature(userName, status, day, hour, temperatureName);
        testRef.child("Users").child(userName).child("Data").child("Temps").child(temperatureName).setValue(temperature);
        // DatabaseReference tempRef = testRef.child("Temps").child(name);
        //  tempRef.setValue(temperature);

    }

    private void newPIR(String userName, String status, String pirName, String hour, String day) {
        PIR Pirs = new PIR(userName, status, pirName, hour, day);
        testRef.child("Users").child(userName).child("Data").child("PIRs").child(pirName).setValue(Pirs);
    }

    private void newHistoryDoor(String userName, String name, String day, String hour, String status) {
        History logs = new History(userName, name, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Logs").child(day).child(hour).setValue(logs);
    }

    private void newHistoryPir(String userName, String name, String day, String hour, String status) {
        History logs = new History(userName, name, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Logs").child(day).child(hour).setValue(logs);
    }

    private void newHistoryTemps(String userName, String name, String day, String hour, String status) {
        History logs = new History(userName, name, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Logs").child(day).child(hour).setValue(logs);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //-------

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Eğer geçerli bir kullanıcı oturumu yoksa LoginActivity e geçilir.
                // Oturum kapatma işlemi yapıldığında bu sayede LoginActivity e geçilir.
                if (user == null) {
                    Log.e("LogOn", "Login olmuş user yok");

                    //startActivity(new Intent(Logs.this, LoginActivity.class));
                    //finish();
                }
            }
        };
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userUid = user.getUid();
        Log.e("LogIn", "User Email : ---- " + user.getEmail());


        //-------

        Date date = new Date();
        dateHour = String.format("%tT", date);
        dateDay = String.format("%tF", date);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_draft);

        Bundle extras = getIntent().getExtras();
        Integer door = Integer.parseInt(extras.getString("door"));
        Integer pir = Integer.parseInt(extras.getString("pir"));
        final Integer temp = Integer.parseInt(extras.getString("temperature"));


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
                        if (realHour() >= sharedInfosHour1() || realHour() <= sharedInfosHour2())  //Control your emulator date , it could be different.. !!
                            sendEmailforDoors("opened", "Door" + a); // only sent mail between 17:00 - 06:00 //default
                        newDoor("" + userUid, "Door" + a, "Opened", "" + dateHour, "" + dateDay);
                        newHistoryDoor("" + userUid, "Door" + a, "" + dateDay, "" + dateHour, "Opened");
                        Toast.makeText(getApplicationContext(), "" +
                                "Opened", Toast.LENGTH_SHORT).show();
                        //button.setBackgroundColor(Color.parseColor("#ba160c"));
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        button.setBackgroundColor(color);
                        j++;


                    } else {
                        if (realHour() >= sharedInfosHour1() && realHour() < sharedInfosHour2())
                            sendEmailforDoors("closed", "Door" + a);
                        newDoor("" + userUid, "Door" + a, "Closed", "" + dateHour, "" + dateDay);
                        newHistoryDoor("" + userUid, "Door" + a, "" + dateDay, "" + dateHour, "Closed");
                        Toast.makeText(getApplicationContext(), "" +
                                "Closed", Toast.LENGTH_SHORT).show();
                        //button.setBackgroundColor(Color.parseColor("#00ff00"));
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        button.setBackgroundColor(color);
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
                        if (realHour() >= sharedInfosHour1() || realHour() <= sharedInfosHour2())
                            sendEmailforPir("detected", "Pir" + a);
                        newPIR("" + userUid, "Detected", "Pir" + a, "" + dateHour, "" + dateDay);
                        newHistoryPir("" + userUid, "Pir" + a, "" + dateDay, "" + dateHour, "Detected");
                        Toast.makeText(getApplicationContext(), "" +
                                "Detected", Toast.LENGTH_SHORT).show();
                       // button.setBackgroundColor(Color.parseColor("#ba160c"));
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        button.setBackgroundColor(color);
                        j++;

                    } else {
                        if (realHour() >= sharedInfosHour1() || realHour() <= sharedInfosHour2())
                            //sendEmailforPir("closed", "Pir" + a);  // Hareket sensörü algılamadıysa mail atmaya gerek yok
                        newPIR("" + userUid, "Not Detected", "Pir" + a, "" + dateHour, "" + dateDay);
                        newHistoryPir("" + userUid, "Pir" + a, "" + dateDay, "" + dateHour, "Not Detected");
                        Toast.makeText(getApplicationContext(), "" +
                                "Not Detected", Toast.LENGTH_SHORT).show();
                      //  button.setBackgroundColor(Color.parseColor("#00ff00"));
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        button.setBackgroundColor(color);
                        j++;

                    }
                }
            });
            layoutPir.addView(button);

        }

        final Button save = new Button(this);

        save.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        save.setText("Save");
        LinearLayout.LayoutParams paramSave = (LinearLayout.LayoutParams) save.getLayoutParams();
        //paramSave.weight = 1.0f;
        save.setLayoutParams(paramSave);
        layoutSave.addView(save);

        editText = new EditText(this);
        final int a = 1;
        editText.setId(a);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("Temp " + " " + a);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams paramText = (LinearLayout.LayoutParams) save.getLayoutParams();
        paramText.weight = 1.0f;
        editText.setLayoutParams(paramText);
        layoutTemp.addView(editText);


        final int b = 2;
        if (temp == 2) {

            editTextSecond = new EditText(this);

            editTextSecond.setId(b);
            editTextSecond.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextSecond.setHint("Temp " + " " + b);
            editTextSecond.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams paramTextSecond = (LinearLayout.LayoutParams) save.getLayoutParams();
            paramTextSecond.weight = 1.0f;
            editTextSecond.setLayoutParams(paramTextSecond);
            layoutTemp.addView(editTextSecond);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                save.setBackgroundColor(color);
                    Date dateTemp = new Date();
                    dateHour = String.format("%tT", dateTemp);
                    dateDay = String.format("%tF", dateTemp);
                    int temp1Final = Integer.parseInt(editText.getText().toString());

                    if (temp == 1) {
                        if (editText.getText().equals("")) {
                            Toast.makeText(getApplicationContext(), "Temperature value is null", Toast.LENGTH_LONG).show();
                        } else {

                            newTemperature("" + userUid, "Temp" + a, "" + editText.getText(), "" + dateHour, "" + dateDay);
                            newHistoryTemps("" + userUid, "Temp" + a, "" + dateDay, "" + dateHour, "" + editText.getText());
                            if (temp1Final > sharedInfosTemp1())
                                sendEmailforTemps("" + editText.getText(), "Temp" + a, sharedInfosTemp1());

                        }

                    } else if (temp == 2) {
                            int temp2Final = Integer.parseInt(editTextSecond.getText().toString());
                            if (editText.getText() == null) {
                                Toast.makeText(getApplicationContext(), "Temperature value is null", Toast.LENGTH_LONG).show();
                            } else {

                                newTemperature("" + userUid, "Temp" + a, "" + editText.getText(), "" + dateHour, "" + dateDay);
                                newHistoryTemps("" + userUid, "Temp" + a, "" + dateDay, "" + dateHour, "" + editText.getText());
                                if (temp1Final > sharedInfosTemp1())
                                    sendEmailforTemps("" + editText.getText(), "Temp" + a, sharedInfosTemp1());

                                newTemperature("" + userUid, "Temp" + b, "" + editTextSecond.getText(), "" + dateHour, "" + dateDay);
                                newHistoryTemps("" + userUid, "Temp" + b, "" + dateDay, "" + dateHour, "" + editTextSecond.getText());
                                if (temp2Final > sharedInfosTemp2())
                                    sendEmailforTemps2("" + editTextSecond.getText(), "Temp" + b, sharedInfosTemp2());
                            }



                    }
                }

        });

    }


}


