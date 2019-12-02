package com.example.capstone_0443.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.capstone_0443.R;

import java.security.PublicKey;

public class LoginActivity extends AppCompatActivity {
    EditText etPass, etUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        etPass =  findViewById(R.id.etPass);
        etUser =  findViewById(R.id.etUser);
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

