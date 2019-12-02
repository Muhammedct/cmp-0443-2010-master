package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.capstone_0443.R;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,password,p_confirm;
    RelativeLayout register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        p_confirm=findViewById(R.id.pc);
        register=findViewById(R.id.registerButton);

    }
}
