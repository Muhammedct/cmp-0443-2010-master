package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.capstone_0443.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String[] HOURS = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"
    };
    private static final String[] TEMP = new String[]{
            "0", "5", "10", "15", "20", "25", "30", "35", "40", "45"
    };
    private SharedPreferences sharedPreferences;
    private MultiAutoCompleteTextView tempFirst, tempSecond, hourFirst, hourSecond;
    Button buttonLogin;


    // for keep & show the infos in screen
    private void getPreferencesDatas() {
        SharedPreferences preferences = getSharedPreferences("mailPreferences",MODE_PRIVATE);

            Integer m = preferences.getInt("temp1Int",30);
            Integer mx = preferences.getInt("temp2Int",30);
            Integer mxy = preferences.getInt("hour1Int",30);
            Integer mxz = preferences.getInt("hour2Int",30);

            tempFirst.setText(m.toString());
            tempSecond.setText(mx.toString());
            hourFirst.setText(mxy.toString());
            hourSecond.setText(mxz.toString());
    }

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
        hourSecond = findViewById(R.id.hourSecond);
        ArrayAdapter<String> adapterSecondHour = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HOURS);
        hourSecond.setAdapter(adapterSecondHour);
        hourSecond.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //SaveButton
        buttonLogin = findViewById(R.id.buttonLogin);

//       SharedPreferences sharedPrefs = getSharedPreferences("mailPreferences", MODE_PRIVATE);
//        Integer hour11Int = sharedPref.getInt("hour1Int", 06);
//        Integer hour22Int = sharedPref.getInt("hour2Int", 17);
//        Integer temp22Int = sharedPref.getInt("temp2Int", 30);
//        Integer temp11Int = sharedPref.getInt("temp1Int", 30);
//
//        tempFirst.setText(temp11Int);
//        tempSecond.setText(temp22Int);
//        hourFirst.setText(hour11Int);
//        hourSecond.setText(hour22Int);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("mailPreferences", MODE_PRIVATE);

                String temp1Value = tempFirst.getText().toString(); //Edittextten alınıyor
                String temp2Value = tempSecond.getText().toString();
                String hour1Value = hourFirst.getText().toString();
                String hour2Value = hourSecond.getText().toString();


                if (temp1Value == null || temp1Value.equals("") || temp2Value.equals("") || temp2Value == null ||
                        hour1Value == null || hour1Value.equals("") || hour2Value == null || hour2Value.equals("")) { //alınan değerlerin boş olup olmaması kontrol ediliyor

                    Toast.makeText(SettingsActivity.this, "Fill in all fields.", Toast.LENGTH_LONG).show();

                } else { //değerler boş değilse

                    int temp1Int = Integer.parseInt(temp1Value); //Alınan değer Integer'a çevriliyor
                    int temp2Int = Integer.parseInt(temp2Value);
                    int hour1Int = Integer.parseInt(hour1Value);
                    int hour2Int = Integer.parseInt(hour2Value);
                    SharedPreferences.Editor editor = sharedPref.edit(); //SharedPreferences'a kayıt eklemek için editor oluşturuyoruz
                    editor.putInt("temp1Int", temp1Int); //int değerler ekleniyor
                    editor.putInt("temp2Int", temp2Int);
                    editor.putInt("hour1Int", hour1Int);
                    editor.putInt("hour2Int", hour2Int);

                    editor.commit(); //Kayıt


                    Toast.makeText(SettingsActivity.this, "Preferences saved.", Toast.LENGTH_SHORT).show();

                }
            }

        });

        sharedPreferences=getSharedPreferences("mailPreferences",MODE_PRIVATE);
        getPreferencesDatas();

    }
}
