package com.example.mobilevendingsystem.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.Items;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.Model.Revenue;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.Util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    //CREATE TABLE QUERIES
    private static final String CREATE_TABLE_USER = "CREATE TABLE "+ Constants.TABLE_USER+"("+
            Constants.USER_USER_NAME+" TEXT PRIMARY KEY,"+Constants.USER_PASSWORD+" TEXT,"+Constants.USER_LAST_NAME+" TEXT,"+Constants.USER_FIRST_NAME+" TEXT,"+
            Constants.USER_ROLE+" TEXT,"+Constants.USER_PHONE+" TEXT,"+Constants.USER_EMAIL+" TEXT,"+Constants.USER_STREET_NO+" TEXT,"
            +Constants.USER_CITY+" TEXT,"+Constants.USER_STATE+" TEXT,"+Constants.USER_ZIP_CODE+" TEXT"+")";

    private static final String CREATE_TABLE_VEHICLE ="CREATE TABLE "+ Constants.TABLE_VEHICLE+"("+
            Constants.VEHICLE_VEHICLE_ID+" TEXT PRIMARY KEY,"+Constants.VEHICLE_VEHICLE_TYPE+" TEXT,"
            +Constants.VEHICLE_STATUS+" TEXT DEFAULT \"AVAILABLE\" NOT NULL"+")";

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_VEHICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_VEHICLE);
        onCreate(db);
    }


    //Register user
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Constants.USER_USER_NAME,user.getUsername());
        value.put(Constants.USER_PASSWORD,user.getPassword());
        value.put(Constants.USER_LAST_NAME,user.getLastName());
        value.put(Constants.USER_FIRST_NAME,user.getFirstName());
        value.put(Constants.USER_ROLE, user.getRole());
        value.put(Constants.USER_PHONE,user.getPhone());
        value.put(Constants.USER_EMAIL,user.getEmail());
        value.put(Constants.USER_STREET_NO,user.getStreetNo());
        value.put(Constants.USER_CITY,user.getCity());
        value.put(Constants.USER_STATE,user.getState());
        value.put(Constants.USER_ZIP_CODE,user.getZipCode());

        db.insert(Constants.TABLE_USER,null,value);
        db.close();
    }

    //get user
    public User getUser(String userName, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        User user=null;
        Cursor cursor = db.query(Constants.TABLE_USER,new String[] {Constants.USER_USER_NAME,Constants.USER_PASSWORD,Constants.USER_LAST_NAME,
                Constants.USER_FIRST_NAME,Constants.USER_ROLE,Constants.USER_PHONE,Constants.USER_EMAIL,Constants.USER_STREET_NO,Constants.USER_CITY,
                Constants.USER_STATE,Constants.USER_ZIP_CODE},
                Constants.USER_USER_NAME+"=? AND "+Constants.USER_PASSWORD+"=?",
                new String[] {userName, password},null,null,null);

        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),
                    cursor.getString(8),cursor.getString(9),cursor.getString(10));
            cursor.close();
            db.close();
        }
        return user;
    }

    //Update user
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Constants.USER_USER_NAME, user.getUsername());
        value.put(Constants.USER_PASSWORD, user.getPassword());
        value.put(Constants.USER_LAST_NAME, user.getLastName());
        value.put(Constants.USER_FIRST_NAME, user.getFirstName());
        value.put(Constants.USER_ROLE, user.getRole());
        value.put(Constants.USER_PHONE, user.getPhone());
        value.put(Constants.USER_EMAIL, user.getEmail());
        value.put(Constants.USER_STREET_NO, user.getStreetNo());
        value.put(Constants.USER_CITY, user.getCity());
        value.put(Constants.USER_STATE, user.getState());
        value.put(Constants.USER_ZIP_CODE, user.getZipCode());

        db.update(Constants.TABLE_USER, value, "username=?" , new String[] { user.getUsername() });
        db.close();
    }

    //Get Vehicle list
    public List<Cart> getVehicles() {
        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        List<Cart> cartList = new ArrayList<Cart>();
        Cursor cursor = db.rawQuery("select vehicle_id,loc_id,start_time,end_time,operator_id from shift where date(date)=? order by time(start_time), loc_id",
                new String[]{date});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleId = cursor.getString(0);
                String locId = cursor.getString(1);
                String startTime = cursor.getString(2);
                String endTime = cursor.getString(3);
                String operator_id = cursor.getString(4);
                Cart cart = new Cart(vehicleId, locId, startTime, endTime);
                cartList.add(cart);
                cursor.moveToNext();
            }

        }
        return cartList;
    }

    //Get Vehicle items
    public List<Items> getVehicleItems(Cart vehicle) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> itemList = new ArrayList<Items>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        Cursor cursor = db.rawQuery("select * from vehicle_items where vehicle_id=? AND date(date)=?" +
                " order by item_type", new String[] { vehicle.getVehicleId(), date });
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemName = cursor.getString(1);
                String quantity = cursor.getString(2);
                Cursor cursor1 = db.rawQuery("select * from item where item_type=?", new String[] { itemName });
                cursor1.moveToFirst();
                String cost = cursor1.getString(1);
                Items items = new Items(itemName,quantity,cost);
                itemList.add(items);
                cursor.moveToNext();
            }

        }
        return itemList;
    }

    //Get vehicle name
    public List<String> getVehicleName(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> vehicleList = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select * from vehicle", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleName = cursor.getString(0);
                vehicleList.add(vehicleName);
                cursor.moveToNext();
            }
        }
        return vehicleList;
    }

    //Get Operator name
    public List<String> getOperatorName(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> vehicleList = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select username from user where role='Operator'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleName = cursor.getString(0);
                vehicleList.add(vehicleName);
                cursor.moveToNext();
            }
        }
        return vehicleList;
    }

    //Get Location name
    public List<String> getLocationName(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> vehicleList = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select loc_name from location", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleName = cursor.getString(0);
                vehicleList.add(vehicleName);
                cursor.moveToNext();
            }
        }
        return vehicleList;
    }

    //Delete shift
    public void deleteShift(int shiftId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_SHIFT, Constants.SHIFT_SHIFT_ID + " = ?",
                new String[] {String.valueOf(shiftId)});

        db.close();
    }

    //Get shift list
    public List<Shift> getShifts() {
        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        String date = sdf.format(dt);


        List<Shift> shiftList = new ArrayList<Shift>();
        Cursor cursor = db.rawQuery("select * from shift where date(date)=?", new String[]{date});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String shiftId = cursor.getString(0);
                String vehicleId = cursor.getString(1);
                String locId = cursor.getString(2);
                String startTime = cursor.getString(3);
                String endTime = cursor.getString(4);
                String operatorId = cursor.getString(5);
//                Cursor cursor1 = db.rawQuery("select loc_name from location where loc_id=?",new String[] { locId });
//                cursor1.moveToFirst();
//                String locationName = cursor1.getString(0);
                Shift shift = new Shift(shiftId,vehicleId,locId,startTime,endTime,operatorId);
                shiftList.add(shift);
                cursor.moveToNext();
            }

        }
        db.close();
        return shiftList;
    }

    //Add shift
    public void addShift(Shift shift) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String date = sdf.format(new Date());

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        String date = sdf.format(dt);
        shift.setDate(date);

        ContentValues values = new ContentValues();
        values.put(Constants.SHIFT_VEHICLE_ID, shift.getVehicleId());
        values.put(Constants.SHIFT_LOCATION_ID, shift.getLocationName());
        values.put(Constants.SHIFT_START_TIME, shift.getStartTime());
        values.put(Constants.SHIFT_END_TIME,shift.getEndTime());
        values.put(Constants.SHIFT_OPERATOR_ID,shift.getOperatorId());
        values.put(Constants.SHIFT_DATE,shift.getDate());

        //Insert the row
        db.insert(Constants.TABLE_SHIFT, null, values);

        String items[] ={"Drinks", "Sandwiches", "Snacks"};
        Integer truckQuantity[] = {50, 35, 40};
        Integer cartQuantity[] = {30, 5, 30};
        for(int i=0; i<3;i++)
        {
            ContentValues values1 = new ContentValues();
            values1.put(Constants.VEHICLE_ITEMS_VEHICLE_ID, shift.getVehicleId());
            values1.put(Constants.VEHICLE_ITEMS_ITEM_TYPE, items[i] );
            if(shift.getVehicleId().contains("Truck"))
                values1.put(Constants.VEHICLE_ITEMS_QUANTITY, truckQuantity[i]);
            else
                values1.put(Constants.VEHICLE_ITEMS_QUANTITY, cartQuantity[i]);
            values1.put(Constants.VEHICLE_ITEMS_DATE, shift.getDate());

            db.insert(Constants.TABLE_VEHICLE_ITEMS, null, values1);
        }

    }

    //Update shift
    public void updateShift(Shift shift) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.SHIFT_VEHICLE_ID, shift.getVehicleId());
        values.put(Constants.SHIFT_LOCATION_ID, shift.getLocationName());
        values.put(Constants.SHIFT_START_TIME, shift.getStartTime());
        values.put(Constants.SHIFT_END_TIME, shift.getEndTime());
        values.put(Constants.SHIFT_OPERATOR_ID, shift.getOperatorId());

        db.update(Constants.TABLE_SHIFT, values, Constants.SHIFT_SHIFT_ID + "=?", new String[] { String.valueOf(shift.getShiftId())} );
        db.close();
    }

    //Get Cart list
    public List<Cart> getCart() {
        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        List<Cart> cartList = new ArrayList<Cart>();
        Cursor cursor = db.rawQuery("select vehicle_id,loc_id,start_time,end_time,operator_id from shift where vehicle_id like 'Cart%'" +
                " AND date(date)=? order by time(start_time), loc_id",new String[]{date});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleId = cursor.getString(0);
                String locId = cursor.getString(1);
                String startTime = cursor.getString(2);
                String endTime = cursor.getString(3);
                String operatorId = cursor.getString(4);
                Cart cart = new Cart(vehicleId, locId, startTime, endTime);
                cartList.add(cart);
                cursor.moveToNext();
            }

        }
        return cartList;
    }

    //Get Truck list
    public List<Cart> getTruck() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Cart> cartList = new ArrayList<Cart>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        Cursor cursor = db.rawQuery("select vehicle_id,loc_id,start_time,end_time,operator_id from shift where vehicle_id like 'Truck%'" +
                " AND date(date)=? order by time(start_time), loc_id",new String[]{date});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleId = cursor.getString(0);
                String locId = cursor.getString(1);
                String startTime = cursor.getString(2);
                String endTime = cursor.getString(3);
                String operatorId = cursor.getString(4);
                Cart cart = new Cart(vehicleId, locId, startTime, endTime);
                cartList.add(cart);
                cursor.moveToNext();
            }

        }
        return cartList;
    }
    //Get assigned operator to particular vehicleId
    public String getAssignedOperatorName(String vehicleId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select operator_id from shift where vehicle_id=?", new String[]{vehicleId});
        String operatorName="";
        if (cursor.moveToFirst()) {
            operatorName =cursor.getString(0);
        }
        return operatorName;
    }

    //Insert Order
    public Order addOrder(Order order, Cart vehicle, User user){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date());


        ContentValues value = new ContentValues();
