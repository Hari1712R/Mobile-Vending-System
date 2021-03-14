package com.example.mobilevendingsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilevendingsystem.Activities.MyOrder;
import com.example.mobilevendingsystem.Activities.Receipt;
import com.example.mobilevendingsystem.Activities.ShiftDetails;
import com.example.mobilevendingsystem.Model.Items;
import com.example.mobilevendingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private Context context;
    private List<Items> itemsList;
    private String reference;

    public ItemsAdapter(Context context, List itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public ItemsAdapter(Context context, List itemsList,String reference) {
        this.context = context;
        this.itemsList = itemsList;
        this.reference=reference;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(parent.getContext() instanceof MyOrder)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsAdapter.ViewHolder holder, int position) {
        Items items = itemsList.get(position);

        holder.itemName.setText(items.getItemName());
        try {
            holder.quantity.setText(items.getQuantity());
        }catch (Exception e){

        }
        holder.price.setText(items.getPrice());
        if (context instanceof MyOrder)
            holder.itemQuantity.setText(items.getQuantity());

        if(context instanceof Receipt) {
            holder.minusQuantity.setVisibility(View.GONE);
            holder.addQuantity.setVisibility(View.GONE);
        }

        if(reference!=null){
            holder.addQuantity.setVisibility(View.GONE);
            holder.minusQuantity.setVisibility(View.GONE);
            holder.itemQuantity.setVisibility(View.GONE);
        }

        holder.minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.itemQuantity.getText().toString());
                quantity--;
                holder.itemQuantity.setText(String.valueOf(quantity));
//                if (context instanceof MyOrder)
//                    updatePrice(holder);
            }
        });

        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.itemQuantity.getText().toString());
                quantity++;
                holder.itemQuantity.setText(String.valueOf(quantity));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName;
        public TextView quantity;
        public TextView price;
        public Button addQuantity;
        public Button minusQuantity;
        public TextView itemQuantity;
        public RecyclerView itemOrderView;
        public TextView subTotal;
        public TextView taxText;
        public TextView totalText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemOrderView = itemView.findViewById(R.id.itemOrderView);
            subTotal = itemView.findViewById(R.id.subTotalText);
            taxText = itemView.findViewById(R.id.taxText);
            totalText = itemView.findViewById(R.id.totalText);

            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            addQuantity = itemView.findViewById(R.id.addQuantity);
            minusQuantity = itemView.findViewById(R.id.minusQuantity);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);


        }

        @Override
        public void onClick(View v) {

        }
    }

    public void updatePrice(ViewHolder holder)
    {
        RecyclerView itemOrderView = holder.itemOrderView;
        List<Items> itemList = new ArrayList<>();
        Double subtotal=0.0;
        for (int i = 0; i < getItemCount(); i++) {
            View v = itemOrderView.getChildAt(i);
//            itemName = v.findViewById(R.id.itemName);
//            price =v.findViewById(R.id.price);
//            itemQuantity = v.findViewById(R.id.itemQuantity);
            int quantity = Integer.parseInt(holder.itemQuantity.getText().toString());
            if(quantity!=0) {
                subtotal += quantity * Double.parseDouble(holder.price.getText().toString());
                Items items = new Items(holder.itemName.getText().toString(), String.valueOf(quantity), holder.price.getText().toString());
                itemList.add(items);
            }
        }
        Double tax = 8.25/100 * subtotal;
        tax = Math.round(tax * 100.0) / 100.0;
        Double total = subtotal +tax;
        total = Math.round(total * 100.0) / 100.0;
        holder.subTotal.setText("Subtotal: $"+subtotal);
        holder.taxText.setText("Tax(8.25%): $"+tax);
        holder.totalText.setText("Total: $"+total);
    }
}
