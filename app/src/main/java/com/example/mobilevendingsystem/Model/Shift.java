package com.example.mobilevendingsystem.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shift {
    private String shiftId;
    private String vehicleId;
    private String locationName;
    private String startTime;
    private String endTime;
    private String operatorId;
    private String date;

    public Shift(String shiftId, String vehicleId, String locationName, String startTime, String endTime, String operatorId, String date) {
        this.shiftId = shiftId;
        this.vehicleId = vehicleId;
        this.locationName = locationName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.operatorId = operatorId;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Shift(String vehicleId, String locationName, String startTime, String endTime, String operatorId) {
        this.vehicleId = vehicleId;
        this.locationName = locationName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.operatorId = operatorId;
    }

    public Shift() {
    }

    public Shift(String shiftId, String vehicleId, String locationName, String startTime, String endTime, String operatorId) {
        this.shiftId = shiftId;
        this.vehicleId = vehicleId;
        this.locationName = locationName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.operatorId = operatorId;
    }

    public String getShiftId() {
        return shiftId;
    }

    public Date getTimeStartTime() throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date d = df.parse(startTime);
        return d;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStartTime() {
        return startTime;
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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
