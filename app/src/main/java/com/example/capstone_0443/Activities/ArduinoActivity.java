package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.capstone_0443.R;

public class ArduinoActivity extends AppCompatActivity   {

    String door,pir,temperature;
    RadioGroup rgDoor,rgPir,rgTemperature;
    RadioButton oneDoor,twoDoor,threeDoor,fourDoor,onePir,twoPir,threePir,fourPir,oneTemperature,twoTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);
        Button buttonSave=findViewById(R.id.buttonSave);

        rgDoor=findViewById(R.id.rgDoor);
        rgPir=findViewById(R.id.rgPir);
        rgTemperature=findViewById(R.id.rgTemperature);

        oneDoor=findViewById(R.id.oneDoor);
        twoDoor=findViewById(R.id.twoDoor);
        threeDoor=findViewById(R.id.threeDoor);
        fourDoor=findViewById(R.id.fourDoor);

        onePir=findViewById(R.id.onePir);
        twoPir=findViewById(R.id.twoPir);
        threePir=findViewById(R.id.threePir);
        fourPir=findViewById(R.id.fourPir);

        oneTemperature=findViewById(R.id.oneTemperature);
        twoTemperature=findViewById(R.id.twoTemperature);

        door = (String) oneDoor.getText();
        pir = (String) onePir.getText();
        temperature = (String) oneTemperature.getText();


        rgDoor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (oneDoor.isChecked()) {

                 door = (String) oneDoor.getText();
            } else if (twoDoor.isChecked()) {

                 door = (String) twoDoor.getText();
            }

            else if(threeDoor.isChecked()){

                 door = (String) threeDoor.getText();
            }
            else if(fourDoor.isChecked()){

                 door = (String) fourDoor.getText();
            }
        }

        });

        rgPir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (onePir.isChecked()) {
                    pir = (String) onePir.getText();
                } else if (twoPir.isChecked()) {
                     pir = (String) twoPir.getText();
                }

                else if(threePir.isChecked()){

                    pir = (String) threePir.getText();
                }
                else if(fourPir.isChecked()){
                    pir = (String) fourPir.getText();
                }
            }

        });

        rgTemperature.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (oneTemperature.isChecked()) {

                    temperature = (String) oneTemperature.getText();
                } else if (twoTemperature.isChecked()) {

                    temperature = (String) twoTemperature.getText();
                }


            }

        });


        buttonSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v){
                Intent intent = new Intent(ArduinoActivity.this, MainDraftActivity.class);


                intent.putExtra("temperature",temperature);
                intent.putExtra("door",door);
                intent.putExtra("pir",pir);
                startActivity(intent);
            }
        });
    }



}




