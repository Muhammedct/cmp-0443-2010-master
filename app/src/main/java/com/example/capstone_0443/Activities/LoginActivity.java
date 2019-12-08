package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
=======
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
>>>>>>> Stashed changes
import android.view.View;
import android.widget.EditText;
<<<<<<< Updated upstream
import android.widget.Toast;
import com.example.capstone_0443.R;

import java.security.PublicKey;
=======
import android.widget.TextView;
import android.widget.Toast;
import com.example.capstone_0443.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
>>>>>>> Stashed changes

public class LoginActivity extends AppCompatActivity {
    EditText etPass, etUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
<<<<<<< Updated upstream
        etPass =  findViewById(R.id.etPass);
        etUser =  findViewById(R.id.etUser);
=======
        password =  findViewById(R.id.password);
        email =  findViewById(R.id.email);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


     }
    private void login() {
        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString().trim();

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = auth.getCurrentUser();

<<<<<<< HEAD
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
=======
                            Intent intent = new Intent(LoginActivity.this,UserActivity.class);
>>>>>>> 337d4c16ae8acee5eacf20d56f9332cc0f256960
                            Toast.makeText(getApplicationContext(),"başarılı",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });


    }

>>>>>>> Stashed changes
    }



    public void onClick(View view) { //Butonlara tıklanınca

        SharedPreferences sharedPref = getSharedPreferences("mailPref",Context.MODE_PRIVATE);
        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences sharedPrefTEMP = getApplicationContext().getSharedPreferences("mailPref",Context.MODE_PRIVATE);



        if (view.getId() == R.id.registerButton) { // Kaydet Butonu
            String stringMail = etUser.getText().toString(); //Edittextten alınıyor
            String stringPass = etPass.getText().toString(); //Edittextten alınıyor
            //boolean isChecked = checkBox.isChecked(); //Checkbox seçili isi true değilse false döner

            if (stringMail == null || stringMail.equals("") || stringPass.equals("") || stringPass == null) { //alınan değerlerin boş olup olmaması kontrol ediliyor

                Toast.makeText(getApplicationContext(), "Tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();

            } else { //değerler boş değilse

              //  int value = Integer.parseInt(stringPass); //Alınan değer Integer'a çevriliyor
                SharedPreferences.Editor editor = sharedPref.edit(); //SharedPreferences'a kayıt eklemek için editor oluşturuyoruz
                editor.putString("stringPass", stringPass); //int değer ekleniyor
                editor.putString("stringMail", stringMail); //string değer ekleniyor
                //editor.putBoolean("isChecked",isChecked); //boolean değer ekleniyor
                editor.commit(); //Kayıt
                Toast.makeText(getApplicationContext(), "Kayıt Yapıldı.", Toast.LENGTH_LONG).show();

            }
        } else { //Kayıtlı verileri getir butonu

            //kaydedilen veriler kaydedilen key ile geri çekiliyor.
            //Eğer o key ile eşlesen bir değer yoksa default  value cekilir
            //örneğin "stringValue" değeri ile bir kayıt yoksa savedString'e değer olarak "Kayıt yok" atanacak
           String savedMail  = sharedPref.getString("stringMail", "Kayıt Yok");
            String savedPass = sharedPref.getString("stringPass", "Kayıt Yok");
           // Boolean savedChecked = sharedPref.getBoolean("isChecked", false);

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("CAPSTONE0443");
            if (savedMail.equals("Kayıt Yok")) {
                builder.setMessage("Önce Kayıt Yapınız");
            } else { //Kayıtlı değerler yazdırılıyor
                builder.setMessage("Kayıtlı User : " + savedMail + "\nKayıtlı pass: " + savedPass );
            }

            builder.setNeutralButton("TAMAM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            builder.show();
        }
    }
}

