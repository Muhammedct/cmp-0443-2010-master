package com.example.capstone_0443.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.capstone_0443.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    private static final String TAG = " ";
    TextView loginButton,registerButton;
    private String userEmail, userPassword;
    private FirebaseAuth auth;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "PrefsFile";
    CheckBox chkBeniHatirla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password =  findViewById(R.id.password);
        email =  findViewById(R.id.email);
       loginButton = findViewById(R.id.loginButton);
        chkBeniHatirla=findViewById(R.id.chkBeniHatirla);
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

        sharedPreferences=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        getPreferencesData();


     }

    private void getPreferencesData() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(preferences.contains("pref_email")){
            String m = preferences.getString("pref_email","not found");
            email.setText(m.toString());
        }
        if(preferences.contains("user_password")){
            String p = preferences.getString("user_password","not found");
            password.setText(p.toString());
        }
        if(preferences.contains("pref_check")){
            Boolean c = preferences.getBoolean("pref_check",false);
            chkBeniHatirla.setChecked(c);
        }
    }

    private void login() {
        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString().trim();


        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(chkBeniHatirla.isChecked()){

                            Boolean boolIsChecked=chkBeniHatirla.isChecked();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("pref_email",email.getText().toString());
                            editor.putString("user_password",password.getText().toString());
                            editor.putBoolean("pref_check",boolIsChecked);
                            editor.apply();
                            Toast.makeText(getApplicationContext(),"Your profile have been saved.",Toast.LENGTH_SHORT).show();
                        }
                        else if(!chkBeniHatirla.isChecked()){

                            sharedPreferences.edit().clear().apply();
                        }
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = auth.getCurrentUser();

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            //Toast.makeText(getApplicationContext(),"başarılı",Toast.LENGTH_SHORT).show();
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

    }


/*

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
    }*/


