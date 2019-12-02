package com.example.capstone_0443.Show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstone_0443.Model.PIR;
import com.example.capstone_0443.R;
import com.example.capstone_0443.ViewHolders.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class Temperature extends Fragment {
    DatabaseReference dref;
    String name,hour,temp,day;
    private RecyclerView mRecyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_android_temps,null);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dref=FirebaseDatabase.getInstance().getReference().child("Temps");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options= new FirebaseRecyclerOptions.Builder<com.example.capstone_0443.Model.Temperature>().setQuery(dref,com.example.capstone_0443.Model.Temperature.class).build();

        FirebaseRecyclerAdapter<com.example.capstone_0443.Model.Temperature, ViewHolder> adapter = new FirebaseRecyclerAdapter<com.example.capstone_0443.Model.Temperature, ViewHolder>(options) {



            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show,parent,false);

                ViewHolder viewHolder=new ViewHolder(view);
                return viewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder ViewHolder, int i, @NonNull com.example.capstone_0443.Model.Temperature temperature) {

                String doorID= getRef(i).getKey();
                dref.child(doorID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        name = dataSnapshot.child("tempName").getValue().toString();
                        hour = dataSnapshot.child("tempHour").getValue().toString();
                        day = dataSnapshot.child("tempDay").getValue().toString();
                        temp = dataSnapshot.child("temp").getValue().toString();

                        ViewHolder.name.setText(name);
                        ViewHolder.status.setText(temp);
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



