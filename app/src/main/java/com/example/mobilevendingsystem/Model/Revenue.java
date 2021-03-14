package com.example.mobilevendingsystem.Model;

public class Revenue {
    public Revenue(String vehicle_id, String location, String revenue) {
        this.vehicle_id = vehicle_id;
        this.location = location;
        this.revenue = revenue;
    }

    private String vehicle_id;
    private String location;
    private String revenue;

    public Revenue(String vehicle_id, String revenue) {
        this.vehicle_id = vehicle_id;
//        this.location = location;
        this.revenue = revenue;
    }
    public String getVehicle_id() {
        return vehicle_id;
    }
    public String getLocation() {
        return location;
    }
    public String getRevenue() {
        return revenue;
    }

    public double getDoubleRevenue() {return Double.valueOf(revenue);}

}
