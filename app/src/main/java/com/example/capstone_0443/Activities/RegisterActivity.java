package com.example.capstone_0443.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_0443.Model.User;
import com.example.capstone_0443.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText email,password,username;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        username=findViewById(R.id.username);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userMail = email.getText().toString();
                final String userPassword = password.getText().toString();
                final String userName=username.getText().toString();

                if(TextUtils.isEmpty(userMail)){
                    Toast.makeText(getApplicationContext(),"Please enter your e-mail.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userPassword)){
                    Toast.makeText(getApplicationContext(),"Please enter your password.",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(getApplicationContext(),"Please enter your username.",Toast.LENGTH_SHORT).show();
                }
                if (userPassword.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 digits",Toast.LENGTH_SHORT).show();
                }

                //FirebaseAuth ile email,parola parametrelerini kullanarak yeni bir kullanıcı oluşturuyoruz.
                auth.createUserWithEmailAndPassword(userMail,userPassword)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //İşlem başarısız olursa kullanıcıya bir Toast mesajıyla bildiriyoruz.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Yetkilendirme Hatası",
                                            Toast.LENGTH_SHORT).show();
                                }

                                //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                                else {


                                    User user = new User(userMail, userName, userPassword);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                //display a failure message
                                            }
                                        }
                                    });




                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }

                            }
                        });

            }
        });


    }
}
