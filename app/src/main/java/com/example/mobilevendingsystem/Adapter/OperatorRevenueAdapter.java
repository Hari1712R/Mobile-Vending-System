package com.example.mobilevendingsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Activities.TotalRevenue;
import com.example.mobilevendingsystem.Activities.VehicleRevenue;
import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.Revenue;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.R;

import java.util.List;

public class OperatorRevenueAdapter extends RecyclerView.Adapter<OperatorRevenueAdapter.ViewHolder> {

    private Context context;
    private List<Revenue> revList;
    private DatabaseHandler db;

    public OperatorRevenueAdapter(Context context, List<Revenue> revList) {
        this.context = context;
        this.revList = revList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Revenue rev = revList.get(position);

        holder.vehicleId.setText("Vehicle Id: "+rev.getVehicle_id());
        holder.location.setText("Location: "+rev.getLocation());
        holder.revenue.setText("Revenue: "+rev.getRevenue());
    }

    @Override
    public int getItemCount() {
        return revList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView revenue;
        public TextView vehicleId, operatorId, timeslot,deleteButton, location;
        public Button viewItemsBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicleId = itemView.findViewById(R.id.shiftId);
            revenue = itemView.findViewById(R.id.vehicleId);
            location = itemView.findViewById(R.id.location);

            operatorId = itemView.findViewById(R.id.operatorId);
            operatorId.setVisibility(View.GONE);
            timeslot = itemView.findViewById(R.id.timeslot);
            timeslot.setVisibility(View.GONE);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setVisibility(View.GONE);

            if(context instanceof TotalRevenue || context instanceof VehicleRevenue) {
                viewItemsBtn = itemView.findViewById(R.id.viewItemsBtn);
                viewItemsBtn.setVisibility(View.GONE);
            }
        }
    }
}
