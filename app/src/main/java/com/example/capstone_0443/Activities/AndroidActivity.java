package com.example.capstone_0443.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.capstone_0443.R;
import com.example.capstone_0443.Show.Door;
import com.example.capstone_0443.Show.Logs;
import com.example.capstone_0443.Show.Pir;
import com.example.capstone_0443.Show.Temperature;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AndroidActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    //Button door,pir,temps,logs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new Door());
        transaction.commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.door:
                selectedFragment=new Door();
                    break;

                case R.id.pir:
                    selectedFragment = new Pir();
                    break;

                case R.id.temperature:
                    selectedFragment=new Temperature();
                    break;

                case R.id.logs:
                    selectedFragment=new Logs();
                    break;

            }


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;



        }


    };




}

