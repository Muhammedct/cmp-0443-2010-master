package com.example.capstone_0443.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.example.capstone_0443.R;

import java.text.BreakIterator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public  class ViewHolder extends RecyclerView.ViewHolder{

    public  TextView name;
    public  TextView status;
    public  TextView day;
    public  TextView hour;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        status=itemView.findViewById(R.id.status);
        day=itemView.findViewById(R.id.day);
        hour=itemView.findViewById(R.id.hour);

    }
}