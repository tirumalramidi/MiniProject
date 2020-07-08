package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class lastActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth mAuth;
    long attendance;
    String locationlogs;

    public void displayAttendance(View view){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                attendance=(long) dataSnapshot.child("organisation").child(mAuth.getCurrentUser().getUid()).child("attendance").getValue();
                TextView textView=findViewById(R.id.textView);
                textView.setText("Attendance : "+attendance);

                locationlogs=(String) dataSnapshot.child("organisation").child(mAuth.getCurrentUser().getUid()).child("locationLogs").getValue();

                TextView displayTextView=findViewById(R.id.displaylocationTextView);
                displayTextView.setText(locationlogs.substring(4));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

    }
}