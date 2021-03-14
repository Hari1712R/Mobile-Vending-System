package com.example.mobilevendingsystem.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {

    private RecyclerView itemsView;
    private List<Items> itemsList;
    private RecyclerView.Adapter itemsAdapter;
    private DatabaseHandler db;
    private Button myOrder;
    private Button sortByPrice;
    private Button sortByName;

    private TextView itemName;
    private TextView itemQuantity;
    private TextView price;
    private Button logoutButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_items);
        setTitle("View Items");

        db = new DatabaseHandler(this);

        final Cart vehicle = (Cart)getIntent().getSerializableExtra("vehicle");
        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
        final String reference = (String) getIntent().getSerializableExtra("ShiftDetails");

        sortByName = findViewById(R.id.sortByType);
        sortByPrice = findViewById(R.id.sortByPrice);
        itemsView = findViewById(R.id.itemView);
        itemsView.setHasFixedSize(true);
        itemsView.setLayoutManager(new LinearLayoutManager(this));

        itemsList = db.getVehicleItems(vehicle);
        itemsList.sort(Comparator.comparing(Items::getItemName));
        try{
            if(!loggedInUser.getRole().contains("User")) {
                myOrder = findViewById(R.id.myOrderButton);
                myOrder.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }

//        itemsList = new ArrayList<Items>();
//
//        itemsList.add(new Items(
//                "Drinks",
//                "39",
//                "1.50"
//        ));
//
//        itemsList.add(new Items(
//                "Sandwiches",
//                "39",
//                "5.75"
//        ));
//
//        itemsList.add(new Items(
//                "Snacks",
//                "39",
//                "$1.25"
//        ));

        if(reference!=null){
            itemsAdapter = new ItemsAdapter(this, itemsList, reference);
        }
        else
            itemsAdapter = new ItemsAdapter(this, itemsList);
        itemsView.setAdapter(itemsAdapter);

        sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(itemsList);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        sortByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsList.sort(Comparator.comparing(Items::getDoublePrice));
                itemsAdapter.notifyDataSetChanged();
            }
        });
        myOrder = findViewById(R.id.myOrderButton);
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Items> itemList = new ArrayList<>();
                Double subtotal=0.0;
                for (int i = 0; i < itemsView.getAdapter().getItemCount(); i++) {
                    v = itemsView.getChildAt(i);
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
                Order order = new Order(vehicleId,itemList,operatorName,"Ongoing",total);
                Intent intent = new Intent(ViewItemsActivity.this, MyOrder.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("order",order);
                intent.putExtra("subtotal",subtotal);
                intent.putExtra("tax",tax);
                intent.putExtra("vehicle",vehicle);
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
