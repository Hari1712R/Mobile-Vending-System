package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

public class Payment extends AppCompatActivity {

    private Button makePayment;
    private Button receipt;
    private DatabaseHandler db;
    private Button logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
        final Order Order = (Order)getIntent().getSerializableExtra("order");
        final Cart vehicle = (Cart)getIntent().getSerializableExtra("vehicle");
        final Double subtotal = (Double) getIntent().getSerializableExtra("subtotal");
        final Double tax = (Double) getIntent().getSerializableExtra("tax");

        db = new DatabaseHandler(this);
        makePayment =findViewById(R.id.makePayment);
        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order1 = db.addOrder(Order, vehicle,loggedInUser);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                paymentSuccess(loggedInUser,order1,subtotal,tax);
//                setContentView(R.layout.payment_success);
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

    private void paymentSuccess(final User loggedInUser, final Order Order,final Double subtotal,final Double tax)
    {
        setContentView(R.layout.payment_success);

        receipt = findViewById(R.id.receiptBtn);
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Receipt.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("order",Order);
                intent.putExtra("subtotal",subtotal);
                intent.putExtra("tax",tax);
                startActivity(intent);
                finish();
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
