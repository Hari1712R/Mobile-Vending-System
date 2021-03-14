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

import com.example.mobilevendingsystem.Adapter.OrderAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView orderHistoryView;
    private  RecyclerView.Adapter orderAdapter;
    private List<Order> orderList;
    private DatabaseHandler db;
    private Button sortByStartTimeInc, sortByStartTimeDec;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);
        setTitle("Order History");

        db = new DatabaseHandler(this);

        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
//        final Order Order = (Order)getIntent().getSerializableExtra("order");

        orderHistoryView = findViewById(R.id.orderHistoryView);
        sortByStartTimeDec = findViewById(R.id.sortByStartTimeDec);
        sortByStartTimeInc = findViewById(R.id.sortByStartTimeInc);
        orderHistoryView.setHasFixedSize(true);
        orderHistoryView.setLayoutManager(new LinearLayoutManager(this));

        orderList = db.getOrderHistory(loggedInUser);


        orderAdapter = new OrderAdapter(this, orderList);
        orderHistoryView.setAdapter(orderAdapter);

        sortByStartTimeDec.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                orderList.sort(Comparator.comparing(order -> {
                    try {
                        return order.getDateorderDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                Collections.reverse(orderList);
                orderAdapter.notifyDataSetChanged();
            }
        });

        sortByStartTimeInc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                orderList.sort(Comparator.comparing(order -> {
                    try {
                        return order.getDateorderDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                orderAdapter.notifyDataSetChanged();
            }
        });

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
