package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.capstone_0443.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    Button buttonSimulation,buttonAndroid;
    Button buttonLogin,buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().subscribeToTopic("SELAM");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonLogin=findViewById(R.id.buttonLogin);
        buttonRegister=findViewById(R.id.buttonRegister);

        buttonSimulation=findViewById(R.id.buttontest);
        buttonAndroid=findViewById(R.id.buttonAndroid);


        buttonAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AndroidActivity.class);
                startActivity(intent);

            }
        });

        buttonSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ArduinoActivity.class);
                startActivity(intent);

            }
        });


    } @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(settings);
                return true;


        }

        return false;
    }

}
