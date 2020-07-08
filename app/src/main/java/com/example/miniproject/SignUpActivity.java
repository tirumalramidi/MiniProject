package com.example.miniproject;


import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class SignUpActivity extends AppCompatActivity {



    FirebaseAuth mAuth;
    EditText emailEditText,passwordEditText,reTypedPasswordEditText;

    public void accountCreation(View view){

        if(passwordEditText.getText().toString().equals(reTypedPasswordEditText.getText().toString())){

            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(getApplicationContext(),fingerprintAuthActivity.class);
                                intent.putExtra("login","signup");
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"Login failed Try Again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else{
            Toast.makeText(getApplicationContext(),"please enter same pw in both fields",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        emailEditText=findViewById(R.id.UserEmailEditText);
        passwordEditText=findViewById(R.id.pwEditText);
        reTypedPasswordEditText=findViewById(R.id.reEnterPwEditText);


        mAuth=FirebaseAuth.getInstance();


    }
}