//        value.put(Constants.ORDER_ORDER_ID,order.getOrderId());
        value.put(Constants.ORDER_VEHICLE_ID,order.getVehicleId());
        value.put(Constants.ORDER_OPERATOR_ID,order.getOperatorName());
        value.put(Constants.ORDER_LOC_ID,vehicle.getLocation());
        order.setLocName(vehicle.getLocation());
        value.put(Constants.ORDER_STATUS, order.getStatus());
        value.put(Constants.ORDER_ORDER_DATE,date);
        value.put(Constants.ORDER_USER_ID,user.getUsername());
        order.setUserName(user.getUsername());
        value.put(Constants.ORDER_TOTAL_PRICE,order.getTotalPrice());


        db.insert(Constants.TABLE_ORDER,null,value);

        Cursor cursor = db.rawQuery("select order_id from 'order' order by datetime(order_date) desc , order_id DESC LIMIT 1", null);
        String orderId="";
        if (cursor.moveToFirst()) {
            orderId =cursor.getString(0);
        }
        order.setOrderId(orderId);
        List<Items> itemsList = order.getItemList();
        for (Items item : itemsList){
            ContentValues value1 = new ContentValues();
            value1.put(Constants.ORDER_ITEMS_ORDER_ID,orderId);
            value1.put(Constants.ORDER_ITEMS_ITEM_TYPE,item.getItemName());
            value1.put(Constants.ORDER_ITEMS_QUANTITY,item.getQuantity());

            db.insert(Constants.TABLE_ORDER_ITEMS,null,value1);
        }

        updqateVehicleItems(order);

        db.close();
        return order;
    }

