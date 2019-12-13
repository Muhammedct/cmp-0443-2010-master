package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.example.capstone_0443.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String[] HOURS = new String[]{
            "0", "1", "2", "3", "4","5","6","7","8","9","10","11","12","13",
            "14","15","16","17","18","19","20","21","22","23","24"
    };
    private static final String[] TEMP = new String[]{
            "0", "5", "10", "15", "20","25","30","35","40","45"
    };

    private MultiAutoCompleteTextView tempFirst,tempSecond,hourFirst,hourSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Temp1
        tempFirst = findViewById(R.id.tempFirst);
        ArrayAdapter<String> adapterFirstTemp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TEMP);
        tempFirst.setAdapter(adapterFirstTemp);
        tempFirst.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //Temp2

        tempSecond = findViewById(R.id.tempSecond);
        ArrayAdapter<String> adapterSecondTemp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TEMP);
        tempSecond.setAdapter(adapterSecondTemp);
        tempSecond.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //Hour1
        hourFirst = findViewById(R.id.hourFirst);
        ArrayAdapter<String> adapterFirstHour = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HOURS);
        hourFirst.setAdapter(adapterFirstHour);
        hourFirst.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //Hour2
        hourSecond = findViewById(R.id.hourFirst);
        ArrayAdapter<String> adapterSecondHour = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HOURS);
        hourSecond.setAdapter(adapterSecondHour);
        hourSecond.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }
}
