package com.example.mobilevendingsystem.Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cart implements Comparable<Cart>, Serializable {
    private String vehicleId;
    private String vehicleStatus;
    private String location;
    private String startTime;
    private String endTime;

    public Cart(String vehicleId, String vehicleStatus, String location, String startTime, String endTime) {
        this.vehicleId = vehicleId;
        this.vehicleStatus = vehicleStatus;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Cart() {
    }

    public Cart(String vehicleId, String location, String startTime, String endTime) {
        this.vehicleId = vehicleId;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {

        return startTime;
    }

    public Date getTimeStartTime() throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date d = df.parse(startTime);
        return d;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    @Override
    public int compareTo(Cart o) {
        return this.vehicleId.compareTo(o.vehicleId);
    }
}

