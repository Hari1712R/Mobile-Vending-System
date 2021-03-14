package com.example.mobilevendingsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobilevendingsystem.Activities.ViewItemsActivity;
import com.example.mobilevendingsystem.Model.Cart;
import com.example.mobilevendingsystem.Model.User;
import com.example.mobilevendingsystem.R;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {

    private Context context;
    private List<Cart> cartList;
    private User loggedInUser;
    public CartAdapter(Context context, List cartList, User loggedInUser){
        this.context=context;
        this.cartList=cartList;
        this.loggedInUser=loggedInUser;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, int position) {
        final Cart cart = cartList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status="";
        holder.vehicleId.setText("Vehicle Id: "+cart.getVehicleId());
        try {
            if(date.compareTo(sdf.parse(cart.getStartTime()))>=0 && date.compareTo(sdf.parse(cart.getEndTime()))<=0)
                status ="Open";
            else
                status= "Closed";
            holder.vehicleStatus.setText("Status: "+status);
            holder.location.setText("Location: "+cart.getLocation());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.timeSlot.setText("Time slot:"+cart.getStartTime()+" to "+cart.getEndTime());

        if(loggedInUser.getRole().equals("Manager"))
            holder.viewItems.setText("Item Inventory");

        holder.viewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItemsActivity.class);
                intent.putExtra("vehicle",  cart);
                intent.putExtra("loggedInUser",loggedInUser);
                if(loggedInUser.getRole().equals("Manager"))
                    intent.putExtra("ShiftDetails", "ShiftDetails");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView vehicleId;
        public TextView vehicleStatus;
        public TextView location;
        public TextView timeSlot;
        public Button viewItems;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicleId = itemView.findViewById(R.id.vehicleId);
            vehicleStatus = itemView.findViewById(R.id.vehicleStatus);
            location = itemView.findViewById(R.id.location);
            timeSlot = itemView.findViewById(R.id.timeslot);
            viewItems = itemView.findViewById(R.id.viewItemsBtn);
        }
    }

}
