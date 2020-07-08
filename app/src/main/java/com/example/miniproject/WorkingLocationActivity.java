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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WorkingLocationActivity extends AppCompatActivity {

    public static LocationManager locationManager;
    public static LocationListener locationListener;


    Location currentWorkingLocation;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String locationlogs="";

    EditText currentAttendanceEditText,updatedAttendanceEditText,workLatitudeEditText,workLongitudeEditText,currentLatitudeEditText,currentLongitudeEditText;

    long EmpAttendance;
    double latitude,longitude;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1  &&(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)   ){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,locationListener);
            }

        }
    }

    public void getCurrentWorkingLocation(final View view){

        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentWorkingLocation=location;
                //Toast.makeText(getApplicationContext(),"Location fetched",Toast.LENGTH_SHORT).show();
                currentLatitudeEditText.setText("lat : "+String.valueOf(currentWorkingLocation.getLatitude()));
                currentLongitudeEditText.setText("Longi : "+String.valueOf(currentWorkingLocation.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,locationListener);
        }

    }


    public void updateAttendance(View view){

    //updatedAttendanceEditText.setText("Working");
        updateInfo();
    }



    public void updateInfo(){

        DistanceHelper dist=new DistanceHelper();

        double distance=dist.distance(latitude,currentWorkingLocation.getLatitude(),longitude,currentWorkingLocation.getLongitude());

        if(Math.abs(distance)<=0.10){
            EmpAttendance++;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        locationlogs+=dtf.format(now)+" ";

        locationlogs+=String.valueOf(currentWorkingLocation.getLatitude())+" "+String.valueOf(currentWorkingLocation.getLongitude())+"::";

        reference.child("organisation").child(mAuth.getCurrentUser().getUid()).child("currentWorkingLocation").setValue(currentWorkingLocation);
        reference.child("organisation").child(mAuth.getCurrentUser().getUid()).child("attendance").setValue(EmpAttendance);
        reference.child("organisation").child(mAuth.getCurrentUser().getUid()).child("locationLogs").setValue(locationlogs);

        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
        updatedAttendanceEditText.setText("UpdatedAttendance : "+String.valueOf(EmpAttendance));
        //mAuth.signOut();
        //finish();
        Intent intent=new Intent(this,lastActivity.class);
        startActivity(intent);

    }

    public void initialize(){
        mAuth=FirebaseAuth.getInstance();
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference();

        currentAttendanceEditText=findViewById(R.id.currentWorkAttendanceEditText);
        workLatitudeEditText=findViewById(R.id.workLatitudeEditText);
        workLongitudeEditText=findViewById(R.id.workLongitudeEditText);

        currentLatitudeEditText=findViewById(R.id.currentLatitudeEditText);
        currentLongitudeEditText=findViewById(R.id.currentLongitudeEditText);
        updatedAttendanceEditText=findViewById(R.id.updatedWorkAttendanceEditText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_location);

        initialize();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    EmpAttendance= (long) dataSnapshot.child("organisation").child(mAuth.getCurrentUser().getUid()).child("attendance").getValue();
                    latitude= (double) dataSnapshot.child("organisation").child(mAuth.getCurrentUser().getUid()).child("latitude").getValue();
                    longitude= (double)dataSnapshot.child("organisation").child(mAuth.getCurrentUser().getUid()).child("longitude").getValue();
                    locationlogs= (String) dataSnapshot.child("organisation").child(mAuth.getCurrentUser().getUid()).child("locationLogs").getValue();

                    workLatitudeEditText.setText("lat : "+latitude);
                    workLongitudeEditText.setText("longi : "+longitude);

                    currentAttendanceEditText.setText("currentAttendance : "+String.valueOf(EmpAttendance));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}