//    Update Items
    public void updqateVehicleItems(Order order)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Items> itemList = order.getItemList();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        String updateQuery ="UPDATE "+Constants.TABLE_VEHICLE_ITEMS+" SET "+Constants.VEHICLE_ITEMS_QUANTITY+" = "+Constants.VEHICLE_ITEMS_QUANTITY+"-"+
            "?"+"WHERE vehicle_id ="+order.getVehicleId()+" AND item_type =? AND date(date)="+date;




        for (Items items:itemList)
        {
//            Cursor c= db.rawQuery(updateQuery, new String[]{items.getQuantity(),order.getVehicleId(),items.getItemName(),date});
//            c.moveToFirst();
//            c.close();
            db.execSQL("UPDATE "+Constants.TABLE_VEHICLE_ITEMS+" SET "+Constants.VEHICLE_ITEMS_QUANTITY+" = "+Constants.VEHICLE_ITEMS_QUANTITY+"-"+
                    items.getQuantity()+" WHERE vehicle_id ='"+order.getVehicleId()+"' AND item_type ='"+items.getItemName()+"' AND date(date)='"+date+"'");
        }



//        order.setStatus(status);
//
//        db.update(Constants.TABLE_VEHICLE_ITEMS, values, Constants.ORDER_ORDER_ID + "=?", new String[] { String.valueOf(order.getOrderId())} );
        db.close();
    }

    //Update Order status
    public void updqateOrderStatus(Order order, String status)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.ORDER_STATUS, status);

        order.setStatus(status);

        db.update(Constants.TABLE_ORDER, values, Constants.ORDER_ORDER_ID + "=?", new String[] { String.valueOf(order.getOrderId())} );
        db.close();
    }

    //Get Order details
    public List<Order> getOrderHistory(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Order> orderList = new ArrayList<Order>();
        Cursor cursor = db.rawQuery("select * from 'order' where user_id=? order by datetime(order_date) DESC , order_id DESC",new String[]{user.getUsername()});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String orderId = cursor.getString(0);
                String vehicleId = cursor.getString(1);
                String operatorId = cursor.getString(2);
                String locationName = cursor.getString(3);
                String status = cursor.getString(4);
                String orderDate = cursor.getString(5);
                String userId = cursor.getString(6);
                Double totalPrice = Double.valueOf(cursor.getString(7));
                List<Items> itemList = new ArrayList<>();

                Cursor cursor1 = db.rawQuery("select * from order_items where order_id=?",new String[] { orderId });
                if(cursor1.moveToFirst()) {
                    while (!cursor1.isAfterLast()) {
                        String itemName = cursor1.getString(1);
                        String quantity = cursor1.getString(2);
                        Items items = new Items(itemName,quantity,"");
                        itemList.add(items);
                        cursor1.moveToNext();
                    }

                }
                Order order = new Order(orderId,vehicleId,itemList,operatorId,status,totalPrice,orderDate,userId,locationName);
                orderList.add(order);
                cursor.moveToNext();
            }

        }
        db.close();
        return orderList;
    }

    //-----------------------------------Added by Ali---------------------------------
    //Get shift list by operator
    public List<Shift> getOperatorShifts(User operator) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Shift> shiftList = new ArrayList<Shift>();
        Cursor cursor = db.rawQuery("select rowid, vehicle_id, loc_id, start_time, end_time, operator_id from shift where operator_id=?", new String[]{operator.getUsername()} );
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String shiftId = cursor.getString(0);
                String vehicleId = cursor.getString(1);
                String locId = cursor.getString(2);
                String startTime = cursor.getString(3);
                String endTime = cursor.getString(4);
                String operatorId = cursor.getString(5);
