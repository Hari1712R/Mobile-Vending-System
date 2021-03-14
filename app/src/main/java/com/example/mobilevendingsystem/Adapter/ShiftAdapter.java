package com.example.mobilevendingsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Data.DatabaseHandler;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.Shift;
import com.example.mobilevendingsystem.R;

import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder> {

    private Context context;
    private List<Shift> shiftList;
    private DatabaseHandler db;

    public ShiftAdapter(Context context, List<Shift> shiftList) {
        this.context = context;
        this.shiftList = shiftList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShiftAdapter.ViewHolder holder, final int position) {
        final Shift shift = shiftList.get(position);

        holder.shiftId.setText("Shift Id: "+shift.getShiftId());
        holder.vehicleId.setText("Vehicle Id: "+shift.getVehicleId());
        holder.location.setText("Location: "+shift.getLocationName());
        holder.operatorId.setText("Operator Id: "+shift.getOperatorId());
        holder.timeslot.setText("Timeslot: "+shift.getStartTime()+" to "+shift.getEndTime());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(context);
                db.deleteShift(Integer.parseInt(shift.getShiftId()));
                shiftList.remove(position);
                notifyItemRemoved(position);
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
        public Button viewItemsBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shiftId = itemView.findViewById(R.id.shiftId);
            vehicleId = itemView.findViewById(R.id.vehicleId);
            location = itemView.findViewById(R.id.location);
            operatorId = itemView.findViewById(R.id.operatorId);
            timeslot = itemView.findViewById(R.id.timeslot);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            viewItemsBtn = itemView.findViewById(R.id.viewItemsBtn);
            viewItemsBtn.setVisibility(View.GONE);
        }
    }
}
