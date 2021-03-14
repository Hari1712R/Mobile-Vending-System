package com.example.mobilevendingsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button registerButton;
    private TextView registerLink;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private DatabaseHandler db;
    private EditText userName;
    private EditText password;
    private EditText lastName;
    private EditText firsttName;
    private Spinner role;
    private EditText phone;
    private EditText email;
    private EditText streetNo;
    private EditText city;
    private EditText state;
    private EditText zipCode;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        registerLink = findViewById(R.id.registerLink);
        final String text ="Not a registered user? register here..";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(MainActivity.this,"Sample",Toast.LENGTH_SHORT).show();
                registerPopup();
            }
        };

        ss.setSpan(clickableSpan, 23,text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerLink.setText(ss);
        registerLink.setMovementMethod(LinkMovementMethod.getInstance());

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User loggedInUSer;
                if(!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    loggedInUSer=db.getUser(userName.getText().toString(),password.getText().toString());
                    Intent intent=null;
                    if(loggedInUSer != null) {
                        if(loggedInUSer.getRole().equalsIgnoreCase("User")) {
                            intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                            intent.putExtra("loggedInUser", loggedInUSer);
                        }else if(loggedInUSer.getRole().equalsIgnoreCase("Operator")){
                            intent = new Intent(getApplicationContext(), OperatorHomeActivity.class);
                            intent.putExtra("loggedInUser", loggedInUSer);
                        }else if(loggedInUSer.getRole().equalsIgnoreCase("Manager")){
                            intent = new Intent(getApplicationContext(), ManagerHomeActivity.class);
                            intent.putExtra("loggedInUser", loggedInUSer);
                        }
                        startActivity(intent);
                    }else{
                        Snackbar.make(v,"username/password is invalid",Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void registerPopup()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.register_popup,null);
        registerButton = view.findViewById(R.id.updateProfileButton);
        userName = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        lastName = view.findViewById(R.id.lastname);
        firsttName = view.findViewById(R.id.firstname);

        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        streetNo = view.findViewById(R.id.street_no);
        city = view.findViewById(R.id.city);
        state = view.findViewById(R.id.state);
        zipCode = view.findViewById(R.id.zipcode);

        role = view.findViewById(R.id.role);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.roles));
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(roleAdapter);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(valid().equals("good")) {
                    saveUserToDB(v);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, valid()+" is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String valid(){
        if(userName.getText().toString().isEmpty())
            return "Username";
        else if (password.getText().toString().isEmpty() || password.getText().toString().length()<=3)
            return "Password";
        else if (lastName.getText().toString().isEmpty() || !lastName.getText().toString().matches("^[a-zA-z]*$"))
            return "Lastname";
        else if (firsttName.getText().toString().isEmpty() || !firsttName.getText().toString().matches("^[a-zA-z]*$"))
            return "Firstname";
        else if (!phone.getText().toString().matches("[0-9]+") || phone.getText().toString().length()!=10)
            return "Phone";
        else if (email.getText().toString().isEmpty()|| !email.getText().toString().contains("@"))
            return "Email";
        else if (streetNo.getText().toString().isEmpty() || streetNo.getText().toString().length()<5)
            return "Street Address";
        else if (city.getText().toString().isEmpty() || city.getText().toString().length()<3)
            return "City";
        else if (state.getText().toString().isEmpty() || state.getText().toString().length()!=2)
            return "State";
        else if (zipCode.getText().toString().isEmpty() || !zipCode.getText().toString().matches("[0-9]+") ||zipCode.getText().toString().length()!=5)
            return "Zip Code";
        else if (role.getSelectedItem().toString().contains("Select a role.."))
            return "Role";
        return "good";
    }
    private void saveUserToDB(View v){


        String newUserName = userName.getText().toString();
        String newUserPassword = password.getText().toString();
        String newLastName = lastName.getText().toString();
        String newFirstName = firsttName.getText().toString();
        String newRole = role.getSelectedItem().toString();
        String newPhone = phone.getText().toString();
        String newEmail = email.getText().toString();
        String newStreetNo = streetNo.getText().toString();
        String newCity = city.getText().toString();
        String newState = state.getText().toString();
        String newZipCode = zipCode.getText().toString();

        User user= new User(newUserName,newUserPassword,newLastName,newFirstName,newRole,newPhone,newEmail,newStreetNo,newCity,newState,newZipCode);

        db.addUser(user);


    }
}
