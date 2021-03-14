package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

public class ViewProfileActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button updateProfileButton;
    private EditText username, password, lastName, firstName, phone, email, street, city, state, zipcode;
    private EditText role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        setTitle("View Profile");
        final User loggedInUser = (User)getIntent().getSerializableExtra("loggedInUser");

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        lastName = findViewById(R.id.lastname);
        firstName = findViewById(R.id.firstname);
        role = findViewById(R.id.role);
        phone  =findViewById(R.id.phone);
        email = findViewById(R.id.email);
        street = findViewById(R.id.street_no);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        zipcode = findViewById(R.id.zipcode);

        username.setText(loggedInUser.getUsername());
        password.setText(loggedInUser.getPassword());
        lastName.setText(loggedInUser.getLastName());
        firstName.setText(loggedInUser.getFirstName());
        phone.setText(loggedInUser.getPhone());
        email.setText(loggedInUser.getEmail());
        street.setText(loggedInUser.getStreetNo());
        state.setText(loggedInUser.getState());
        zipcode.setText(loggedInUser.getZipCode());
        city.setText(loggedInUser.getCity());
        role.setText(loggedInUser.getRole());

        logoutButton = findViewById(R.id.logoutButton);
        updateProfileButton = findViewById(R.id.updateProfileButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(ViewProfileActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, UpdateProfileActivity.class);
                intent.putExtra("loggedInUser", loggedInUser);
                startActivity(intent);
            }
        });
    }

}
