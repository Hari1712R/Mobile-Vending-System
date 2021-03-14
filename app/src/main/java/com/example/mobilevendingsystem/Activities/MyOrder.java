package com.example.mobilevendingsystem.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Adapter.ItemsAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.Items;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrder extends AppCompatActivity {

    private RecyclerView itemOrderView;
    private RecyclerView.Adapter itemsAdapter;
    private TextView subTotal;
    private TextView taxText;
    private TextView totalText;
    private TextView itemName;
    private TextView price;
    private TextView itemQuantity;
    private Button payBtn;
    private DatabaseHandler db;
    private Button logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);

        db = new DatabaseHandler(this);

        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
        final Order Order = (Order)getIntent().getSerializableExtra("order");
        final Double subtotal = (Double) getIntent().getSerializableExtra("subtotal");
        final Double tax = (Double) getIntent().getSerializableExtra("tax");
        final Cart vehicle = (Cart)getIntent().getSerializableExtra("vehicle");

        itemOrderView = findViewById(R.id.itemOrderView);
        itemOrderView.setHasFixedSize(true);
        itemOrderView.setLayoutManager(new LinearLayoutManager(this));

        itemsAdapter = new ItemsAdapter(this, Order.getItemList());
        itemOrderView.setAdapter(itemsAdapter);


        subTotal = findViewById(R.id.subTotalText);
        subTotal.setText("Subtotal: $"+subtotal);
        taxText = findViewById(R.id.taxText);
        taxText.setText("Tax(8.25%): $"+tax);
        totalText = findViewById(R.id.totalText);
        totalText.setText("Total: $"+Order.getTotalPrice());

        payBtn = findViewById(R.id.payBtn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Items> itemList = new ArrayList<>();
                Double subtotal=0.0;
                for (int i = 0; i < itemOrderView.getAdapter().getItemCount(); i++) {
                    v = itemOrderView.getChildAt(i);
                    itemName = v.findViewById(R.id.itemName);
                    price =v.findViewById(R.id.price);
                    itemQuantity = v.findViewById(R.id.itemQuantity);
                    int quantity = Integer.parseInt(itemQuantity.getText().toString());
                    if(quantity!=0) {
                        subtotal += quantity * Double.parseDouble(price.getText().toString());
                        Items items = new Items(itemName.getText().toString(), String.valueOf(quantity), price.getText().toString());
                        itemList.add(items);
                    }
                }
                String vehicleId = vehicle.getVehicleId();
                String operatorName = db.getAssignedOperatorName(vehicleId);

                Double tax = 8.25/100 * subtotal;
                tax = Math.round(tax * 100.0) / 100.0;
                Double total = subtotal +tax;
                total = Math.round(total * 100.0) / 100.0;
                subTotal.setText("Subtotal: $"+subtotal);
                taxText.setText("Tax(8.25%): $"+tax);
                totalText.setText("Total: $"+total);
                Toast.makeText(getApplicationContext(),"Price updated..",Toast.LENGTH_SHORT).show();

                Order order = new Order(vehicleId,itemList,operatorName,"Ongoing",total);
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("order",order);
                intent.putExtra("subtotal",subtotal);
                intent.putExtra("tax",tax);
                intent.putExtra("vehicle",vehicle);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
