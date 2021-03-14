package com.example.mobilevendingsystem.Util;

public class Constants {
    //DATABASE details
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "vendingSystemDB";

    //TABLE details
    public static final String TABLE_USER = "user";
    public static final String TABLE_VEHICLE = "vehicle";
    public static final String TABLE_VEHICLE_ITEMS = "vehicle_items";
    public static final String TABLE_ITEM = "item";
    public static final String TABLE_LOCATION = "location";
    public static final String TABLE_SHIFT = "shift";
    public static final String TABLE_ORDER = "'order'";
    public static final String TABLE_ORDER_ITEMS = "order_items";

    //USER_TABLE details
    public static final String USER_USER_NAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_LAST_NAME = "lastname";
    public static final String USER_FIRST_NAME = "firstname";
    public static final String USER_ROLE =  "role";
    public static final String USER_PHONE = "phone";
    public static final String USER_EMAIL = "email";
    public static final String USER_STREET_NO = "street_no";
    public static final String USER_CITY = "city";
    public static final String USER_STATE = "state";
    public static final String USER_ZIP_CODE = "zip_code";

    //VEHICLE_TABLE details
    public static final String VEHICLE_VEHICLE_ID = "vehicle_id";
    public static final String VEHICLE_VEHICLE_TYPE = "vehicle_type";
    public static final String VEHICLE_STATUS = "status";

    //VEHICLE_ITEMS_TABLE details
    public static final String VEHICLE_ITEMS_VEHICLE_ID = "vehicle_id";
    public static final String VEHICLE_ITEMS_ITEM_TYPE = "item_type";
    public static final String VEHICLE_ITEMS_QUANTITY = "quantity";
    public static final String VEHICLE_ITEMS_DATE = "date";

    //SHIFT_TABLE details
    public static final String SHIFT_SHIFT_ID = "shift_id";
    public static final String SHIFT_VEHICLE_ID = "vehicle_id";
    public static final String SHIFT_LOCATION_ID = "loc_id";
    public static final String SHIFT_START_TIME = "start_time";
    public static final String SHIFT_END_TIME = "end_time";
    public static final String SHIFT_OPERATOR_ID = "operator_id";
    public static final String SHIFT_DATE = "date";

    //ORDER_TABLE details
    public static final String ORDER_ORDER_ID = "order_id";
    public static final String ORDER_VEHICLE_ID = "vehicle_id";
    public static final String ORDER_OPERATOR_ID = "operator_id";
    public static final String ORDER_LOC_ID = "loc_id";
    public static final String ORDER_STATUS = "status";
    public static final String ORDER_ORDER_DATE = "order_date";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_TOTAL_PRICE = "total_price";

    //ORDER_ITEMS_TABLE details
    public static final String ORDER_ITEMS_ORDER_ID = "order_id";
    public static final String ORDER_ITEMS_ITEM_TYPE = "item_type";
    public static final String ORDER_ITEMS_QUANTITY = "quantity";

}
