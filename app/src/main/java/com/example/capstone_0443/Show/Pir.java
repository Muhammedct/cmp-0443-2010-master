package com.example.capstone_0443.Show;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Pir extends Fragment {

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    DatabaseReference dref;
    private RecyclerView mRecyclerView;
    String name,hour,status,day;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view=inflater.inflate(R.layout.activity_android_pir,null);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dref=FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("Data").child("PIRs");
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options= new FirebaseRecyclerOptions.Builder<com.example.capstone_0443.Model.PIR>().setQuery(dref,com.example.capstone_0443.Model.PIR.class).build();

        FirebaseRecyclerAdapter<com.example.capstone_0443.Model.PIR, ViewHolder> adapter = new FirebaseRecyclerAdapter<com.example.capstone_0443.Model.PIR, ViewHolder>(options) {



            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show,parent,false);

                ViewHolder viewHolder=new ViewHolder(view);
                return viewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder ViewHolder, int i, @NonNull com.example.capstone_0443.Model.PIR pir) {

                String doorID= getRef(i).getKey();
                dref.child(doorID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        name = dataSnapshot.child("pirName").getValue().toString();
                        hour = dataSnapshot.child("pirHour").getValue().toString();
                        day = dataSnapshot.child("pirDay").getValue().toString();
                        status = dataSnapshot.child("pirStatus").getValue().toString();

                        ViewHolder.name.setText(name);
                        ViewHolder.status.setText(status);
                        ViewHolder.day.setText(day);
                        ViewHolder.hour.setText(hour);





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
