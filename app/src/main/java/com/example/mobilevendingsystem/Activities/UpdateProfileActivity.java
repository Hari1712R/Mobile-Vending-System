package com.example.mobilevendingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;
import com.google.android.material.snackbar.Snackbar;

public class UpdateProfileActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button submitButton;
    private EditText username, password, lastName, firstName, phone, email, street, city, state, zipcode;
    private Spinner role;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        setTitle("Update Profile");

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

        db = new DatabaseHandler(this);

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

        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.roles));
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(roleAdapter);

        String compareValue = loggedInUser.getRole();
        if (compareValue != null) {
            int spinnerPosition = roleAdapter.getPosition(compareValue);
            role.setSelection(spinnerPosition);
        }

//        role.setText(loggedInUser.getRole());

        logoutButton = findViewById(R.id.logoutButton);
        submitButton = findViewById(R.id.submitButton);
        //Need to make changes in Db while submitting

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = updateUserInDB(v);
                Intent intent = new Intent(UpdateProfileActivity.this, ViewProfileActivity.class);
                intent.putExtra("loggedInUser", newUser);
                startActivity(intent);
                Toast.makeText(UpdateProfileActivity.this,"User information updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(UpdateProfileActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private User updateUserInDB(View v){
        String newUserName = username.getText().toString();
        String newUserPassword = password.getText().toString();
        String newLastName = lastName.getText().toString();
        String newFirstName = firstName.getText().toString();
        String newRole = role.getSelectedItem().toString();
        String newPhone = phone.getText().toString();
        String newEmail = email.getText().toString();
        String newStreetNo = street.getText().toString();
        String newCity = city.getText().toString();
        String newState = state.getText().toString();
        String newZipCode = zipcode.getText().toString();

        User user= new User(newUserName,newUserPassword,newLastName,newFirstName,newRole,newPhone,newEmail,newStreetNo,newCity,newState,newZipCode);

        db.updateUser(user);

        return user;
    }
}
