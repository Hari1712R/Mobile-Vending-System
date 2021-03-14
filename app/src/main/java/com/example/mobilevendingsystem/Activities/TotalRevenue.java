package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Adapter.OperatorRevenueAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Revenue;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

public class TotalRevenue extends AppCompatActivity {
    private RecyclerView revenueView;
    private DatabaseHandler db;
    private List<Revenue> revenueList;
    private RecyclerView.Adapter revenueAdapter;
    private TextView totalRevenueTxt;
    private Button logoutButton;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_revenue);

        final User loggedInOperator = (User)getIntent().getSerializableExtra("loggedInUser");

        revenueView = findViewById(R.id.vehiclerevenueview);
        db = new DatabaseHandler(this);

//        db.addOrder(new Order("7","11","50","onetwothree","zx","available"));

        revenueView.setHasFixedSize(true);
        revenueView.setLayoutManager(new LinearLayoutManager(this));
        revenueList=db.getTotalVehicleRevenue(loggedInOperator);

        revenueList.sort(Comparator.comparing(Revenue::getDoubleRevenue));
        revenueAdapter = new OperatorRevenueAdapter(this, revenueList);
        revenueView.setAdapter(revenueAdapter);

        double totRevenue=0;
        for(Revenue revenue:revenueList)
        {
            totRevenue += Double.valueOf(revenue.getRevenue());
        }

        totalRevenueTxt = findViewById(R.id.totalRevenueTxt);
        totalRevenueTxt.setText("Total Revenue: "+totRevenue);

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
