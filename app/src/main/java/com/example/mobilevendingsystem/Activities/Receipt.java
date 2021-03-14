package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Adapter.ItemsAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

public class Receipt extends AppCompatActivity {

    private RecyclerView itemsView;
    private RecyclerView.Adapter itemsAdapter;
    private TextView operatorNameText;
    private TextView locationText;
    private TextView vehicleIdText;
    private TextView orderNoText;
    private TextView taxText;
    private TextView subTotalText;
    private TextView totalText;
    private Button pickedUpBtn;
    private Button cancelOrderBtn;
    private DatabaseHandler db;
    private Button logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);

        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
        final Order Order = (Order)getIntent().getSerializableExtra("order");
        final Double subtotal = (Double) getIntent().getSerializableExtra("subtotal");
        final Double tax = (Double) getIntent().getSerializableExtra("tax");

        db = new DatabaseHandler(this);

        itemsView = findViewById(R.id.itemsView);
        itemsView.setHasFixedSize(true);
        itemsView.setLayoutManager(new LinearLayoutManager(this));

        itemsAdapter = new ItemsAdapter(this, Order.getItemList());
        itemsView.setAdapter(itemsAdapter);

        operatorNameText = findViewById(R.id.operatorNameText);
        operatorNameText.setText("Operator Name: "+Order.getOperatorName());

        locationText = findViewById(R.id.locationText);
        locationText.setText("Location: "+Order.getLocName());

        vehicleIdText = findViewById(R.id.vehicleIdText);
        vehicleIdText.setText("Vehicle Id: "+Order.getVehicleId());

        orderNoText = findViewById(R.id.orderNoText);
        orderNoText.setText("Order#: "+Order.getOrderId());

        taxText = findViewById(R.id.taxText);
        taxText.setText("Tax(8.25%): "+tax);

        subTotalText = findViewById(R.id.subTotalText);
        subTotalText.setText("Subtotal: "+subtotal);

        totalText = findViewById(R.id.totalText);
        totalText.setText("Total: "+Order.getTotalPrice());

        pickedUpBtn = findViewById(R.id.pickedUpBtn);
        pickedUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updqateOrderStatus(Order,"Picked Up");
                Intent intent = new Intent(getApplicationContext(), OrderHistoryActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("order",Order);
                Toast.makeText(getApplicationContext(),"Picked-Up Order", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        cancelOrderBtn = findViewById(R.id.cancelOrderBtn);
        cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updqateOrderStatus(Order,"Cancelled");
                Intent intent = new Intent(getApplicationContext(), OrderHistoryActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("order",Order);
                Toast.makeText(getApplicationContext(),"Cancelled Order", Toast.LENGTH_SHORT).show();
                startActivity(intent);
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
