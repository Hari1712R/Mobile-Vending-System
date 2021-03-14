package com.example.mobilevendingsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Activities.ViewItemsActivity;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;

import java.util.List;

public class ShiftDetailsAdapter extends RecyclerView.Adapter<ShiftDetailsAdapter.ViewHolder> {

    private Context context;
    private List<Shift> shiftList;
    private DatabaseHandler db;
    private User loggedInUser;

    public ShiftDetailsAdapter(Context context, List<Shift> shiftList, User loggedInUser) {
        this.context = context;
        this.shiftList = shiftList;
        this.loggedInUser=loggedInUser;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_row, parent, false);

        db = new DatabaseHandler(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Shift shift = shiftList.get(position);

        holder.shiftId.setText("Shift Id: "+shift.getShiftId());
        holder.vehicleId.setText("Vehicle ID: "+shift.getVehicleId());
        holder.location.setText("Location: "+shift.getLocationName());
//        holder.operatorId.setText("Operator Id: "+shift.getOperatorId());
        holder.timeslot.setText("Time slot: "+shift.getStartTime()+" - "+shift.getEndTime());



        holder.vehicleInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItemsActivity.class);
                intent.putExtra("vehicle",  db.getVehiclefromID(shift.getVehicleId()));
                intent.putExtra("loggedInUser",loggedInUser);
                intent.putExtra("ShiftDetails", "ShiftDetails");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shiftList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView shiftId;
        public TextView vehicleId;
        public TextView location;
        public TextView operatorId;
        public TextView timeslot;
        public Button deleteButton;
        public Button vehicleInventory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shiftId = itemView.findViewById(R.id.shiftId);
            vehicleId = itemView.findViewById(R.id.vehicleId);
            location = itemView.findViewById(R.id.location);
            operatorId = itemView.findViewById(R.id.operatorId);
            operatorId.setVisibility(View.GONE);
            timeslot = itemView.findViewById(R.id.timeslot);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setVisibility(View.GONE);
            vehicleInventory = itemView.findViewById(R.id.viewItemsBtn);

        }
    }
}
