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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class ActionMainActivity extends AppCompatActivity {

    EditText nameEditText,phoneEditText,emailEditText,latitudeEditText,longitudeEditText;
    //Storing User's Current Location
    Location presentLocation;
    //Creating instances
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    //For getting UserLocation
    LocationManager locationManager;
    LocationListener locationListener;

    FirebaseAuth mAuth;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1  &&(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)   ){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
            }

        }
    }

    public void getLocation(final View view){

        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                presentLocation=location;

                DecimalFormat df=new DecimalFormat("###.###");

                latitudeEditText.setText("latitude : "+String.valueOf(df.format(presentLocation.getLatitude())));
                longitudeEditText.setText("Longitude : "+String.valueOf(df.format(presentLocation.getLongitude())));

                //view.invalidate();

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
        }

    }


    public void saveClicked(View view){
        String uname=nameEditText.getText().toString();
        String phNum=phoneEditText.getText().toString();
        String email=emailEditText.getText().toString();
        Location location=presentLocation;
        double latitude=presentLocation.getLatitude();
        double longitude=presentLocation.getLongitude();

        //Location userLocation=presentLocation;

        //User user=new User(uname,phNum,email,userLocation);

        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("organisation");

        //reference.setValue("Tirumal");
        //FirebaseDatabase.getInstance().getReference().child("Organisation").push().setValue(user);



        UserHelper userHelperClass=new UserHelper(mAuth.getCurrentUser().getUid(),uname,phNum,email,location,latitude,longitude,0);
        //reference.setValue(userHelperClass);

        //DatabaseReference ref=reference.push();
        //String empId=ref.getKey();
        //ref.setValue(userHelperClass);
        reference.child(mAuth.getCurrentUser().getUid()).setValue(userHelperClass);



        Intent intent=new Intent(getApplicationContext(),WorkingLocationActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_main);

        nameEditText=findViewById(R.id.UserNameEditText);
        phoneEditText=findViewById(R.id.UserPhoneEditText);
        emailEditText=findViewById(R.id.UserEmailEditText);
        latitudeEditText=findViewById(R.id.latitudeEditText);
        longitudeEditText=findViewById(R.id.longitudeEditText);

        mAuth=FirebaseAuth.getInstance();
    }
}