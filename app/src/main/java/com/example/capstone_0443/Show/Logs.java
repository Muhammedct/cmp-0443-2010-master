package com.example.capstone_0443.Show;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_0443.Model.History;
import com.example.capstone_0443.Model.PIR;
import com.example.capstone_0443.R;
import com.example.capstone_0443.ViewHolders.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Logs extends Fragment {

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    EditText logDay,logMonth,logYear;
    String day,month,year;
    ArrayList<String> ArrayListForDb=new ArrayList<String>();
    Button logSearch,logClear;
    History forLogDataSnapShot = new History();
    DatabaseReference dref;
    TextView txt,tarih;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //-------

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Eğer geçerli bir kullanıcı oturumu yoksa LoginActivity e geçilir.
                // Oturum kapatma işlemi yapıldığında bu sayede LoginActivity e geçilir.
                if (user == null) {
                    Log.e("LogOn", "Login olmuş user yok");

                    //startActivity(new Intent(Logs.this, LoginActivity.class));
                    //finish();
                }
            }
        };
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getUid();
        Log.e("LogIn", "User Email : ---- "+user.getEmail());


        //-------

        View view=inflater.inflate(R.layout.activity_android_history,null);
        logSearch=view.findViewById(R.id.logSearch);
        logDay=view.findViewById(R.id.logDay);
        logMonth=view.findViewById(R.id.logMonth);
        logYear=view.findViewById(R.id.logYear);
        logClear=view.findViewById(R.id.logClear);
        txt = view.findViewById(R.id.androidLogsText);
        // tarih=view.findViewById(R.id.tarih);

        dref = FirebaseDatabase.getInstance().getReference();

        txt.setText("");
        Log.d("TAG", "Before attaching the listener!");
        dref.child("Users").child(email).child("Data").child("Logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayListForDb.clear();
                txt.setText("");
                forLogDataSnapShot = dataSnapshot.getValue(History.class);
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    for (DataSnapshot datass : datas.getChildren()){
                        for (DataSnapshot datasss : datass.getChildren()) {
                            ArrayListForDb.add(datasss.getValue(History.class).getHistoryDay() + "--" + "\t" +
                                    datasss.getValue(History.class).getHistoryName() + "--" + "\t" +
                                    datasss.getValue(History.class).getStatus() + "--" + "\t" +
                                    datasss.getValue(History.class).getHistoryHour() + "--" + "\n");

                        }
                    }
                }
                Collections.reverse(ArrayListForDb);
                int ArrayNumber = ArrayListForDb.size();

                if(ArrayNumber>18 ){
                    for (int i = 0; i < 18; i++) {
                        txt.append(ArrayListForDb.get(i) + "\t");

                    }

                }
                else{
                    for (int i = 0; i < ArrayListForDb.size(); i++) {
                        txt.append(ArrayListForDb.get(i) + "\t");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        logSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayListForDb.clear(); // for clear past data
                final String x;
                day = logDay.getText().toString();
                month = logMonth.getText().toString();
                year = logYear.getText().toString();

                x = year + "-" + month + "-" + day;
                dref.child("Users").child(email).child("Logs").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        txt.setText("");
                        forLogDataSnapShot = dataSnapshot.getValue(History.class);
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            if (datas.getKey().matches(x)) {
                                for (DataSnapshot datass : datas.getChildren()) {
                                    for (DataSnapshot datasss : datass.getChildren()) {


                                        ArrayListForDb.add(datasss.getValue(History.class).getHistoryDay() + "--" + "\t" +
                                                datasss.getValue(History.class).getHistoryName() + "--" + "\t" +
                                                datasss.getValue(History.class).getStatus() + "--" + "\t" +
                                                datasss.getValue(History.class).getHistoryHour() + "--" + "\n");


                                    }
                                }
                            } else {
                                ArrayListForDb.clear();
                                txt.append("Geçersiz/kaydı olmayan tarih seçildi");

                        }

                            for (int i = 0; i < ArrayListForDb.size(); i++) {
                                txt.append(ArrayListForDb.get(i) + "\t");

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        });
        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.history, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.door:

                return true;

            case R.id.pir:

                // do more stuff
                return true;
            case R.id.temperature:
                // do more stuff
                return true;
        }

        return false;
    }





}