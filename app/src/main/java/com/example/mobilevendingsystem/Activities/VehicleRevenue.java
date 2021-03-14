package com.example.mobilevendingsystem.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilevendingsystem.Adapter.OperatorRevenueAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Items;
import com.example.mobilevendingsystem.Model.Revenue;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VehicleRevenue extends AppCompatActivity {
    private RecyclerView revenueView;
    private DatabaseHandler db;
    private List<Revenue> revenueList;
    private RecyclerView.Adapter revenueAdapter;
    private Button logoutButton;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_revenue);
        final User loggedInOperator = (User)getIntent().getSerializableExtra("loggedInUser");

        revenueView = findViewById(R.id.vehiclerevenueview);
        db = new DatabaseHandler(this);

//        db.addOrder(new Order("7","11","50","onetwothree","zx","available"));

        revenueView.setHasFixedSize(true);
        revenueView.setLayoutManager(new LinearLayoutManager(this));
        revenueList=db.getOperatorsVehicleRevenue(loggedInOperator);
        revenueList.sort(Comparator.comparing(Revenue::getDoubleRevenue));
        revenueAdapter = new OperatorRevenueAdapter(this, revenueList);
        revenueView.setAdapter(revenueAdapter);

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
