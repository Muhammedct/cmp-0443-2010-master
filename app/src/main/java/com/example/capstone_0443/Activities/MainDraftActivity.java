package com.example.capstone_0443.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

public class MainDraftActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    public String dateHour, dateDay;
    int j = 0;
    EditText editText,editTextSecond;

    private String authEmail(){
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
       // String email = sharedInfos();
        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + doorName + " " + statusDoor + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }
    private void sendEmailforTemps(String statusTemp, String tempName) {
        //Getting content for email
        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + tempName + " " + statusTemp + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }

    private void sendEmailforPir(String statusPir, String PirName) {
        //Getting content for email
        String email = sharedInfos();
        String subject = "Capstone0443 Security Mail ";
        String message = "Your " + PirName + " " + statusPir + " at " + realTime();
        SendMail sm = new SendMail(this, authEmail(), subject, message);
        sm.execute();
    }


    LinearLayout layoutDoor, layoutPir, layoutTemp, layoutSave;
    FirebaseDatabase testDb = FirebaseDatabase.getInstance();
    DatabaseReference testRef = testDb.getReference();

    private void newDoor(String userName,String doorName, String status, String hour, String day) {
        Door doors = new Door(userName,doorName, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Doors").child(doorName).setValue(doors);
    }

    private void newTemperature(String userName,String temperatureName, String status, String hour, String day) {
        Temperature temperature = new Temperature(userName,status, day, hour, temperatureName);
        testRef.child("Users").child(userName).child("Data").child("Temps").child(temperatureName).setValue(temperature);
        // DatabaseReference tempRef = testRef.child("Temps").child(name);
        //  tempRef.setValue(temperature);

    }
    private void newPIR(String userName,String status, String pirName, String hour, String day) {
        PIR Pirs = new PIR(userName,status, pirName, hour, day);
        testRef.child("Users").child(userName).child("Data").child("PIRs").child(pirName).setValue(Pirs);
    }

    private void newHistoryDoor(String userName,String name, String day, String hour, String status) {
        History logs = new History(userName,name, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Logs").child(day).child(name).child(hour).setValue(logs);
    }

    private void newHistoryPir(String userName,String name, String day, String hour, String status) {
        History logs = new History(userName,name, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Logs").child(day).child(name).child(hour).setValue(logs);
    }

    private void newHistoryTemps(String userName,String name, String day, String hour, String status) {
        History logs = new History(userName,name, day, hour, status);
        testRef.child("Users").child(userName).child("Data").child("Logs").child(day).child(name).child(hour).setValue(logs);
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
        final String email = user.getUid();
        Log.e("LogIn", "User Email : ---- "+user.getEmail());


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
                        if (realHour() >= 17 || realHour() <= 6)  //Control your emulator date , it could be different.. !!
                            sendEmailforDoors("opened", "Door" + a); // only sent mail between 17:00 - 06:00
                        newDoor(""+email,"Door" + a, "Opened", "" + dateHour, "" + dateDay);
                        newHistoryDoor(""+email,"Door" + a, "" + dateDay, "" + dateHour, "Opened");
                        Toast.makeText(getApplicationContext(), "" +
                                "Opened", Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.parseColor("#ba160c"));
                        j++;


                    } else {
                        if (realHour() >= 17 && realHour() < 6)
                            sendEmailforDoors("closed", "Door" + a);
                        newDoor(""+email,"Door" + a, "Closed", "" + dateHour, "" + dateDay);
                        newHistoryDoor(""+email,"Door" + a, "" + dateDay, "" + dateHour, "Closed");
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
                        newPIR(""+email,"Detected", "Pir" + a, "" + dateHour, "" + dateDay);
                        newHistoryPir(  ""+email,"Pir" + a, "" + dateDay, "" + dateHour, "Detected");
                        Toast.makeText(getApplicationContext(), "" +
                                "Detected", Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.parseColor("#ba160c"));
                        j++;

                    } else {
                        if (realHour() >= 17 || realHour() <= 6)
                            sendEmailforPir("closed", "Pir" + a);
                        newPIR(""+email,"Not Detected", "Pir" + a, "" + dateHour, "" + dateDay);
                        newHistoryPir(""+email,"Pir" + a, "" + dateDay, "" + dateHour, "Not Detected");
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

/*
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
*/

        // Temp

        editText = new EditText(this);
        final int a =1;
        editText.setId(a);
        editText.setHint("Temp " + " " + a);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams paramText = (LinearLayout.LayoutParams) save.getLayoutParams();
        paramText.weight = 1.0f;
        editText.setLayoutParams(paramText);
        layoutTemp.addView(editText);


        final int b =2;
        if(temp==2){

            editTextSecond = new EditText(this);

            editTextSecond.setId(b);
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
                Date dateTemp = new Date();
                dateHour = String.format("%tT", dateTemp);
                dateDay = String.format("%tF", dateTemp);

                if(temp==1){
                   if(editText.getText().equals("")){
                       Toast.makeText(getApplicationContext(), "Temperature value is null", Toast.LENGTH_LONG).show();
                   }
                   else{

                       newTemperature(""+email,"Temp" + a, "" + editText.getText(), "" + dateHour, "" + dateDay);
                       newHistoryTemps(""+email,"Temp" + a, "" + dateDay, "" + dateHour, "" + editText.getText());
                       sendEmailforTemps(""+editText.getText(),"Temp" +a);
                   }

                }
                else if(temp==2){
                    if(editText.getText()==null){
                        Toast.makeText(getApplicationContext(), "Temperature value is null", Toast.LENGTH_LONG).show();
                    }
                    else {
                        newTemperature(""+email,"Temp" + a, "" + editText.getText(), "" + dateHour, "" + dateDay);
                        newHistoryTemps(""+email,"Temp" + a, "" + dateDay, "" + dateHour, "" + editText.getText());
                        sendEmailforTemps(""+editText.getText(),"Temp" +a);
                        newTemperature(""+email,"Temp" + b, "" + editTextSecond.getText(), "" + dateHour, "" + dateDay);
                        newHistoryTemps(""+email,"Temp" + b, "" + dateDay, "" + dateHour, "" + editTextSecond.getText());
                        sendEmailforTemps(""+editTextSecond.getText(),"Temp" +b);
                    }

                }

            }
        });

    }


}


