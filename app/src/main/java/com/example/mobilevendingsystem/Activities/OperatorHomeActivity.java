package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;
import com.google.android.material.snackbar.Snackbar;

public class OperatorHomeActivity extends AppCompatActivity {

    //private TextView greetingOperator;
    private Button logoutButton;
    private TextView shift_details;
    private TextView vehicle_revenue;
    private Button viewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operator_homepage);

        User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");

        shift_details = findViewById(R.id.shiftDetails);
        vehicle_revenue = findViewById(R.id.totaRvenue);

        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OperatorHomeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(OperatorHomeActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        shift_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OperatorHomeActivity.this, ShiftDetails.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);

            }
        });

        vehicle_revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OperatorHomeActivity.this, VehicleRevenue.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);

            }
        });

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
