package com.example.miniproject;

import android.location.Location;

import java.sql.DataTruncation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserHelper {

    String name,phone,email,id;
    long attendance;
    Location workLocation;
    double latitude,longitude;
    String locationLogs="\n";

    Location currentWorkingLocation;
   // List<Location>locationLogs;


    public UserHelper() {
    }

    public UserHelper(String id, String name, String phone, String email, Location workLocation,double latitude,double longitude ,long attendance) {
        this.id=id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.workLocation=workLocation;
        this.latitude=latitude;
        this.longitude=longitude;
        this.attendance=attendance;

        this.currentWorkingLocation=this.workLocation;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.locationLogs+=dtf.format(now)+" ";
        this.locationLogs=String.valueOf(workLocation.getLatitude())+" "+String.valueOf(workLocation.getLongitude())+"::";

    }

    public String getLocationLogs() {
        return locationLogs;
    }

    public void setLocationLogs(String locationLogs) {
        this.locationLogs = locationLogs;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAttendance() {
        return attendance;
    }

    public void setAttendance(long attendance) {
        this.attendance = attendance;
    }

    public Location getCurrentWorkingLocation() {
        return currentWorkingLocation;
    }

    public void setCurrentWorkingLocation(Location currentWorkingLocation) {
        this.currentWorkingLocation = currentWorkingLocation;
        //this.locationLogs.add(currentWorkingLocation);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(Location workLocation) {
        this.workLocation = workLocation;
    }
}
