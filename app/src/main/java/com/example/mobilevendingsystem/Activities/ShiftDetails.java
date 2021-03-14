package com.example.mobilevendingsystem.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobilevendingsystem.Adapter.ShiftDetailsAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Items;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShiftDetails extends AppCompatActivity {
    private RecyclerView shiftsView;
    private List<Shift> shiftsList;
    private RecyclerView.Adapter shiftsAdapter;
    private DatabaseHandler db;
    private Button sort;
    private Button logoutButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_details);
        final User loggedInOperator = (User)getIntent().getSerializableExtra("loggedInUser");

        sort = findViewById(R.id.sortTime);
        db = new DatabaseHandler(this);
        shiftsView = findViewById(R.id.shiftDetailView);
        shiftsView.setHasFixedSize(true);
        shiftsView.setLayoutManager(new LinearLayoutManager(this));

//        shiftsList = new ArrayList<Shift>();

//        db.addShift(new Shift(
//                "100", "10", "Nowhere", "11:00", "99", loggedInOperator.getUsername()
//        ));
//        db.addShift(new Shift(
//                "100", "0", "Nowhere", "2:00", "99", loggedInOperator.getUsername()
//        ));
//        db.addShift(new Shift(
//                "100", "0", "Nowhere", "14:00", "99", loggedInOperator.getUsername()
//        ));

        shiftsList=db.getOperatorShifts(loggedInOperator);
        shiftsList.sort(Comparator.comparing(shift -> {
            try {
                return shift.getTimeStartTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }));


        shiftsAdapter = new ShiftDetailsAdapter(getApplicationContext(), shiftsList,loggedInOperator);
        shiftsView.setAdapter(shiftsAdapter);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftsList.sort(Comparator.comparing(shift -> {
                    try {
                        return shift.getTimeStartTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                shiftsAdapter.notifyDataSetChanged();
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
