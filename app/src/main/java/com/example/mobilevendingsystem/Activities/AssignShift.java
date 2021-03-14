package com.example.mobilevendingsystem.Activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Adapter.CartAdapter;
import com.example.mobilevendingsystem.Adapter.ShiftAdapter;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.R;

import java.util.List;

public class AssignShift extends AppCompatActivity {

    private EditText startTime;
    private EditText endTime;
    private Spinner vehicleSpinner;
    private Spinner operatorSpinner;
    private Spinner locationSpinner;
    private RecyclerView shiftView;
    private List<Shift> shiftList;
    private RecyclerView.Adapter shiftAdapter;
    private Button assignShiftBtn;
    private Button logoutButton;

    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_shift);

        startTime = findViewById(R.id.startTime);
        startTime.setText("00:00");
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AssignShift.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(hourOfDay+":"+minute);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        endTime = findViewById(R.id.endTime);
        endTime.setText("00:00");
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AssignShift.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(hourOfDay+":"+minute);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        db = new DatabaseHandler(this);
        List<String> vehicleNameList = db.getVehicleName();
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vehicleNameList);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(vehicleAdapter);

        operatorSpinner = findViewById(R.id.operatorSpinner);
        List<String> operatorNameList = db.getOperatorName();
        ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operatorNameList);
        operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(operatorAdapter);

        locationSpinner = findViewById(R.id.locationSpinner);
        List<String> locationNameList = db.getLocationName();
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationNameList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        assignShiftBtn = findViewById(R.id.assignShiftBtn);
        assignShiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleId = vehicleSpinner.getSelectedItem().toString();
                String locId = locationSpinner.getSelectedItem().toString();
                String etime = endTime.getText().toString();
                String stime = startTime.getText().toString();
                String operatorId = operatorSpinner.getSelectedItem().toString();
                Shift shift = new Shift(vehicleId, locId,stime,etime,operatorId);
                db.addShift(shift);
                shiftList.add(shift);
                shiftAdapter.notifyItemInserted(shiftList.indexOf(shift));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), AssignShift.class));
                        finish();
                    }
                }, 500);
            }
        });

        shiftView= findViewById(R.id.shiftView);
        shiftView.setHasFixedSize(true);
        shiftView.setLayoutManager(new LinearLayoutManager(this));

        shiftList = db.getShifts();
        shiftAdapter = new ShiftAdapter(this, shiftList);
        shiftView.setAdapter(shiftAdapter);

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
