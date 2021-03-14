package com.example.mobilevendingsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Model.Items;
import com.example.mobilevendingsystem.Model.Order;
import com.example.mobilevendingsystem.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.orderId.setText("Order ID: "+order.getOrderId());
        holder.vehicleId.setText("Vehicle ID: "+order.getVehicleId());
        String str ="";
        for(Items items:order.getItemList()){
            str += items.getItemName()+" x"+items.getQuantity()+" ";
        }
        holder.itemList.setText("Item(s): "+str);
        holder.price.setText("Total Price: $"+order.getTotalPrice());
        holder.operatorName.setText("Operator Name: "+order.getOperatorName());
        holder.status.setText("Status: "+order.getStatus());
        holder.orderDate.setText("Date: "+order.getOrderDate());
        holder.location.setText("Location: "+order.getLocName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderId;
        public TextView vehicleId;
        public TextView itemList;
        public TextView price;
        public TextView operatorName;
        public TextView status;
        public TextView orderDate;
        public TextView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId =  itemView.findViewById(R.id.orderId);
            vehicleId = itemView.findViewById(R.id.vehicleId);
            itemList = itemView.findViewById(R.id.items);
            price = itemView.findViewById(R.id.price);
            operatorName = itemView.findViewById(R.id.operatorName);
            status = itemView.findViewById(R.id.status);
            orderDate = itemView.findViewById(R.id.orderDate);
            location = itemView.findViewById(R.id.location);
        }
    }
}