//                Cursor cursor1 = db.rawQuery("select loc_name from location where loc_id=?",new String[] { locId });
//                cursor1.moveToFirst();
//                String locationName = cursor1.getString(0);
                Shift shift = new Shift(shiftId,vehicleId,locId,startTime,endTime,operatorId);
                shiftList.add(shift);
                cursor.moveToNext();
            }

        }
        db.close();
        return shiftList;
    }


    public List<Revenue> getOperatorsVehicleRevenue(User operator) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Revenue> ovr = new ArrayList<Revenue>();
        Cursor cursor = db.rawQuery("SELECT" +
                "   vehicle_id," +
                "   SUM(CAST (total_price AS REAL)) AS revenue, loc_id" +
                " FROM 'order'" +
                " WHERE operator_id=?"+
                " GROUP by vehicle_id"+
                " ORDER BY revenue;", new String[]{operator.getUsername()} );

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleid = cursor.getString(0);
                String rev = cursor.getString(1);
                String loc = cursor.getString(2);
                Revenue revenue = new Revenue(vehicleid,loc,rev);
                ovr.add(revenue);
                cursor.moveToNext();
            }
        }
        db.close();
        return ovr;
    }

    public List<Revenue> getTotalVehicleRevenue(User operator) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Revenue> ovr = new ArrayList<Revenue>();
        Cursor cursor = db.rawQuery("SELECT" +
                "   vehicle_id," +
                "   SUM(CAST (total_price AS REAL)) AS revenue, loc_id" +
                " FROM 'order'" +
                " GROUP by vehicle_id"+
                " ORDER BY revenue;", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String vehicleid = cursor.getString(0);
                String rev = cursor.getString(1);
                String loc = cursor.getString(2);
                Revenue revenue = new Revenue(vehicleid,loc,rev);
                ovr.add(revenue);
                cursor.moveToNext();
            }
        }
        db.close();
        return ovr;
    }

    public Cart getVehiclefromID(String vId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
//        List<Cart> cartList = new ArrayList<Cart>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        Cart cart=null;
        Cursor cursor = db.rawQuery("select vehicle_id,loc_id,start_time,end_time,operator_id from shift where vehicle_id =?" +
                " AND date(date)=? order by time(start_time), loc_id",new String[]{vId,date});
        if (cursor.moveToFirst()) {
                String vehicleId = cursor.getString(0);
                String locId = cursor.getString(1);
                String startTime = cursor.getString(2);
                String endTime = cursor.getString(3);
                String operatorId = cursor.getString(4);
                cart = new Cart(vehicleId, locId, startTime, endTime);
//                cartList.add(cart);
                cursor.moveToNext();
            }
        return cart;
    }


//    public void addOrder(Order order) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.ORDER_ORDER_ID, order.getOrderId());
//        values.put(Constants.ORDER_VEHICLE_ID, order.getVehicleId());
//        values.put(Constants.ORDER_PRICE, order.getPrice());
//        values.put(Constants.ORDER_ITEM_LIST,order.getItemList());
//        values.put(Constants.ORDER_OPERATOR_NAME,order.getOperatorName());
//        values.put(Constants.ORDER_STATUS,order.getStatus());
//
//        //Insert the row
//        db.insert(Constants.TABLE_ORDER, null, values);
//    }

//----------------------------------------------------------------------------
}
