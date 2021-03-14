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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

public class ManagerHomeActivity extends AppCompatActivity {

    private TextView greetingManager;
    private Button logoutButton;
    private TextView viewVehicleLink;
    private TextView assignShiftLink;
    private TextView totalRevenueLink;
    private Button viewProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_homepage);

        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");

        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHomeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(ManagerHomeActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewVehicleLink = findViewById(R.id.viewVehicle);
        final String viewVehicleLinkText ="View Vehicles";

        SpannableString vehicleSpan = new SpannableString(viewVehicleLinkText);

        ClickableSpan clickableVehicleSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(MainActivity.this,"Sample",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagerHomeActivity.this, ViewCart.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);
            }
        };

        vehicleSpan.setSpan(clickableVehicleSpan, 0,viewVehicleLinkText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewVehicleLink.setText(vehicleSpan);
        viewVehicleLink.setMovementMethod(LinkMovementMethod.getInstance());

        assignShiftLink = findViewById(R.id.assignShift);
        final String assignShiftText ="Assign Shift";
        SpannableString shiftSpan = new SpannableString(assignShiftText);
        ClickableSpan clickableShiftSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(MainActivity.this,"Sample",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagerHomeActivity.this, AssignShift.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);
            }
        };
        shiftSpan.setSpan(clickableShiftSpan, 0,assignShiftText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        assignShiftLink.setText(shiftSpan);
        assignShiftLink.setMovementMethod(LinkMovementMethod.getInstance());

        totalRevenueLink = findViewById(R.id.totalRevenue);
        final String totalRevenueText ="Total Revenue";
        SpannableString totalRevenueSpan = new SpannableString(totalRevenueText);
        ClickableSpan clickableTotalRevenueSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(MainActivity.this,"Sample",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagerHomeActivity.this, TotalRevenue.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);
            }
        };
        totalRevenueSpan.setSpan(clickableTotalRevenueSpan, 0,totalRevenueText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        totalRevenueLink.setText(totalRevenueSpan);
        totalRevenueLink.setMovementMethod(LinkMovementMethod.getInstance());

        viewProfile  =findViewById(R.id.viewProfileButton);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfileActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);

            }
        });
    }
}
