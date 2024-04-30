package com.example.pizza_app.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza_app.Modal.DelivaryModal.OrderDetail;
import com.example.pizza_app.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private List<OrderDetail> orderDetails;

    public OrderDetailAdapter(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);
        Log.d("check_orders", "onBindViewHolder: "+orderDetail.getName());
        holder.textName.setText(orderDetail.getName());
        holder.textQuantity.setText("*" + orderDetail.getQuantity());
        holder.textPrice.setText( orderDetail.getPrice());
    }

    @Override
    public int getItemCount() {
        Log.d("check_orders", "onBindViewHolder: "+orderDetails.size());

        return orderDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textQuantity;
        public TextView textPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.itemname);
            textQuantity = itemView.findViewById(R.id.count);
            textPrice = itemView.findViewById(R.id.itemtotal);
        }
    }
}
