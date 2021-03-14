package com.example.mobilevendingsystem.Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String vehicleId;
    private List<Items> itemList;
    private String operatorName;
    private String Status;
    private Double totalPrice;
    private String orderDate;
    private String userName;

    public String getLocName() {
        return locName;
    }

    public Order(String orderId, String vehicleId, List<Items> itemList, String operatorName, String status, Double totalPrice, String orderDate, String userName, String locName) {
        this.orderId = orderId;
        this.vehicleId = vehicleId;
        this.itemList = itemList;
        this.operatorName = operatorName;
        Status = status;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.userName = userName;
        this.locName = locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    private String locName;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Order(String orderId, String vehicleId, List<Items> itemList, String operatorName, String status, Double totalPrice) {
        this.orderId = orderId;
        this.vehicleId = vehicleId;
        this.itemList = itemList;
        this.operatorName = operatorName;
        Status = status;
        this.totalPrice = totalPrice;
    }

    public Order(String vehicleId, List<Items> itemList, String operatorName, String status, Double totalPrice) {
        this.vehicleId = vehicleId;
        this.itemList = itemList;
        this.operatorName = operatorName;
        Status = status;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public List<Items> getItemList() {
        return itemList;
    }

    public void setItemList(List<Items> itemList) {
        this.itemList = itemList;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getStatus() {
        return Status;
    }

    public Date getDateorderDate() throws ParseException {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM");
            Date d = df.parse(orderDate);
            return d;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

