package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone_0443.R;

public class UserActivity extends AppCompatActivity {

    Button buttonSimulation,buttonAndroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        buttonSimulation=findViewById(R.id.buttontest);
        buttonAndroid=findViewById(R.id.buttonAndroid);


        buttonAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, AndroidActivity.class);
                startActivity(intent);

            }
        });

        buttonSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserActivity.this, ArduinoActivity.class);
                startActivity(intent);

            }
        });
    }
}
