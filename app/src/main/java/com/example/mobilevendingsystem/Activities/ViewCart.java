package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Adapter.CartAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewCart extends AppCompatActivity {

    private RecyclerView cartView;
    private  RecyclerView.Adapter cartAdapter;
    private List<Cart> cartList;
    private DatabaseHandler db;
    private Button sortByStartTime;
    private Button sortByLocationName;
    private Button logoutButton;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cart);
        //setTitle("Cart List");
//        setTitle("Truck List");
//        setTitle("View Vehicles");
        db = new DatabaseHandler(this);
        cartView = findViewById(R.id.cartView);
        sortByStartTime = findViewById(R.id.sortByStartTimeBtn);
        sortByLocationName = findViewById(R.id.sortByLocationBtn);
        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
        final String vehicleType = (String) getIntent().getSerializableExtra("vehicleType");
//        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");

//        sortByStartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cartView.setHasFixedSize(true);
//                cartView.setLayoutManager(new LinearLayoutManager(this));
//
//                cartList = db.getVehicles();
//                cartAdapter = new CartAdapter(this, cartList);
//                cartView.setAdapter(cartAdapter);
//            }
//        });
//        Collections.sort(cartList, new Comparator<Cart>() {
//            @Override
//            public int compare(Cart o1, Cart o2) {
//                return 0;
//            }
//        });
        cartView.setHasFixedSize(true);
        cartView.setLayoutManager(new LinearLayoutManager(this));

        if(loggedInUser.getRole().equals("User")) {
            if(vehicleType.contains("Cart"))
                cartList = db.getCart();
            else if(vehicleType.contains("Truck"))
                cartList = db.getTruck();
        }
        else
            cartList = db.getVehicles();

//                cartList = new ArrayList<Cart>();

///////////////////////TESTING/////////////////
//        cartList.add(new Cart(
//                "Cart 1",
//                "Wherever",
//                "14:00",
//                "16:00"
//        ));
//
//        cartList.add(new Cart(
//                "Cart 2",
//                "Alaska",
//                "2:00",
//                "16:00"
//        ));
////////////////////////////////////////////

        cartList.sort(Comparator.comparing(cart -> {
            try {
                return cart.getTimeStartTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }));
        cartAdapter = new CartAdapter(this, cartList, loggedInUser);
        cartView.setAdapter(cartAdapter);


        sortByStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartList.sort(Comparator.comparing(cart -> {
                    try {
                        return cart.getTimeStartTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                cartAdapter.notifyDataSetChanged();
            }
        });

        sortByLocationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartList.sort(Comparator.comparing(Cart::getLocation));
                cartAdapter.notifyDataSetChanged();
            }
        });
//        cartList = new ArrayList<Cart>();


//        cartList.add(new Cart(
//                "Cart 1",
//                "Status: Closed",
//                "Location: Cooper & UTA Blvd",
//                "Time slot: 8 am to 10 am"
//        ));
//        cartList.add(new Cart(
//                "Cart 2",
//                "Status: Closed",
//                "Location: W Nedderman & Greek Row",
//                "Time slot: 11 am to 12 pm"
//        ));
//        cartList.add(new Cart(
//                "Cart 3",
//                "Status: Closed",
//                "Location: S Davis & W Mitchell",
//                "Time slot: 12 pm to 2 pm"
//        ));
//        cartList.add(new Cart(
//                "Cart 4",
//                "Status: Closed",
//                "Location: Cooper & W Mitchelll",
//                "Time slot: 2 pm to 5 pm"
//        ));
//        cartList.add(new Cart(
//                "Truck 1",
//                "Status: Open",
//                "Location: Spaniolo & W 1st",
//                "Time slot: 2 pm to 6 pm"
//        ));
//        cartList.add(new Cart(
//                "Cart 5",
//                "Status: Closed",
//                "Location: S Oak & UTA Blvd",
//                "Time slot: 5 pm to 7 pm"
//        ));
//        cartList.add(new Cart(
//                "Truck 2",
//                "Status: Closed",
//                "Location: Spaniolo & W Mitchell",
//                "Time slot: 6 pm to 8 pm"
//        ));

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
