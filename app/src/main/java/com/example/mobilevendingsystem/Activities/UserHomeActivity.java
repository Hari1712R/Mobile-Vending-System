package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

public class UserHomeActivity extends AppCompatActivity {

    //private TextView greetingUser;
    private Button logoutButton;
    private Button viewProfile;
    private TextView viewCartLink;
    private TextView viewTruckLink;
    private TextView orderHistLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_homepage);

        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");
       // greetingUser = findViewById(R.id.greetingManager);
       // greetingUser.setText("Hello "+loggedInUser.getFirstName());

        logoutButton = findViewById(R.id.logoutButton);
        viewProfile  =findViewById(R.id.viewProfileButton);
        viewCartLink = findViewById(R.id.cartTitle);
        viewTruckLink = findViewById(R.id.truckTitle);
        orderHistLink = findViewById(R.id.orderHistory);

        final String cartLinkText ="View Cart(s)";
        final String truckLinkText = "View Truck(s)";
        final String orderHistLinkText = "Order History";

        SpannableString orderSpan = new SpannableString(orderHistLinkText);

        ClickableSpan clickableOrderSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(UserHomeActivity.this, OrderHistoryActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);
            }
        };

        orderSpan.setSpan(clickableOrderSpan, 0, orderHistLinkText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderHistLink.setText(orderSpan);
        orderHistLink.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString truckSpan = new SpannableString(truckLinkText);

        ClickableSpan clickableTruckSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(MainActivity.this,"Sample",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserHomeActivity.this, ViewCart.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("vehicleType","Truck");
                startActivity(intent);
            }
        };

        truckSpan.setSpan(clickableTruckSpan, 0,truckLinkText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewTruckLink.setText(truckSpan);
        viewTruckLink.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString cartSpan = new SpannableString(cartLinkText);

        ClickableSpan clickableCartSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(MainActivity.this,"Sample",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserHomeActivity.this, ViewCart.class);
//                Intent intent = new Intent(UserHomeActivity.this, ViewItemsActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                intent.putExtra("vehicleType","Cart");
                startActivity(intent);
            }
        };

        cartSpan.setSpan(clickableCartSpan, 0,cartLinkText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewCartLink.setText(cartSpan);
        viewCartLink.setMovementMethod(LinkMovementMethod.getInstance());

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, ViewProfileActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(UserHomeActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
